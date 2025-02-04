package global.hiber.api.customer.rest.modem

import global.hiber.api.initializeToken
import global.hiber.api.rest.customer.modem.RestApiDeviceList
import global.hiber.api.rest.customer.modem.RestApiModemList
import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.database.system.asModemNumberHex
import global.hiber.database.system.db
import global.hiber.database.system.initializeModems
import global.hiber.database.system.initializePrimaryOrganization
import global.hiber.database.system.test.container.DatabaseIntegrationTestContainer
import global.hiber.database.system.type.locationOf
import global.hiber.serialization.json.assertJsonEquals
import io.ktor.server.resources.Resources
import io.ktor.server.routing.Route
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag(TestTags.Modem)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiModemListIT : DatabaseIntegrationTestContainer() {
  val parent: Route? = null
  val location = locationOf(1, 1)
  val modems = (1..20).toSet()
  val modemsJson = modems.sorted().map {
    """
    {
      "number": "${it.asModemNumberHex}",
      "organization": "hiber",
      "name": "${it.asModemNumberHex}",
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
    }
    """.trimIndent()
  }

  fun withApi(f: suspend ApplicationTestBuilder.(String) -> Unit): Unit = testApplication {
    val identity = db.action { initializePrimaryOrganization() }
    db.action { initializeModems(modems.size, identity, includeNumbers = modems, location = location) }

    install(Resources)
    routing {
      RestApiModemList.route(this)
      RestApiDeviceList.route(this)
    }

    f(db.transaction { initializeToken(identity).token })
  }

  @Test
  fun `WEB-7150 - modems`() = withApi { token ->
    getAndHead(RestApiModemList.route.path(parent), token) {
      assertJsonEquals(
        """
        {
          "modems": ${modemsJson.joinToString(separator = ",", prefix = "[", postfix = "]")},
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/modems?size=100&page=0"
            }
          },
          "pagination": {"size":100,"page":0,"approximation":false,"total":20,"totalPages":1}
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(RestApiModemList.path(parent), token, RestApiModemList.Request(size = 1, page = 3)) {
      assertJsonEquals(
        """
        {
          "modems": ${modemsJson.drop(3).take(1).joinToString(separator = ",", prefix = "[", postfix = "]")},
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/modems?size=1&page=3"
            },
            "previous": {
              "href": "https://rest.api.test.env.hiber.cloud/modems?size=1&page=2"
            },
            "next": {
              "href": "https://rest.api.test.env.hiber.cloud/modems?size=1&page=4"
            }
          },
          "pagination": {"size":1,"page":3,"approximation":false,"total":20,"totalPages":20}
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    // device endpoint
    getAndHead(RestApiDeviceList.route.path(parent), token) {
      assertJsonEquals(
        """
        {
          "devices": ${modemsJson.joinToString(separator = ",", prefix = "[", postfix = "]")},
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/devices?size=100&page=0"
            }
          },
          "pagination": {"size":100,"page":0,"approximation":false,"total":20,"totalPages":1}
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(RestApiDeviceList.path(parent), token, RestApiDeviceList.Request(size = 1, page = 3)) {
      assertJsonEquals(
        """
        {
          "devices": ${modemsJson.drop(3).take(1).joinToString(separator = ",", prefix = "[", postfix = "]")},
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/devices?size=1&page=3"
            },
            "previous": {
              "href": "https://rest.api.test.env.hiber.cloud/devices?size=1&page=2"
            },
            "next": {
              "href": "https://rest.api.test.env.hiber.cloud/devices?size=1&page=4"
            }
          },
          "pagination": {"size":1,"page":3,"approximation":false,"total":20,"totalPages":20}
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(
      RestApiModemList.path(parent),
      token,
      RestApiModemList.Request(size = 5, page = 6, tags = "test with space"),
    ) {
      assertJsonEquals(
        """
        {
          "modems": [],
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/modems?tags=test+with+space&size=5&page=6"
            }
          },
          "pagination": {"size":5,"page":6,"approximation":false,"total":0,"totalPages":0}
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }
  }
}
