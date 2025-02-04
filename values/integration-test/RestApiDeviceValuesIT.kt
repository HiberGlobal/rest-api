package global.hiber.api.rest.customer.value

import global.hiber.api.rest.customer.value.RestApiDeviceValueList.Companion.path
import global.hiber.api.rest.customer.value.RestApiDeviceValueList.Request
import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.database.system.asModemNumberHex
import global.hiber.serialization.json.assertJsonEquals
import io.ktor.http.HttpStatusCode.Companion.PreconditionFailed
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag(TestTags.Modem)
@Tag(TestTags.Values)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiDeviceValuesIT : RestApiValueITBase() {
  val parent = ""
  @Test
  fun `WEB-8061 - device values`() = withValues(RestApiDeviceValueList.route) { token, _, device ->
    val deviceHex = device.asModemNumberHex
    val deviceHexWithoutSpace = device.asModemNumberHex.replace(" ", "")
    val request = Request(device = listOf(deviceHexWithoutSpace))
    val expected =
      """
        {
          "values": [
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T20:05:00Z",
                  "textual": "C",
                  "type": "ENUM"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T20:05:00Z",
                  "textual": "The status is C",
                  "type": "TEXT"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T20:05:00Z",
                  "value": 3.5885694E8,
                  "textual": "358856940.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T19:05:00Z",
                  "textual": "B",
                  "type": "ENUM"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T19:05:00Z",
                  "textual": "The status is B",
                  "type": "TEXT"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T19:05:00Z",
                  "value": 3.58856939E8,
                  "textual": "358856939.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T18:05:00Z",
                  "textual": "C",
                  "type": "ENUM"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T18:05:00Z",
                  "textual": "The status is C",
                  "type": "TEXT"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T18:05:00Z",
                  "value": 3.5885694E8,
                  "textual": "358856940.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:35:00Z",
                  "textual": "B",
                  "type": "ENUM"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:35:00Z",
                  "textual": "The status is B",
                  "type": "TEXT"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:35:00Z",
                  "value": 3.58856939E8,
                  "textual": "358856939.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:05:00Z",
                  "textual": "A",
                  "type": "ENUM"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:05:00Z",
                  "textual": "The status is A",
                  "type": "TEXT"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:05:00Z",
                  "value": 3.58856938E8,
                  "textual": "358856938.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T16:05:00Z",
                  "textual": "C",
                  "type": "ENUM"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T16:05:00Z",
                  "textual": "The status is C",
                  "type": "TEXT"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T16:05:00Z",
                  "value": 3.5885694E8,
                  "textual": "358856940.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T15:05:00Z",
                  "textual": "B",
                  "type": "ENUM"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T15:05:00Z",
                  "textual": "The status is B",
                  "type": "TEXT"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T15:05:00Z",
                  "value": 3.58856939E8,
                  "textual": "358856939.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T14:05:00Z",
                  "textual": "A",
                  "type": "ENUM"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T14:05:00Z",
                  "textual": "The status is A",
                  "type": "TEXT"
              },
              {
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T14:05:00Z",
                  "value": 3.58856938E8,
                  "textual": "358856938.0000 bar",
                  "type": "PRESSURE"
              }
          ],
          "_links": {
              "self": {
                  "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&size=100&page=0"
              }
          },
          "pagination": {
              "size": 100,
              "page": 0,
              "total": 24,
              "approximation": false,
              "totalPages": 1
          }
      }
      """.trimIndent()

    // CALL & VERIFY: select device
    getAndHead(path(parent), token, Request(device = listOf(deviceHexWithoutSpace))) {
      assertJsonEquals(expected, it, debug = true)
    }

    // CALL & VERIFY: invalid device
    getAndHead(
      path(parent),
      token,
      Request(device = listOf("invalid")),
      expectedStatus = PreconditionFailed,
      expectedBody = "Invalid modem number: 'invalid'",
    )

    // CALL & VERIFY: empty list when different device is selected
    getAndHead(path(parent), token, Request(device = listOf("00000000"))) {
      assertJsonEquals(
        """
        {
            "values": [],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=00000000&size=100&page=0"
                }
            },
            "pagination": {
                "size": 100,
                "page": 0,
                "total": 0,
                "approximation": false,
                "totalPages": 0
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    // CALL & VERIFY: numeric value type selection and pagination
    getAndHead(path(parent), token, request.copy(size = 1, page = 3, type = listOf("pressure"))) {
      assertJsonEquals(
        """
        {
            "values": [
                {
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "value": 3.58856939E8,
                    "textual": "358856939.0000 bar",
                    "type": "PRESSURE"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=pressure&size=1&page=3"
                },
                "previous": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=pressure&size=1&page=2"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=pressure&size=1&page=4"
                }
            },
            "pagination": {
                "size": 1,
                "page": 3,
                "total": 8,
                "approximation": false,
                "totalPages": 8
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(path(parent), token, request.copy(size = 1, page = 3, type = listOf("temperature"))) {
      assertJsonEquals(
        """
        {
            "values": [],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=temperature&size=1&page=3"
                }
            },
            "pagination": {
                "size": 1,
                "page": 3,
                "total": 0,
                "approximation": false,
                "totalPages": 0
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(path(parent), token, request.copy(size = 1, page = 3, type = listOf("text"))) {
      assertJsonEquals(
        """
        {
            "values": [
                {
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "textual": "The status is B",
                    "type": "TEXT"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=text&size=1&page=3"
                },
                "previous": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=text&size=1&page=2"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=text&size=1&page=4"
                }
            },
            "pagination": {
                "size": 1,
                "page": 3,
                "total": 8,
                "approximation": false,
                "totalPages": 8
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(path(parent), token, request.copy(size = 1, page = 3, type = listOf("enum"))) {
      assertJsonEquals(
        """
        {
            "values": [
                {
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "textual": "B",
                    "type": "ENUM"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=enum&size=1&page=3"
                },
                "previous": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=enum&size=1&page=2"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&type=enum&size=1&page=4"
                }
            },
            "pagination": {
                "size": 1,
                "page": 3,
                "total": 8,
                "approximation": false,
                "totalPages": 8
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    // CALL & VERIFY: time range selection
    getAndHead(path(parent), token, request.copy(from = "2022-01-01T17:30:00Z", to = "2022-01-01T17:40:00Z")) {
      assertJsonEquals(
        """
        {
            "values": [
                {
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "textual": "B",
                    "type": "ENUM"
                },
                {
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "textual": "The status is B",
                    "type": "TEXT"
                },
                {
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "value": 3.58856939E8,
                    "textual": "358856939.0000 bar",
                    "type": "PRESSURE"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&from=2022-01-01T17%3A30%3A00Z&to=2022-01-01T17%3A40%3A00Z&size=100&page=0"
                }
            },
            "pagination": {
                "size": 100,
                "page": 0,
                "total": 3,
                "approximation": false,
                "totalPages": 1
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    // CALL & VERIFY: multiple devices
    val expectedMultiAsset = """
        {
            "values": [
                {
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T20:05:00Z",
                    "textual": "C",
                    "type": "ENUM"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&device=00000000&size=1&page=0"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?device=$deviceHexWithoutSpace&device=00000000&size=1&page=1"
                }
            },
            "pagination": {
                "size": 1,
                "page": 0,
                "total": 24,
                "approximation": false,
                "totalPages": 24
            }
        }
        """
    getAndHead(path(parent), token, Request(size = 1, device = listOf(deviceHexWithoutSpace, "00000000"))) {
      assertJsonEquals(expectedMultiAsset.trimIndent(), it, debug = true)
    }
    val requestWithoutDevice = Request(size = 1, device = emptyList())
    getAndHead(path(parent) + "?device=$deviceHexWithoutSpace&device=00000000", token, requestWithoutDevice) {
      assertJsonEquals(expectedMultiAsset, it, debug = true)
    }
    getAndHead(path(parent) + "?device=$deviceHexWithoutSpace,00000000", token, requestWithoutDevice) {
      assertJsonEquals(
        expectedMultiAsset
          .replace("device=$deviceHexWithoutSpace&device=00000000", "device=$deviceHexWithoutSpace%2C00000000"),
        it,
        debug = true,
      )
    }

    // VERIFY: works without specifying any selection
    val expectedNoSelection = """
        {
            "values": [
                {
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T20:05:00Z",
                    "textual": "C",
                    "type": "ENUM"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?size=1&page=0"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/device?size=1&page=1"
                }
            },
            "pagination": {
                "size": 1,
                "page": 0,
                "total": 24,
                "approximation": false,
                "totalPages": 24
            }
        }
        """

    getAndHead(path(parent), token, requestWithoutDevice) {
      assertJsonEquals(expectedNoSelection, it, debug = true)
    }
  }
}
