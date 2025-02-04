package global.hiber.api.rest.customer.modem.message

import global.hiber.api.customer.asProto
import global.hiber.api.customer.conversion.json.toJsonObject
import global.hiber.api.customer.modem.asNewMessageProto
import global.hiber.api.rest.HAL
import global.hiber.api.rest.PaginationResult
import global.hiber.api.rest.RestApiResponse
import global.hiber.api.rest.asRestApiPaginationResult
import global.hiber.api.rest.restApiResource
import global.hiber.api.rest.toJson
import global.hiber.auth.Messages
import global.hiber.auth.Modems
import global.hiber.auth.Scope.Organization
import global.hiber.common.iterables.mapToSet
import global.hiber.database.system.db
import global.hiber.database.system.parseModemNumberHex
import global.hiber.database.system.type.filter.Filter
import global.hiber.database.util.Pagination
import global.hiber.modem.ModemSelection
import global.hiber.modem.message.ModemMessageSelection
import global.hiber.modem.message.ModemMessageWithAdditionalData
import global.hiber.modem.message.modemMessages
import global.hiber.serialization.time.TimeRangeSerializer
import global.hiber.tag.TagSelection
import io.ktor.http.Parameters
import io.ktor.resources.Resource
import io.ktor.server.routing.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RestApiMessageList(
  val messages: List<JsonObject>,

  @SerialName("_links")
  val links: Links,
  val pagination: PaginationResult,
) : RestApiResponse {
  @Resource(location) data class Request(
    override val from: String? = null,
    override val to: String? = null,
    override val devices: String? = null,
    override val modems: String? = null,
    override val tags: String? = null,
    override val groups: String? = null,
    override val size: Int = 0,
    override val page: Int = 0,
  ) : RestApiMessageBase.Request,
    Parameters by HAL.Link.parameters(
      "from" to from,
      "to" to to,
      "devices" to devices,
      "modems" to modems,
      "tags" to tags,
      "groups" to groups,
      "size" to size,
      "page" to page,
    ) {
    fun copyWithSelection(selection: ModemMessageSelection) = copy(
      from = selection.timeRange?.start?.asProto?.textual ?: from,
      to = selection.timeRange?.endInclusive?.asProto?.textual ?: to,
    )

    override fun withPagination(newSize: Int, newPage: Int) = copy(size = newSize, page = newPage)

    companion object {
      val allowedParameters = setOf("from", "to", "devices", "modems", "tags", "groups", "size", "page")
    }
  }

  @Serializable
  data class Links(
    override val self: HAL.Link,
    override val previous: HAL.Link?,
    override val next: HAL.Link?,
  ) : HAL.Links {
    companion object {
      operator fun invoke(
        request: RestApiMessageBase.Request,
        pagination: Pagination,
        paginated: Pagination.Result<*>,
        link: (RestApiMessageBase.Request) -> HAL.Link,
      ) = Links(
        self = link(request.withPagination(pagination.limit, pagination.page)),
        next = paginated.next?.let { next -> link(request.withPagination(pagination.limit, next.page)) },
        previous = paginated.previous?.let { previous ->
          link(request.withPagination(pagination.limit, previous.page))
        },
      )
    }
  }

  companion object : RestApiMessageBase {
    const val location: String = "/messages"

    operator fun invoke(
      parent: Route,
      request: Request,
      pagination: Pagination,
      paginated: Pagination.Result<ModemMessageWithAdditionalData>,
    ) = RestApiMessageList(
      messages = paginated.results.map {
        it.modemMessage.asNewMessageProto(
          modem = it.modem,
          tagRecords = it.tagRecords,
          bodyResult = it.bodyResult,
        ).toJsonObject(full = false)
      },
      links = Links(request, pagination, paginated, link = { link(parent, it) }),
      pagination = paginated.asRestApiPaginationResult,
    )

    override val route = restApiResource<Request>(
      path = location,
      allowedParameters = Request.allowedParameters,
      head = { _, _, _ -> },
      get = { parent, _, request ->
        // selection and pagination
        val selection = ModemMessageSelection(
          timeRange = when {
            request.from.isNullOrBlank() || request.to.isNullOrBlank() -> null
            else -> TimeRangeSerializer.parse(start = request.from, end = request.to)
          },
          modemSelection = ModemSelection(
            modems = Filter.Modems(
              include = request.devices?.split(",")?.map { it.trim().parseModemNumberHex() }.orEmpty() +
                request.modems?.split(",")?.map { it.trim().parseModemNumberHex() }.orEmpty(),
            ),
            filterByTags = TagSelection(
              names = request.tags?.split(",")?.mapToSet { it.trim() }.orEmpty() +
                request.groups?.split(",")?.mapToSet { it.trim() }.orEmpty(),
            ),
          ),
        )
        val pagination = ModemMessageSelection.Pagination(size = request.size, page = request.page)

        // do the actual call
        val result = db.read(Organization.Read(identity).Modems().Messages()) {
          modemMessages(selection, pagination)
        }

        // json response
        RestApiMessageList(parent, request.copyWithSelection(selection), pagination, result).toJson()
      },
    )
  }
}
