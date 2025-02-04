package global.hiber.api.rest.customer.asset

import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.database.system.asModemNumberHex
import global.hiber.serialization.json.assertJsonEquals
import io.ktor.http.HttpStatusCode
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag(TestTags.Asset)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiAssetIT : RestApiAssetTestBase() {
  val asset = "asset-c"

  @Test
  fun `WEB-8061 - asset`() = withApi { token, previousDevice, currentDevice ->
    routing { RestApiAsset.route(this) }

    getAndHead(RestApiAsset.route.path(parent, asset), token) {
      assertJsonEquals(
        """
        {
            "asset": {
                "organization": "hiber",
                "identifier": "$asset",
                "name": "Asset C",
                "type": "WELL_ANNULUS_C",
                "description": "",
                "notes": "",
                "devices": [
                    {
                        "number": "${currentDevice.asModemNumberHex}",
                        "name": "${currentDevice.asModemNumberHex}",
                        "type": "Pressure Sensor-1 EU",
                        "health": "Healthy",
                        "numericValueTypes": [PRESSURE],
                        "assignmentStart": "${currentTimeRange.start}"
                    }
                ],
                "inactiveDevices": [
                    {
                        "number": "${previousDevice.asModemNumberHex}",
                        "type": "Temperature Sensor-1 EU",
                        "numericValueTypes": [TEMPERATURE],
                        "assignmentStart": "${previousTimeRange.start}",
                        "assignmentEnd": "${previousTimeRange.endInclusive}"
                    }
                ],
                "tags": []
            },
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/asset/$asset"
                }
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(RestApiAsset.route.path(parent, "invalid"), token, expectedStatus = HttpStatusCode.NotFound)
  }
}
