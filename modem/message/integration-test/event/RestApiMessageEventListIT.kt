package global.hiber.api.rest.customer.modem.message.event

import global.hiber.api.rest.customer.modem.message.RestApiMessageITBase
import global.hiber.api.rest.test.getAndHead
import global.hiber.common.tests.TestTags
import global.hiber.serialization.json.assertJsonEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag(TestTags.Messages)
@Tag(TestTags.RestAPI)
@Tag(TestTags.RestAPICustomer)
class RestApiMessageEventListIT : RestApiMessageITBase() {
  val parent = ""

  @Test
  fun `WEB-5938 - message events`() = withMessages(RestApiMessageEventList.route) { token ->
    getAndHead(RestApiMessageEventList.path(parent), token, RestApiMessageEventList.Request(devices = "00000001")) {
      assertJsonEquals(
        """
        {
          "messageEvents": [
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-28T13:00:00Z",
                  "textual": "2021-02-28T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "10",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-28 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-28 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-28T13:00:00Z",
                "textual": "2021-02-28T13:00:00Z",
                "timeZone": "UTC"
              }
            },
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-27T13:00:00Z",
                  "textual": "2021-02-27T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "9",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-27 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-27 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-27T13:00:00Z",
                "textual": "2021-02-27T13:00:00Z",
                "timeZone": "UTC"
              }
            },
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-26T13:00:00Z",
                  "textual": "2021-02-26T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "8",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-26 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-26 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-26T13:00:00Z",
                "textual": "2021-02-26T13:00:00Z",
                "timeZone": "UTC"
              }
            },
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-25T13:00:00Z",
                  "textual": "2021-02-25T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "7",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-25 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-25 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-25T13:00:00Z",
                "textual": "2021-02-25T13:00:00Z",
                "timeZone": "UTC"
              }
            },
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-24T13:00:00Z",
                  "textual": "2021-02-24T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "6",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-24 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-24 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-24T13:00:00Z",
                "textual": "2021-02-24T13:00:00Z",
                "timeZone": "UTC"
              }
            },
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-23T13:00:00Z",
                  "textual": "2021-02-23T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "5",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-23 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-23 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-23T13:00:00Z",
                "textual": "2021-02-23T13:00:00Z",
                "timeZone": "UTC"
              }
            },
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-22T13:00:00Z",
                  "textual": "2021-02-22T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "4",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-22 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-22 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-22T13:00:00Z",
                "textual": "2021-02-22T13:00:00Z",
                "timeZone": "UTC"
              }
            },
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-21T13:00:00Z",
                  "textual": "2021-02-21T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "3",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-21 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-21 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-21T13:00:00Z",
                "textual": "2021-02-21T13:00:00Z",
                "timeZone": "UTC"
              }
            },
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-20T13:00:00Z",
                  "textual": "2021-02-20T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "2",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-20 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-20 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-20T13:00:00Z",
                "textual": "2021-02-20T13:00:00Z",
                "timeZone": "UTC"
              }
            },
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-19T13:00:00Z",
                  "textual": "2021-02-19T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "1",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-19 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-19 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-19T13:00:00Z",
                "textual": "2021-02-19T13:00:00Z",
                "timeZone": "UTC"
              }
            }
          ],
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/message/events?devices=00000001&size=10&page=0"
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
      RestApiMessageEventList.path(parent),
      token,
      RestApiMessageEventList.Request(size = 1, page = 3, devices = "00000001"),
    ) {
      assertJsonEquals(
        """
        {
          "messageEvents": [
            {
              "organization": "hiber",
              "modemNumber": "0000 0001",
              "deviceNumber": "0000 0001",
              "message": {
                "modemNumber": "0000 0001",
                "sentAt": {
                  "timestamp": "2021-02-25T13:00:00Z",
                  "textual": "2021-02-25T13:00:00Z",
                  "timeZone": "UTC"
                },
                "location": {
                  "latitude": 1.0,
                  "longitude": 0.99999,
                  "textual": "[1.0,0.99999]"
                },
                "receivedAt": {
                  "timestamp": "2021-03-01T13:00:00Z",
                  "textual": "2021-03-01T13:00:00Z",
                  "timeZone": "UTC"
                },
                "messageId": "7",
                "sources": [
                  "DIRECT_TO_API"
                ],
                "modemName": "0000 0001"
              },
              "title": "New message from device 0000 0001 was received and processed. It was sent at 2021-02-25 13:00:00Z.",
              "description": "A new message from device 0000 0001 was received and processed.\n\nThe message was sent at 2021-02-25 13:00:00Z with location [1.0,0.99999].\n\nThe body was empty.",
              "time": {
                "timestamp": "2021-02-25T13:00:00Z",
                "textual": "2021-02-25T13:00:00Z",
                "timeZone": "UTC"
              }
            }
          ],
          "_links": {
            "self": {
              "href": "https://rest.api.test.env.hiber.cloud/message/events?devices=00000001&size=1&page=3"
            },
            "previous": {
              "href": "https://rest.api.test.env.hiber.cloud/message/events?devices=00000001&size=1&page=2"
            },
            "next": {
              "href": "https://rest.api.test.env.hiber.cloud/message/events?devices=00000001&size=1&page=4"
            }
          },
          "pagination": {"size":1,"page":3,"approximation":false,"total":10,"totalPages":10}
        }
        """.trimIndent(),
        it,
        debug = true,
      )
    }
  }
}
