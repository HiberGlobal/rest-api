package global.hiber.api.rest.customer.file

import global.hiber.api.initializeToken
import global.hiber.api.rest.test.getAndHead
import global.hiber.auth.Scope
import global.hiber.common.tests.TestTags
import global.hiber.database.system.db
import global.hiber.database.system.file.AddFile
import global.hiber.database.system.file.uploadFiles
import global.hiber.database.system.initializePrimaryOrganization
import global.hiber.database.system.test.container.DatabaseIntegrationTestContainer
import io.ktor.client.HttpClient
import io.ktor.http.HttpStatusCode.Companion.MovedPermanently
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.server.routing.Route
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Tag(TestTags.Asset)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiAssetIT : DatabaseIntegrationTestContainer() {
  val parent: Route? = null

  @Test
  fun `WEB-8173 - file`() = testApplication {
    val identity = db.action { initializePrimaryOrganization() }
    val token = db.transaction { initializeToken(identity).token }

    // SETUP: route
    routing { RestApiFile.route(this) }

    // SETUP: upload a few files
    val fileContent = "test-content"
    val file = db.transaction(Scope.Organization.Read(identity)) {
      uploadFiles(
        setOf(
          AddFile.UploadFile(
            name = "test-file",
            mediaType = "text/plain",
            content = fileContent.toByteArray().toList(),
            url = null,
          ),
        ),
      )
    }.single()
    val url = "https://logo.clearbit.com/hiber.global"
    val link = db.transaction(Scope.Organization.Read(identity)) {
      uploadFiles(
        setOf(
          AddFile.UploadFile(
            name = "test-link",
            mediaType = "image/png",
            content = null,
            url = url,
          ),
        ),
      )
    }.single()

    // CALL & VERIFY: file content
    getAndHead(RestApiFile.route.path(parent, file.identifier), token) {
      assertEquals(fileContent, it)
    }

    // CALL & VERIFY: redirect to url
    HttpClient(client.engine) {
      followRedirects = false
    }.getAndHead(RestApiFile.route.path(parent, link.identifier), token, expectedStatus = MovedPermanently) {}.apply {
      assertEquals(url, headers["Location"])
    }

    // CALL & VERIFY: invalid results in not found
    getAndHead(RestApiFile.route.path(parent, "invalid"), token, expectedStatus = NotFound)
  }
}
