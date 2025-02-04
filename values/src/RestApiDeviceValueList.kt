package global.hiber.api.rest.customer.value

import global.hiber.api.customer.asDb
import global.hiber.api.customer.value.asProto
import global.hiber.api.rest.HAL
import global.hiber.api.rest.PaginationResult
import global.hiber.api.rest.RestApiResponse
import global.hiber.api.rest.customer.value.RestApiValueBase.Response.Links
import global.hiber.api.rest.customer.value.RestApiValueBase.Response.ValueContext
import global.hiber.api.rest.restApiResource
import global.hiber.api.rest.toJson
import global.hiber.auth.Modems
import global.hiber.auth.Values
import global.hiber.common.iterables.mapNotNullToSet
import global.hiber.common.iterables.mapToSet
import global.hiber.database.system.db
import global.hiber.database.system.modem.identifier.deviceInfoForValues
import global.hiber.database.system.parseModemNumberHex
import global.hiber.database.system.unit.unitPreferences
import global.hiber.modem.ModemSelection
import global.hiber.modem.modemNumbersFromSelection
import global.hiber.serialization.time.TimeRangeSerializer
import global.hiber.value.ValueSelection
import global.hiber.value.values
import io.ktor.http.Parameters
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestApiDeviceValueList(
  override val values: List<ValueContext>,

  @SerialName("_links")
  override val links: Links,
  override val pagination: PaginationResult,
) : RestApiResponse, RestApiValueBase.Response {

  @Resource(location) data class Request(
    val device: List<String> = emptyList(),
    override val type: List<String> = emptyList(),
    override val from: String? = null,
    override val to: String? = null,
    override val size: Int? = null,
    override val page: Int? = null,
  ) : RestApiValueBase.Request,
    Parameters by HAL.Link.parameters(
      "device" to device,
      "type" to type,
      "from" to from,
      "to" to to,
      "size" to size,
      "page" to page,
    ) {
    val devices get() = device.flatMap { it.split(",") }.mapToSet { it.trim() }
    override fun withPagination(newSize: Int, newPage: Int) = copy(size = newSize, page = newPage)

    companion object {
      val allowedParameters = setOf("device", "type", "from", "to", "size", "page")
    }
  }

  companion object : RestApiValueBase.CompanionBase<RestApiDeviceValueList> {
    override fun invoke(values: List<ValueContext>, links: Links, pagination: PaginationResult) =
      RestApiDeviceValueList(values, links, pagination)

    const val location: String = "/values/device"
    override val route = restApiResource<Request>(
      path = location,
      allowedParameters = Request.allowedParameters,
      head = { _, _, request -> request.devices.mapToSet { it.parseModemNumberHex() } },
      get = { parent, _, request ->
        // selection and pagination
        val selection = ValueSelection.Device(
          modems = db.read(Modems()) {
            modemNumbersFromSelection(
              selection = ModemSelection(request.devices.mapToSet { it.parseModemNumberHex() }),
              allowDefaultSelection = true,
              allowInvalid = true,
            )
          },
          valueTypes = request.valueTypes.mapNotNullToSet { it.asDb },
          numericValueTypes = request.numericValueTypes.mapNotNullToSet { it.asDb },
          timeRange = when {
            request.from.isNullOrBlank() || request.to.isNullOrBlank() -> null
            else -> TimeRangeSerializer.parse(start = request.from, end = request.to)
          },
        )
        val pagination = ValueSelection.Pagination(size = request.size ?: 0, page = request.page ?: 0)

        // do the actual call
        val result = db.read(Modems().Values()) { values(selection, pagination) }
        val unitPreferences = db.read(this) { unitPreferences() }
        val devices = db.read(this) { deviceInfoForValues(result.results.mapToSet { it.device }) }
          .associateBy { it.number }

        invoke(
          parent,
          request,
          pagination,
          result.withExpandedResults { it.asProto(emptyMap(), emptyMap(), devices, unitPreferences) },
        ).toJson()
      },
    )
  }
}
