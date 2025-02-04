package global.hiber.api.rest.customer.modem.message

import global.hiber.api.rest.HAL
import global.hiber.api.rest.RestApiRoute
import io.ktor.http.Parameters
import io.ktor.server.routing.Route

interface RestApiMessageBase {
  val route: RestApiRoute
  fun path(parent: Route): String = route.path(parent)
  fun link(parent: Route, request: Request) = HAL.Link.withDomain(route.path(parent), request)
  fun path(parent: String): String = route.path(parent)
  fun link(parent: String, request: Request) = HAL.Link.withDomain(route.path(parent), request)

  interface Request : Parameters {
    val from: String?
    val to: String?
    val devices: String?
    val modems: String?
    val tags: String?
    val groups: String?
    val size: Int?
    val page: Int?

    fun withPagination(newSize: Int, newPage: Int): Request
  }
}
