package global.hiber.api.rest.customer.modem.message

import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.serialization.json.assertJsonEquals
import io.ktor.http.HttpStatusCode
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@Tag(TestTags.Messages)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiMessageListMessageIT : RestApiMessageITBase() {
  val parent = ""
  @Test
  fun `WEB-5938 - messages`() = withMessages(RestApiMessageList.route) { token ->
    getAndHead(RestApiMessageList.path(parent), token, RestApiMessageList.Request(devices = "00000001")) {
      assertJsonEquals(
        """
        {
          "messages": [
            {
              "messageId": "10",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-28T13:00:00Z",
                "textual": "2021-02-28T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
              "messageId": "9",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-27T13:00:00Z",
                "textual": "2021-02-27T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
              "messageId": "8",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-26T13:00:00Z",
                "textual": "2021-02-26T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
              "messageId": "7",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-25T13:00:00Z",
                "textual": "2021-02-25T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
              "messageId": "6",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-24T13:00:00Z",
                "textual": "2021-02-24T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
              "messageId": "5",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-23T13:00:00Z",
                "textual": "2021-02-23T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
              "messageId": "4",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-22T13:00:00Z",
                "textual": "2021-02-22T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
              "messageId": "3",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-21T13:00:00Z",
                "textual": "2021-02-21T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
              "messageId": "2",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-20T13:00:00Z",
                "textual": "2021-02-20T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            },
            {
              "messageId": "1",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-19T13:00:00Z",
                "textual": "2021-02-19T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            }
          ],
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/messages?devices=00000001&size=10&page=0"
            }
          },
          "pagination": {"size":10,"page":0,"approximation":false,"total":10,"totalPages":1}
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(
      RestApiMessageList.path(parent),
      token,
      RestApiMessageList.Request(size = 1, page = 3, devices = "00000001"),
    ) {
      assertJsonEquals(
        """
        {
          "messages": [
            {
              "messageId": "7",
              "modemNumber": "0000 0001",
              "modemName": "0000 0001",
              "sentAt": {
                "timestamp": "2021-02-25T13:00:00Z",
                "textual": "2021-02-25T13:00:00Z",
                "timeZone": "UTC"
              },
              "receivedAt": {
                "timestamp": "2021-03-01T13:00:00Z",
                "textual": "2021-03-01T13:00:00Z",
                "timeZone": "UTC"
              },
              "location": {
                "latitude": 1.0,
                "longitude": 0.99999,
                "textual": "[1.0,0.99999]"
              }
            }
          ],
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/messages?devices=00000001&size=1&page=3"
            },
            "previous": {
              "href": "https://rest.api.test.env.hiber.cloud/messages?devices=00000001&size=1&page=2"
            },
            "next": {
              "href": "https://rest.api.test.env.hiber.cloud/messages?devices=00000001&size=1&page=4"
            }
          },
          "pagination": {"size":1,"page":3,"approximation":false,"total":10,"totalPages":10}
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(
      RestApiMessageList.path(parent),
      token,
      RestApiMessageList.Request(size = 1, page = 0, devices = "00000002"),
    ) {
      assertJsonEquals(
        """
        {
            "messages": [
                {
                    "messageId": "20",
                    "modemNumber": "0000 0002",
                    "modemName": "0000 0002",
                    "sentAt": {
                        "timestamp": "2021-02-28T13:00:00Z",
                        "timeZone": "UTC",
                        "textual": "2021-02-28T13:00:00Z"
                    },
                    "receivedAt": {
                        "timestamp": "2021-03-01T13:00:00Z",
                        "timeZone": "UTC",
                        "textual": "2021-03-01T13:00:00Z"
                    },
                    "location": {
                        "latitude": 1.0,
                        "longitude": 0.99999,
                        "textual": "[1.0,0.99999]"
                    }
                }
            ],
            "_links": {
                "self": {
                    "href": "https://rest.api.test.env.hiber.cloud/messages?devices=00000002&size=1&page=0"
                },
                "next": {
                    "href": "https://rest.api.test.env.hiber.cloud/messages?devices=00000002&size=1&page=1"
                }
            },
            "pagination": {
                "size": 1,
                "page": 0,
                "total": 10,
                "approximation": false,
                "totalPages": 10
            }
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }

    getAndHead(
      RestApiMessageList.path(parent) + "?device=00000002", // invalid parameter
      token,
      RestApiMessageList.Request(size = 1, page = 0, devices = "00000002"),
      expectedStatus = HttpStatusCode.PreconditionFailed,
    ) {
      assertEquals("Unknown parameter: device", it)
    }
  }
}
