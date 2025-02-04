package global.hiber.api.rest.customer.modem.message.event

import global.hiber.api.customer.asProto
import global.hiber.api.customer.conversion.json.toJsonObject
import global.hiber.api.customer.event.asProto
import global.hiber.api.grpc.event.EventApi
import global.hiber.api.rest.HAL
import global.hiber.api.rest.PaginationResult
import global.hiber.api.rest.RestApiResponse
import global.hiber.api.rest.asRestApiPaginationResult
import global.hiber.api.rest.customer.modem.message.RestApiMessageBase
import global.hiber.api.rest.customer.modem.message.RestApiMessageList
import global.hiber.api.rest.restApiResource
import global.hiber.api.rest.toJson
import global.hiber.auth.Messages
import global.hiber.auth.Modems
import global.hiber.auth.Scope
import global.hiber.common.iterables.mapToSet
import global.hiber.database.system.db
import global.hiber.database.system.enums.EventType
import global.hiber.database.system.parseModemNumberHex
import global.hiber.database.system.type.filter.Filter
import global.hiber.database.util.Pagination
import global.hiber.events.EventSelection
import global.hiber.events.EventSort
import global.hiber.events.unbundled.eventsUnbundled
import global.hiber.serialization.time.TimeRangeSerializer
import global.hiber.tag.TagSelection
import global.hiber.tag.tags
import io.ktor.http.Parameters
import io.ktor.resources.Resource
import io.ktor.server.routing.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RestApiMessageEventList(
  val messageEvents: List<JsonObject>,

  @SerialName("_links")
  val links: RestApiMessageList.Links,
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
    fun copyWithSelection(selection: EventSelection) = copy(
      from = selection.timeRange?.start?.asProto?.textual ?: from,
      to = selection.timeRange?.endInclusive?.asProto?.textual ?: to,
    )

    override fun withPagination(newSize: Int, newPage: Int) = copy(size = newSize, page = newPage)

    companion object {
      val allowedParameters = setOf("from", "to", "devices", "modems", "tags", "groups", "size", "page")
    }
  }

  companion object : RestApiMessageBase {
    const val location: String = "/message/events"

    operator fun invoke(
      parent: Route,
      request: Request,
      pagination: Pagination,
      paginated: Pagination.Result<EventApi.Event.MessageEvent.MessageReceivedEvent>,
    ) = RestApiMessageEventList(
      messageEvents = paginated.results.map { it.toJsonObject(full = false) },
      links = RestApiMessageList.Links.invoke(request, pagination, paginated) { link(parent, it) },
      pagination = paginated.asRestApiPaginationResult,
    )

    override val route = restApiResource<Request>(
      path = location,
      allowedParameters = Request.allowedParameters,
      head = { _, _, _ -> },
      get = { parent, _, request ->
        val eventType = EventType.modem_message_received
        val tags = db.read(Scope.Organization.Read(identity)) {
          tags(
            TagSelection(
              names = request.tags?.split(",")?.mapToSet { it.trim() }.orEmpty() +
                request.groups?.split(",")?.mapToSet { it.trim() }.orEmpty(),
            ),
          )
        }

        // selection and pagination
        val selection = EventSelection(
          events = Filter.Events(include = setOf(eventType)),
          timeRange = when {
            request.from.isNullOrBlank() || request.to.isNullOrBlank() -> null
            else -> TimeRangeSerializer.parse(start = request.from, end = request.to)
          },
          modems = Filter.Modems(
            include = request.devices?.split(",")?.map { it.trim().parseModemNumberHex() }.orEmpty() +
              request.modems?.split(",")?.map { it.trim().parseModemNumberHex() }.orEmpty(),
          ),
          tags = Filter.Tags(include = tags.mapToSet { it.displayId }),
          unbundledEvents = true,
          includeResolved = true, // just in case this becomes relevant later
        )
        val pagination = EventSelection.Pagination(size = request.size, page = request.page)

        // do the actual call
        val result = db.read(Scope.Organization.Read(identity).Modems().Messages()) {
          eventsUnbundled(selection, pagination, sort = EventSort.TIME_DESCENDING)[eventType]
            ?.withExpandedResults { it.asProto!!.messageReceived }
            ?: Pagination.Result.empty(pagination)
        }

        // json response
        RestApiMessageEventList(parent, request.copyWithSelection(selection), pagination, result).toJson()
      },
    )
  }
}
