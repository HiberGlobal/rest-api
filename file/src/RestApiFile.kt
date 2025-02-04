package global.hiber.api.rest.customer.file

import global.hiber.api.rest.RestApiRoute
import global.hiber.api.rest.restApiRouteCustom
import global.hiber.auth.Scope
import global.hiber.database.system.db
import global.hiber.database.system.file.FileIdentifier
import global.hiber.database.system.file.getFileWithContent
import global.hiber.environment.environment
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.response.respondRedirect

object RestApiFile {
  val route: RestApiRoute.WithPathParameter = restApiRouteCustom(
    environment.paths.restApi.filePath("{identifier}"),
    head = { _, call, identifier -> call.respondFile(this, identifier, allowContent = false) },
    get = { _, call, identifier -> call.respondFile(this, identifier, allowContent = true) },
  )

  private suspend fun ApplicationCall.respondFile(
    organization: Scope.Organization,
    identifier: FileIdentifier,
    allowContent: Boolean,
  ) {
    val file = db.read(organization) { getFileWithContent(identifier) }

    when {
      // when the file is a url, redirect to it
      file?.url != null -> respondRedirect(file.url!!, permanent = true)

      // when the file has content, return it if allowed
      file?.content != null -> when {
        allowContent -> respondBytes(
          bytes = file.content!!,
          status = HttpStatusCode.OK,
          contentType = ContentType.parse(file.mediaType),
        )

        else -> respond(HttpStatusCode.OK)
      }

      // otherwise, not found
      else -> respond(HttpStatusCode.NotFound)
    }
  }
}
