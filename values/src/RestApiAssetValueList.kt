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
import global.hiber.asset.AssetSelection
import global.hiber.asset.assets
import global.hiber.auth.Assets
import global.hiber.common.iterables.mapNotNullToSet
import global.hiber.common.iterables.mapToSet
import global.hiber.database.system.db
import global.hiber.database.system.modem.identifier.deviceInfoForValues
import global.hiber.database.system.unit.unitPreferences
import global.hiber.serialization.time.TimeRangeSerializer
import global.hiber.value.ValueSelection
import global.hiber.value.values
import io.ktor.http.Parameters
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestApiAssetValueList(
  override val values: List<ValueContext>,

  @SerialName("_links")
  override val links: Links,
  override val pagination: PaginationResult,
) : RestApiResponse, RestApiValueBase.Response {

  @Resource(location) data class Request(
    val asset: List<String> = emptyList(),
    override val type: List<String> = emptyList(),
    override val from: String? = null,
    override val to: String? = null,
    override val size: Int? = null,
    override val page: Int? = null,
  ) : RestApiValueBase.Request,
    Parameters by HAL.Link.parameters(
      "asset" to asset,
      "type" to type,
      "from" to from,
      "to" to to,
      "size" to size,
      "page" to page,
    ) {
    val assets get() = asset.flatMap { it.split(",") }.mapToSet { it.trim() }
    override fun withPagination(newSize: Int, newPage: Int) = copy(size = newSize, page = newPage)

    companion object {
      val allowedParameters = setOf("asset", "type", "from", "to", "size", "page")
    }
  }

  companion object : RestApiValueBase.CompanionBase<RestApiAssetValueList> {
    override fun invoke(values: List<ValueContext>, links: Links, pagination: PaginationResult) =
      RestApiAssetValueList(values, links, pagination)

    const val location: String = "/values/asset"
    override val route = restApiResource<Request>(
      path = location,
      allowedParameters = Request.allowedParameters,
      head = { _, _, _ -> },
      get = { parent, _, request ->
        // selection and pagination
        val selection = ValueSelection.Asset(
          assets = request.assets,
          valueTypes = request.valueTypes.mapNotNullToSet { it.asDb },
          numericValueTypes = request.numericValueTypes.mapNotNullToSet { it.asDb },
          timeRange = when {
            request.from.isNullOrBlank() || request.to.isNullOrBlank() -> null
            else -> TimeRangeSerializer.parse(start = request.from, end = request.to)
          },
        )
        val pagination = ValueSelection.Pagination(size = request.size ?: 0, page = request.page ?: 0)

        // do the actual call
        val result = db.read(Assets().Values()) { values(selection, pagination) }
        val unitPreferences = db.read(this) { unitPreferences() }
        val devices = db.read(this) { deviceInfoForValues(result.results.mapToSet { it.device }) }
          .associateBy { it.number }
        val assets = (selection as? ValueSelection.Asset)?.assets?.let {
          db.read(Assets()) { assets(AssetSelection(it)).results.associateBy { it.identifier } }
        }.orEmpty()

        invoke(
          parent,
          request,
          pagination,
          result.withExpandedResults { it.asProto(emptyMap(), assets, devices, unitPreferences) },
        ).toJson()
      },
    )
  }
}
