package docs

import global.hiber.api.rest.RestApiRoute
import global.hiber.api.rest.customer.docs.restApiDocs
import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.database.system.test.container.DatabaseIntegrationTestContainer
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.Route
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class CustomerRestApiDocsIT : DatabaseIntegrationTestContainer() {
  val parent: Route? = null

  @Test
  fun `WEB-5937 - docs`() = testApplication {
    routing { restApiDocs(this) }

    val client = createClient { followRedirects = false }

    // main docs
    client.getAndHead(restApiDocs.path(parent), expectedStatus = HttpStatusCode.Found) {}.apply {
      assertEquals("https://docs.api.hiber.cloud/#/REST-API/README", headers["Location"])
    }

    val restApiDocsRel = restApiDocs.children.single() as RestApiRoute.WithPathParameter
    val parent = restApiDocs.path

    // rels
    listOf("modem", "messages", "message-events").forEach {
      client.getAndHead(
        restApiDocsRel.path(parent, it),
        expectedStatus = HttpStatusCode.Found,
      ) {}.apply {
        assertEquals("https://docs.api.hiber.cloud/#/REST-API/$it", headers["Location"])
      }
    }

    // invalid rel will still work
    client.getAndHead(
      restApiDocsRel.path(parent, "invalid"),
      expectedStatus = HttpStatusCode.Found,
    ) {}.apply {
      assertEquals("https://docs.api.hiber.cloud/#/REST-API/invalid", headers["Location"])
    }
  }
}
