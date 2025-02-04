package global.hiber.api.rest.customer.value

import global.hiber.api.rest.customer.value.RestApiAssetValueList.Companion.path
import global.hiber.api.rest.customer.value.RestApiAssetValueList.Request
import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.database.system.asModemNumberHex
import global.hiber.serialization.json.assertJsonEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag(TestTags.Asset)
@Tag(TestTags.Values)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiAssetValuesIT : RestApiValueITBase() {
  val parent = ""
  @Test
  fun `WEB-8061 - asset values`() = withValues(RestApiAssetValueList.route) { token, asset, device ->
    val deviceHex = device.asModemNumberHex
    val expected =
      """
        {
          "values": [
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T20:05:00Z",
                  "textual": "C",
                  "type": "ENUM"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T20:05:00Z",
                  "textual": "The status is C",
                  "type": "TEXT"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T20:05:00Z",
                  "value": 3.5885694E8,
                  "textual": "358856940.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T19:05:00Z",
                  "textual": "B",
                  "type": "ENUM"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T19:05:00Z",
                  "textual": "The status is B",
                  "type": "TEXT"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T19:05:00Z",
                  "value": 3.58856939E8,
                  "textual": "358856939.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T18:05:00Z",
                  "textual": "C",
                  "type": "ENUM"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T18:05:00Z",
                  "textual": "The status is C",
                  "type": "TEXT"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T18:05:00Z",
                  "value": 3.5885694E8,
                  "textual": "358856940.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:35:00Z",
                  "textual": "B",
                  "type": "ENUM"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:35:00Z",
                  "textual": "The status is B",
                  "type": "TEXT"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:35:00Z",
                  "value": 3.58856939E8,
                  "textual": "358856939.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:05:00Z",
                  "textual": "A",
                  "type": "ENUM"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:05:00Z",
                  "textual": "The status is A",
                  "type": "TEXT"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T17:05:00Z",
                  "value": 3.58856938E8,
                  "textual": "358856938.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T16:05:00Z",
                  "textual": "C",
                  "type": "ENUM"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T16:05:00Z",
                  "textual": "The status is C",
                  "type": "TEXT"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T16:05:00Z",
                  "value": 3.5885694E8,
                  "textual": "358856940.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T15:05:00Z",
                  "textual": "B",
                  "type": "ENUM"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T15:05:00Z",
                  "textual": "The status is B",
                  "type": "TEXT"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T15:05:00Z",
                  "value": 3.58856939E8,
                  "textual": "358856939.0000 bar",
                  "type": "PRESSURE"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T14:05:00Z",
                  "textual": "A",
                  "type": "ENUM"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T14:05:00Z",
                  "textual": "The status is A",
                  "type": "TEXT"
              },
              {
                  "asset": {
                      "identifier": "asset-a",
                      "name": "Asset A",
                      "type": "WELL_ANNULUS_A"
                  },
                  "device": {"number": "$deviceHex", "identifier": ""},
                  "time": "2022-01-01T14:05:00Z",
                  "value": 3.58856938E8,
                  "textual": "358856938.0000 bar",
                  "type": "PRESSURE"
              }
          ],
          "_links": {
              "self": {
                  "href": "https://rest.api.test.env.hiber.cloud/values/asset?size=100&page=0"
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

    getAndHead(RestApiAssetValueList.route.path(parent), token) {
      assertJsonEquals(expected, it, debug = true)
    }

    // CALL & VERIFY: select asset
    getAndHead(path(parent), token, Request(asset = listOf(asset))) {
      assertJsonEquals(expected.replace("values/asset?", "values/asset?asset=asset-a&"), it, debug = true)
    }

    // CALL & VERIFY: empty list when different asset is selected
    getAndHead(path(parent), token, Request(asset = listOf("asset-c"))) {
      assertJsonEquals(
        """
        {
            "values": [],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?asset=asset-c&size=100&page=0"
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
    getAndHead(path(parent), token, Request(size = 1, page = 3, type = listOf("pressure"))) {
      assertJsonEquals(
        """
        {
            "values": [
                {
                    "asset": {
                        "identifier": "asset-a",
                        "name": "Asset A",
                        "type": "WELL_ANNULUS_A"
                    },
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "value": 3.58856939E8,
                    "textual": "358856939.0000 bar",
                    "type": "PRESSURE"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=pressure&size=1&page=3"
                },
                "previous": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=pressure&size=1&page=2"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=pressure&size=1&page=4"
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

    getAndHead(path(parent), token, Request(size = 1, page = 3, type = listOf("temperature"))) {
      assertJsonEquals(
        """
        {
            "values": [],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=temperature&size=1&page=3"
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

    getAndHead(path(parent), token, Request(size = 1, page = 3, type = listOf("text"))) {
      assertJsonEquals(
        """
        {
            "values": [
                {
                    "asset": {
                        "identifier": "asset-a",
                        "name": "Asset A",
                        "type": "WELL_ANNULUS_A"
                    },
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "textual": "The status is B",
                    "type": "TEXT"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=text&size=1&page=3"
                },
                "previous": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=text&size=1&page=2"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=text&size=1&page=4"
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

    getAndHead(path(parent), token, Request(size = 1, page = 3, type = listOf("enum"))) {
      assertJsonEquals(
        """
        {
            "values": [
                {
                    "asset": {
                        "identifier": "asset-a",
                        "name": "Asset A",
                        "type": "WELL_ANNULUS_A"
                    },
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "textual": "B",
                    "type": "ENUM"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=enum&size=1&page=3"
                },
                "previous": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=enum&size=1&page=2"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?type=enum&size=1&page=4"
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
    getAndHead(path(parent), token, Request(from = "2022-01-01T17:30:00Z", to = "2022-01-01T17:40:00Z")) {
      assertJsonEquals(
        """
        {
            "values": [
                {
                    "asset": {
                        "identifier": "asset-a",
                        "name": "Asset A",
                        "type": "WELL_ANNULUS_A"
                    },
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "textual": "B",
                    "type": "ENUM"
                },
                {
                    "asset": {
                        "identifier": "asset-a",
                        "name": "Asset A",
                        "type": "WELL_ANNULUS_A"
                    },
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "textual": "The status is B",
                    "type": "TEXT"
                },
                {
                    "asset": {
                        "identifier": "asset-a",
                        "name": "Asset A",
                        "type": "WELL_ANNULUS_A"
                    },
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T17:35:00Z",
                    "value": 3.58856939E8,
                    "textual": "358856939.0000 bar",
                    "type": "PRESSURE"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?from=2022-01-01T17%3A30%3A00Z&to=2022-01-01T17%3A40%3A00Z&size=100&page=0"
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

    // CALL & VERIFY: multiple assets
    val expectedMultiAsset = """
        {
            "values": [
                {
                    "asset": {
                        "identifier": "asset-a",
                        "name": "Asset A",
                        "type": "WELL_ANNULUS_A"
                    },
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T20:05:00Z",
                    "textual": "C",
                    "type": "ENUM"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?asset=asset-a&asset=asset-b&size=1&page=0"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?asset=asset-a&asset=asset-b&size=1&page=1"
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
    getAndHead(path(parent), token, Request(size = 1, asset = listOf("asset-a", "asset-b"))) {
      assertJsonEquals(expectedMultiAsset.trimIndent(), it, debug = true)
    }
    getAndHead(path(parent) + "?asset=asset-a&asset=asset-b", token, Request(size = 1)) {
      assertJsonEquals(expectedMultiAsset, it, debug = true)
    }
    getAndHead(path(parent) + "?asset=asset-a,asset-b", token, Request(size = 1)) {
      assertJsonEquals(
        expectedMultiAsset
          .replace("asset=asset-a&asset=asset-b", "asset=asset-a%2Casset-b"),
        it,
        debug = true,
      )
    }

    // VERIFY: works without specifying any selection
    val expectedNoSelection = """
        {
            "values": [
                {
                    "asset": {
                        "identifier": "asset-a",
                        "name": "Asset A",
                        "type": "WELL_ANNULUS_A"
                    },
                    "device": {"number": "$deviceHex", "identifier": ""},
                    "time": "2022-01-01T20:05:00Z",
                    "textual": "C",
                    "type": "ENUM"
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?size=1&page=0"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/values/asset?size=1&page=1"
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

    getAndHead(path(parent), token, Request(size = 1)) {
      assertJsonEquals(expectedNoSelection, it, debug = true)
    }
  }
}
