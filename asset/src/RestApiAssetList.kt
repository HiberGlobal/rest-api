package global.hiber.api.rest.customer.asset

import global.hiber.api.customer.asset.asDb
import global.hiber.api.customer.asset.asProto
import global.hiber.api.grpc.asset.AssetApi.Asset
import global.hiber.api.rest.HAL
import global.hiber.api.rest.PaginationResult
import global.hiber.api.rest.RestApiResponse
import global.hiber.api.rest.asRestApiPaginationResult
import global.hiber.api.rest.customer.asset.RestApiAsset.Asset.Companion.asRestApiAsset
import global.hiber.api.rest.restApiResource
import global.hiber.api.rest.toJson
import global.hiber.asset.AssetSelection
import global.hiber.asset.assets
import global.hiber.auth.Assets
import global.hiber.common.Try
import global.hiber.common.iterables.mapToSet
import global.hiber.common.requireNotNull
import global.hiber.database.system.db
import global.hiber.database.system.file.getFilesNoContent
import global.hiber.database.system.type.filter.Filter
import global.hiber.database.util.Pagination
import global.hiber.tag.TagSelection
import io.ktor.http.Parameters
import io.ktor.resources.Resource
import io.ktor.server.routing.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RestApiAssetList(
  val assets: List<RestApiAsset.Asset>,

  @SerialName("_links")
  val links: Links,
  val pagination: PaginationResult,
) : RestApiResponse {
  @Resource(location) data class Request(
    val tags: String? = null,
    val groups: String? = null,
    val type: List<String> = emptyList(),
    val size: Int = 0,
    val page: Int = 0,
  ) : Parameters by HAL.Link.parameters(
      "tags" to tags,
      "groups" to groups,
      "type" to type,
      "size" to size,
      "page" to page,
    ) {
    fun withPagination(newSize: Int, newPage: Int) = copy(size = newSize, page = newPage)

    companion object {
      val allowedParameters = setOf("tags", "groups", "type", "size", "page")
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
    const val location: String = "/assets"
    fun path(parent: Route?) = route.path(parent)
    fun link(parent: Route?, request: Request) = HAL.Link.withDomain(path(parent), request)

    operator fun invoke(
      parent: Route,
      request: Request,
      pagination: Pagination,
      paginated: Pagination.Result<RestApiAsset.Asset>,
    ) = RestApiAssetList(
      assets = paginated.results,
      links = Links(parent, request, pagination, paginated),
      pagination = paginated.asRestApiPaginationResult,
    )

    val route = restApiResource<Request>(
      path = location,
      allowedParameters = Request.allowedParameters,
      head = { _, _, request ->
        request.type.forEach {
          Try { Asset.Type.valueOf(it) }.value?.asDb.requireNotNull("Unknown asset type: $it")
        }
      },
      get = { parent, _, request ->
        // selection and pagination
        val selection = AssetSelection(
          types = Filter.AssetTypes(
            include = request.type.mapToSet {
              Try { Asset.Type.valueOf(it) }.value?.asDb.requireNotNull("Unknown asset type: $it")
            },
          ),
          filterByTags = TagSelection(
            names = request.tags?.split(",")?.mapToSet { it.trim() }.orEmpty() +
              request.groups?.split(",")?.mapToSet { it.trim() }.orEmpty(),
          ),
        )
        val pagination = AssetSelection.Pagination(size = request.size, page = request.page)

        // do the actual call
        val result = db.read(Assets()) {
          val paginated = assets(selection, pagination, sort = listOf(AssetSelection.Sort.NAME_ASC))
          val proto = paginated.results
            .map { it.asProto { getFilesNoContent(it) } }
            .map { it.asRestApiAsset }
            .associateBy { it.identifier }
          paginated.withExpandedResults { proto.getValue(it.identifier) }
        }

        RestApiAssetList(parent, request, pagination, result).toJson()
      },
    )
  }
}
