package global.hiber.api.rest.customer.docs

import global.hiber.api.rest.restApiRoute
import io.ktor.server.application.call
import io.ktor.server.response.respondRedirect

val restApiDocs = restApiRoute(
  "/docs",
  restApiRoute("/rels/{rel}") { _, rel ->
    call.respondRedirect("https://docs.api.hiber.cloud/#/REST-API/${rel.get()}")
  },
) { call.respondRedirect("https://docs.api.hiber.cloud/#/REST-API/README") }
