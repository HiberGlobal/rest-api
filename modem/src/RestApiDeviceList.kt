package global.hiber.api.rest.customer.modem

import global.hiber.api.customer.modem.asProto
import global.hiber.api.rest.HAL
import global.hiber.api.rest.PaginationResult
import global.hiber.api.rest.RestApiResponse
import global.hiber.api.rest.asRestApiPaginationResult
import global.hiber.api.rest.customer.modem.RestApiModem.Modem.Companion.asModem
import global.hiber.api.rest.restApiResource
import global.hiber.api.rest.toJson
import global.hiber.auth.Modems
import global.hiber.common.iterables.mapToSet
import global.hiber.database.system.db
import global.hiber.database.system.type.filter.Filter
import global.hiber.database.util.Pagination
import global.hiber.modem.ModemSelection
import global.hiber.modem.ModemSelection.ModemViewSort.MODEM_NAME_ASC
import global.hiber.modem.modems
import global.hiber.tag.TagSelection
import io.ktor.http.Parameters
import io.ktor.resources.Resource
import io.ktor.server.routing.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestApiDeviceList(
  val devices: List<RestApiModem.Modem>,

  @SerialName("_links")
  val links: Links,
  val pagination: PaginationResult,
) : RestApiResponse {
  @Resource(location) data class Request(
    val tag: List<String> = emptyList(),
    val type: List<String> = emptyList(),
    val size: Int? = null,
    val page: Int? = null,
  ) : Parameters by HAL.Link.parameters("tags" to tag, "type" to type, "size" to size, "page" to page) {
    val tags get() = tag.flatMap { it.split(",") }.mapToSet { it.trim() }
    val types get() = type.flatMap { it.split(",") }.mapToSet { it.trim() }

    fun withPagination(newSize: Int, newPage: Int) = copy(size = newSize, page = newPage)

    companion object {
      val allowedParameters = setOf("tag", "type", "size", "page")
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
        parent: Route,
        request: Request,
        pagination: Pagination,
        paginated: Pagination.Result<*>,
      ): Links = Links(
        self = link(parent, request.withPagination(pagination.limit, pagination.page)),
        next = paginated.next?.let { next -> link(parent, request.withPagination(pagination.limit, next.page)) },
        previous = paginated.previous?.let { previous ->
          link(parent, request.withPagination(pagination.limit, previous.page))
        },
      )
    }
  }

  companion object {
    const val location: String = "/devices"
    fun path(parent: Route?) = route.path(parent)
    fun link(parent: Route?, request: Request) = HAL.Link.withDomain(path(parent), request)

    operator fun invoke(
      parent: Route,
      request: Request,
      pagination: Pagination,
      paginated: Pagination.Result<RestApiModem.Modem>,
    ) = RestApiDeviceList(
      devices = paginated.results,
      links = Links(parent, request, pagination, paginated),
      pagination = paginated.asRestApiPaginationResult,
    )

    val route = restApiResource<Request>(
      path = location,
      allowedParameters = Request.allowedParameters,
      head = { _, _, _ -> },
      get = { parent, _, request ->
        // selection and pagination
        val selection = ModemSelection(
          filterByTags = TagSelection(names = request.tags.mapToSet { it.trim() }),
          deviceTypes = Filter.DeviceTypes(request.types),
        )
        val pagination = ModemSelection.Pagination(size = request.size ?: 0, page = request.page ?: 0)

        // do the actual call
        val result = db.read(Modems()) {
          val paginated = modems(selection, pagination, listOf(MODEM_NAME_ASC.sortField))
          val proto = asProto(paginated.results).map { it.asModem }.associateBy { it.number }
          paginated.withExpandedResults { proto.getValue(it.numberHex) }
        }

        invoke(parent, request, pagination, result).toJson()
      },
    )
  }
}
