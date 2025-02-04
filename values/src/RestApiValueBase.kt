package global.hiber.api.rest.customer.value

import global.hiber.api.grpc.value.Value
import global.hiber.api.grpc.value.Value.Numeric
import global.hiber.api.grpc.value.Value.ValueCase
import global.hiber.api.grpc.value.ValueServiceApi
import global.hiber.api.ifNotDefault
import global.hiber.api.rest.HAL
import global.hiber.api.rest.PaginationResult
import global.hiber.api.rest.RestApiRoute
import global.hiber.api.rest.asRestApiPaginationResult
import global.hiber.common.asEnumValuesOrOmit
import global.hiber.database.system.AssetIdentifier
import global.hiber.database.system.ModemNumberHex
import global.hiber.database.util.Pagination
import io.ktor.http.Parameters
import io.ktor.server.routing.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface RestApiValueBase {
  val route: RestApiRoute
  fun path(parent: Route): String = route.path(parent)
  fun link(parent: Route, request: Request) = HAL.Link.withDomain(route.path(parent), request)
  fun path(parent: String): String = route.path(parent)
  fun link(parent: String, request: Request) = HAL.Link.withDomain(route.path(parent), request)

  interface Request : Parameters {
    val type: List<String>
    val from: String?
    val to: String?
    val size: Int?
    val page: Int?

    val valueTypes get() = type.map { it.uppercase() }.asEnumValuesOrOmit<Value.Type>()
    val numericValueTypes get() = type.map { it.uppercase() }.asEnumValuesOrOmit<Numeric.Type>()

    fun withPagination(newSize: Int, newPage: Int): Request
  }

  interface Response {
    val values: List<ValueContext>

    @SerialName("_links")
    val links: Links
    val pagination: PaginationResult

    @Serializable data class ValueContext(
      val asset: Asset?,
      val device: Device,
      val time: String,
      val value: Double? = null,
      val textual: String,
      val type: String?,
    ) {
      @Serializable data class Asset(val identifier: AssetIdentifier, val name: String, val type: String)
      @Serializable data class Device(val number: ModemNumberHex, val identifier: String)

      companion object {
        operator fun invoke(value: ValueServiceApi.ValueContext) = ValueContext(
          asset = Asset(identifier = value.asset.identifier, name = value.asset.name, type = value.asset.type.name)
            .takeIf { value.hasAsset() },
          device = Device(number = value.device.number, identifier = value.device.identifier),
          time = value.time.textual,
          value = value.value.numeric.value.ifNotDefault(),
          textual = when (value.value.valueCase) {
            ValueCase.NUMERIC -> value.value.numeric.textual
            ValueCase.TEXT -> value.value.text
            ValueCase.ENUM -> value.value.enum.value
            ValueCase.VALUE_NOT_SET, null -> ""
          },
          type = when (value.value.valueCase) {
            ValueCase.NUMERIC -> value.value.numeric.type.name
            ValueCase.TEXT -> value.value.type.name
            ValueCase.ENUM -> value.value.type.name
            ValueCase.VALUE_NOT_SET, null -> null
          },
        )
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
          request: Request,
          pagination: Pagination,
          paginated: Pagination.Result<*>,
          link: (Request) -> HAL.Link,
        ): Links = Links(
          self = link(request.withPagination(pagination.limit, pagination.page)),
          next = paginated.next?.let { next -> link(request.withPagination(pagination.limit, next.page)) },
          previous = paginated.previous?.let { previous ->
            link(request.withPagination(pagination.limit, previous.page))
          },
        )
      }
    }
  }

  interface CompanionBase<R : Response> {
    val route: RestApiRoute
    fun path(parent: String) = route.path(parent)
    fun link(parent: String, request: Request) = HAL.Link.withDomain(route.path(parent), request)
    fun path(parent: Route?) = route.path(parent)
    fun link(parent: Route?, request: Request) = HAL.Link.withDomain(route.path(parent), request)

    operator fun invoke(
      values: List<Response.ValueContext>,
      links: Response.Links,
      pagination: PaginationResult,
    ): R

    operator fun invoke(
      parent: Route,
      request: Request,
      pagination: Pagination,
      paginated: Pagination.Result<ValueServiceApi.ValueContext>,
    ): R = invoke(
      values = paginated.results.map { Response.ValueContext(it) },
      links = Response.Links(request, pagination, paginated) { link(parent, it) },
      pagination = paginated.asRestApiPaginationResult,
    )
  }
}
