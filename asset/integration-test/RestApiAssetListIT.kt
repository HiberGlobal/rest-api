package global.hiber.api.rest.customer.asset

import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.database.system.asModemNumberHex
import global.hiber.serialization.json.assertJsonEquals
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.Resources
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag(TestTags.Asset)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiAssetListIT : RestApiAssetTestBase() {
  @Test
  fun `WEB-7150 - assets`() = withApi { token, previousDevice, currentDevice ->
    install(Resources)
    routing { RestApiAssetList.route(this) }

    val assetsJson =
      """
      {
          "assets": [
              {
                  "organization": "hiber",
                  "identifier": "asset-a",
                  "name": "Asset A",
                  "type": "WELL_ANNULUS_A",
                  "description": "",
                  "notes": "",
                  "devices": [
                      {
                          "number": "${currentDevice.asModemNumberHex}",
                          "name": "${currentDevice.asModemNumberHex}",
                          "type": "Pressure Sensor-1 EU",
                          "numericValueTypes": [PRESSURE],
                          "health": "Healthy",
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
              {
                  "organization": "hiber",
                  "identifier": "asset-b",
                  "name": "Asset B",
                  "type": "WELL_ANNULUS_B",
                  "description": "",
                  "notes": "",
                  "devices": [
                      {
                          "number": "${currentDevice.asModemNumberHex}",
                          "name": "${currentDevice.asModemNumberHex}",
                          "type": "Pressure Sensor-1 EU",
                          "numericValueTypes": [PRESSURE],
                          "health": "Healthy",
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
              {
                  "organization": "hiber",
                  "identifier": "asset-c",
                  "name": "Asset C",
                  "type": "WELL_ANNULUS_C",
                  "description": "",
                  "notes": "",
                  "devices": [
                      {
                          "number": "${currentDevice.asModemNumberHex}",
                          "name": "${currentDevice.asModemNumberHex}",
                          "type": "Pressure Sensor-1 EU",
                          "numericValueTypes": [PRESSURE],
                          "health": "Healthy",
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
              {
                  "organization": "hiber",
                  "identifier": "asset-d",
                  "name": "Asset D",
                  "type": "WELL_ANNULUS_D",
                  "description": "",
                  "notes": "",
                  "devices": [
                      {
                          "number": "${currentDevice.asModemNumberHex}",
                          "name": "${currentDevice.asModemNumberHex}",
                          "type": "Pressure Sensor-1 EU",
                          "numericValueTypes": [PRESSURE],
                          "health": "Healthy",
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
              {
                  "organization": "hiber",
                  "identifier": "asset-e",
                  "name": "Asset E",
                  "type": "WELL_TUBING_HEAD",
                  "description": "",
                  "notes": "",
                  "devices": [
                      {
                          "number": "${currentDevice.asModemNumberHex}",
                          "name": "${currentDevice.asModemNumberHex}",
                          "type": "Pressure Sensor-1 EU",
                          "numericValueTypes": [PRESSURE],
                          "health": "Healthy",
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
              }
          ],
          "_links": {
              "self": {
                  "href": "https://rest.api.test.env.hiber.cloud/assets?size=100&page=0"
              }
          },
          "pagination": {
              "size": 100,
              "page": 0,
              "total": 5,
              "approximation": false,
              "totalPages": 1
          }
      }
      """.trimIndent()

    getAndHead(RestApiAssetList.route.path(parent), token) { assertJsonEquals(assetsJson, it, debug = true) }

    getAndHead(RestApiAssetList.path(parent), token, RestApiAssetList.Request(size = 1, page = 3)) {
      assertJsonEquals(
        """
        {
            "assets": [
                {
                    "organization": "hiber",
                    "identifier": "asset-d",
                    "name": "Asset D",
                    "type": "WELL_ANNULUS_D",
                    "description": "",
                    "notes": "",
                    "devices": [
                        {
                            "number": "${currentDevice.asModemNumberHex}",
                            "name": "${currentDevice.asModemNumberHex}",
                            "type": "Pressure Sensor-1 EU",
                            "numericValueTypes": [PRESSURE],
                            "health": "Healthy",
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
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/assets?size=1&page=3"
                },
                "previous": {
                    "href": "https://rest.api.test.env.hiber.cloud/assets?size=1&page=2"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/assets?size=1&page=4"
                }
            },
            "pagination": {
                "size": 1,
                "page": 3,
                "total": 5,
                "approximation": false,
                "totalPages": 5
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(RestApiAssetList.path(parent), token, RestApiAssetList.Request(type = listOf("WELL_ANNULUS_D"))) {
      assertJsonEquals(
        """
        {
            "assets": [
                {
                    "organization": "hiber",
                    "identifier": "asset-d",
                    "name": "Asset D",
                    "type": "WELL_ANNULUS_D",
                    "description": "",
                    "notes": "",
                    "devices": [
                        {
                            "number": "${currentDevice.asModemNumberHex}",
                            "name": "${currentDevice.asModemNumberHex}",
                            "type": "Pressure Sensor-1 EU",
                            "numericValueTypes": [PRESSURE],
                            "health": "Healthy",
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
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/assets?type=WELL_ANNULUS_D&size=100&page=0"
                }
            },
            "pagination": {
                "size": 100,
                "page": 0,
                "total": 1,
                "approximation": false,
                "totalPages": 1
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(
      RestApiAssetList.path(parent),
      token,
      RestApiAssetList.Request(type = listOf("WELL_ANNULUS_X")),
      expectedStatus = HttpStatusCode.PreconditionFailed,
      expectedBody = "Unknown asset type: WELL_ANNULUS_X",
    )
  }
}
