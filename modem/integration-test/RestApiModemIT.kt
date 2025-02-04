package global.hiber.api.customer.rest.modem

import global.hiber.api.initializeToken
import global.hiber.api.rest.customer.modem.RestApiDevice
import global.hiber.api.rest.customer.modem.RestApiModem
import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.database.system.ModemNumber
import global.hiber.database.system.asModemNumberHex
import global.hiber.database.system.db
import global.hiber.database.system.initializeModems
import global.hiber.database.system.initializePrimaryOrganization
import global.hiber.database.system.test.container.DatabaseIntegrationTestContainer
import global.hiber.database.system.type.locationOf
import global.hiber.serialization.json.assertJsonEquals
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.Route
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag(TestTags.Modem)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiModemIT : DatabaseIntegrationTestContainer() {
  val parent: Route? = null
  val location = locationOf(1, 1)

  fun withApi(f: suspend ApplicationTestBuilder.(String, ModemNumber) -> Unit): Unit = testApplication {
    val identity = db.action { initializePrimaryOrganization() }
    val modem = db.action { initializeModems(1, identity, location = location).single() }

    routing {
      RestApiModem.route(this)
      RestApiDevice.route(this)
    }

    f(db.transaction { initializeToken(identity).token }, modem)
  }

  @Test
  fun `WEB-5938 - modem`() = withApi { token, modem ->
    val modemNumberHex = modem.asModemNumberHex
    getAndHead(RestApiModem.route.path(parent, modemNumberHex), token) {
      assertJsonEquals(
        """
        {
          "modem": {
            "number": "$modemNumberHex",
            "organization": "hiber",
            "name": "$modemNumberHex",
            "location": {
              "latitude": 1.0,
              "longitude": 1.0,
              "textual": "[1.0,1.0]"
            },
            "lifecycle": "INSTALLED",
            "healthLevel": {
              "level": "Healthy",
              "color": "#11E07F",
              "colorData": {
                "fill": "#1FFF96",
                "markerBackground": "#F54C4C",
                "markerForeground": "#FFFFFF",
                "text": "#11E07F"
              },
              "severity": "1"
            },
            "notes": "",
            "tags": []
          },
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/modem/${modemNumberHex.replace(" ", "")}"
            }
          }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(RestApiModem.route.path(parent, "00000000"), token, expectedStatus = HttpStatusCode.NotFound)
    getAndHead(
      RestApiModem.route.path(parent, "invalid"),
      token,
      expectedStatus = HttpStatusCode.PreconditionFailed,
      expectedBody = "Invalid modem number: 'invalid'",
    )

    // device endpoint
    getAndHead(RestApiDevice.route.path(parent, modemNumberHex), token) {
      assertJsonEquals(
        """
        {
          "device": {
            "number": "$modemNumberHex",
            "organization": "hiber",
            "name": "$modemNumberHex",
            "location": {
              "latitude": 1.0,
              "longitude": 1.0,
              "textual": "[1.0,1.0]"
            },
            "lifecycle": "INSTALLED",
            "healthLevel": {
              "level": "Healthy",
              "color": "#11E07F",
              "colorData": {
                "fill": "#1FFF96",
                "markerBackground": "#F54C4C",
                "markerForeground": "#FFFFFF",
                "text": "#11E07F"
              },
              "severity": "1"
            },
            "notes": "",
            "tags": []
          },
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/device/${modemNumberHex.replace(" ", "")}"
            }
          }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(RestApiDevice.route.path(parent, "00000000"), token, expectedStatus = HttpStatusCode.NotFound)
    getAndHead(
      RestApiDevice.route.path(parent, "invalid"),
      token,
      expectedStatus = HttpStatusCode.PreconditionFailed,
      expectedBody = "Invalid modem number: 'invalid'",
    )
  }
}
