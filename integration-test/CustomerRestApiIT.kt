import global.hiber.api.initializeToken
import global.hiber.api.rest.RestApiRoute.Companion.allRoutes
import global.hiber.api.rest.customer.customerRestApi
import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.database.system.asModemNumberHex
import global.hiber.database.system.db
import global.hiber.database.system.initializeModems
import global.hiber.database.system.initializePrimaryOrganization
import global.hiber.database.system.test.container.DatabaseIntegrationTestContainer
import global.hiber.database.system.type.locationOf
import io.ktor.server.resources.Resources
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class CustomerRestApiIT : DatabaseIntegrationTestContainer() {
  val expectedRoutes = listOf(
    // asset endpoints
    "/api/asset/{identifier}/(method:GET)",
    "/api/asset/{identifier}/(method:HEAD)",
    // (locations produce weird routes)
    "/api/assets/[tags?]/[groups?]/[type?]/[size?]/[page?]/(method:GET)",
    "/api/assets/[tags?]/[groups?]/[type?]/[size?]/[page?]/(method:HEAD)",

    // device endpoints
    "/api/device/{number}/(method:GET)",
    "/api/device/{number}/(method:HEAD)",
    "/api/devices/[tag?]/[type?]/[size?]/[page?]/(method:GET)",
    "/api/devices/[tag?]/[type?]/[size?]/[page?]/(method:HEAD)",

    // docs endpoints
    "/api/docs/(method:GET)",
    "/api/docs/(method:HEAD)",
    "/api/docs/rels/{rel}/(method:GET)",
    "/api/docs/rels/{rel}/(method:HEAD)",

    // message endpoints
    // (locations produce weird routes)
    "/api/message/events/[from?]/[to?]/[devices?]/[modems?]/[tags?]/[groups?]/[size?]/[page?]/(method:GET)",
    "/api/message/events/[from?]/[to?]/[devices?]/[modems?]/[tags?]/[groups?]/[size?]/[page?]/(method:HEAD)",
    "/api/messages/[from?]/[to?]/[devices?]/[modems?]/[tags?]/[groups?]/[size?]/[page?]/(method:GET)",
    "/api/messages/[from?]/[to?]/[devices?]/[modems?]/[tags?]/[groups?]/[size?]/[page?]/(method:HEAD)",

    // modem endpoints
    "/api/modem/{number}/(method:GET)",
    "/api/modem/{number}/(method:HEAD)",
    // (locations produce weird routes)
    "/api/modems/[tags?]/[groups?]/[size?]/[page?]/(method:GET)",
    "/api/modems/[tags?]/[groups?]/[size?]/[page?]/(method:HEAD)",

    // value endpoints
    "/api/values/asset/[asset?]/[type?]/[from?]/[to?]/[size?]/[page?]/(method:GET)",
    "/api/values/asset/[asset?]/[type?]/[from?]/[to?]/[size?]/[page?]/(method:HEAD)",
    "/api/values/device/[device?]/[type?]/[from?]/[to?]/[size?]/[page?]/(method:GET)",
    "/api/values/device/[device?]/[type?]/[from?]/[to?]/[size?]/[page?]/(method:HEAD)",

    // file
    "/api/file/{identifier}/(method:GET)",
    "/api/file/{identifier}/(method:HEAD)",
  ).sorted()

  @Test
  fun `rest api routes`() = testApplication {
    val identity = db.action { initializePrimaryOrganization() }
    val modem = db.action { initializeModems(1, identity, location = locationOf(1, 1)) }.single()
    val modemHex = modem.asModemNumberHex
    val token = db.transaction { initializeToken(identity).token }

    install(Resources)
    routing {
      customerRestApi()
      assertEquals(expectedRoutes, allRoutes().sorted())
    }

    // VERIFY: endpoints are available
    getAndHead("api/modem/${modem.asModemNumberHex.replace(" ", "")}", token) { assertContains(it, modemHex) }
    listOf(
      "/api/modems",
      "/api/messages",
      "/api/message/events",
    ).forEach { getAndHead(it, token) { assertNotEquals("", it) } }
  }
}
