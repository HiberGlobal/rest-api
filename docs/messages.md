# /messages{?from,to,modems,devices,tags,groups,size,page}

List the messages for a modem, or the whole organization.

## Usage

This endpoint is available at
[rest.api.hiber.cloud/messages{?from,to,modems,devices,tags,groups,size,page}](https://rest.api.hiber.cloud/messages),
with a number of query parameters to select the messages to return.

The query parameters are:
- `from` (timestamp): starting time for a time range, to list messages for a specific time
- `to` (timestamp): end time for a time range, to list messages for a specific time
- `modems`, `devices` (text, default all): list messages for the given modem(s) only, comma-separated list of modem numbers
- `tags`, `groups` (text): list messages for modems with the given tag(s) only, comma-separated list of tag names
- `size` (integer, default 10): the amount of messages to return per page
- `page` (integer, default 0): the page to return

Don't forget the auth token as described in the main [readme](../README.md#auth).

Since modem numbers are often "AAAA BBBB", the space can be omitted when using this call (and anywhere else in the API).

We support a number of different timestamp formats, but regardless of input we output time as
[ISO-8601](https://www.iso.org/iso-8601-date-and-time-format.html) `YYYY-MM-DDTHH:mm:ss.sssZ`.

Textual shortcuts for timestamps are also supported, like:
  - "now": converted to the request time
  - a duration as an offset of now, i.e. "-10h" or "PT-10h": converted to now + offset, so -10h is 10 hours before now
    - For example, to fetch messages in the past hour, a request could use `from=-1h&to=now`.

### Output format

The output is an object with two fields.

The `messages` field is a json array containing messages, which are a json version of the `ModemMessage` object
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/md/modem.md#modemmessage),
though some fields may be omitted for simplicity.

The `pagination` field is a simple object with some pagination-related information, like the total amount of results
and the current page number (starting from 0).

The other field is a `_links` field, for pagination.

### Example

For the url `https://rest.api.hiber.cloud/messages?devices=ABCD0123&size=2`

```
curl https://rest.api.hiber.cloud/messages\?devices\=ABCD0123\&size\=2 -H "Authorization: Bearer <token>"
{
    "messages": [
        {
            "messageId": "1722097",
            "modemNumber": "ABCD 0123",
            "modemName": "ABCD 0123",
            "modemIdentifier": "00 00 00 00 00 00 00 00",
            "sentAt": {
                "timestamp": "2022-05-10T07:09:48Z",
                "textual": "2022-05-10T07:09:48Z"
            },
            "receivedAt": {
                "timestamp": "2022-05-10T07:09:49Z",
                "textual": "2022-05-10T07:09:49Z"
            },
            "location": {
                "latitude": 1.2345,
                "longitude": 1.2345,
                "textual": "[1.2345,1.2345]"
            },
            "body": "91aAP/Fi2r03aV+Xor/vRQnPSuqvumIDjAr8LVvq4ac=",
            "bodyBytes": {
                "bytes": "91aAP/Fi2r03aV+Xor/vRQnPSuqvumIDjAr8LVvq4ac=",
                "hex": "F756803FF162DABD37695F97A2BFEF4509CF4AEAAFBA62038C0AFC2D5BEAE1A7"
            },
            "bodyParsed": [
                {
                    "parserName": "example: 8x float parser",
                    "result": {
                        "first": 1.065375479E9,
                        "second": 3.185206001E9,
                        "third": 2.539612471E9,
                        "fourth": 1.173340066E9,
                        "fifth": 3.930771209E9,
                        "sixth": 5.6801967E7,
                        "seventh": 7.71492492E8,
                        "eighth": 2.816600667E9
                    },
                    "parserIdentifier": "message-body-parser-14b865d4d34bc4a2505938b87061f28c"
                }
            ],
            "bodyParsedSuccessfully": true,
            "viaGatewayMessage": "1721887",
            "bodyFields": {
                "first": 1.065375479E9,
                "second": 3.185206001E9,
                "third": 2.539612471E9,
                "fourth": 1.173340066E9,
                "fifth": 3.930771209E9,
                "sixth": 5.6801967E7,
                "seventh": 7.71492492E8,
                "eighth": 2.816600667E9
            },
            "tags": [
                {
                    "id": "3",
                    "label": {
                        "name": "my group",
                        "type": "group"
                    }
                }
            ],
            "tagNames": [
                "my group"
            ]
        },
        {
            "messageId": "1722114",
            "modemNumber": "ABCD 0123",
            "modemName": "ABCD 0123",
            "modemIdentifier": "00 00 00 00 00 00 00 00",
            "sentAt": {
                "timestamp": "2022-05-10T07:09:48Z",
                "textual": "2022-05-10T07:09:48Z"
            },
            "receivedAt": {
                "timestamp": "2022-05-10T07:09:49Z",
                "textual": "2022-05-10T07:09:49Z"
            },
            "location": {
                "latitude": 1.2345,
                "longitude": 1.2345,
                "textual": "[1.2345,1.2345]"
            },
            "bodyBytes": {
            },
            "viaGatewayMessage": "1721937",
            "tags": [
                {
                    "id": "3",
                    "label": {
                        "name": "my group",
                        "type": "group"
                    }
                }
            ],
            "tagNames": [
                "my group"
            ]
        }
    ],
    "_links": {
        "self": {
            "href": "https://rest.api.hiber.cloud/messages?devices=ABCD0123&size=2&page=0"
        },
        "next": {
            "href": "https://rest.api.hiber.cloud/messages?devices=ABCD0123&size=2&page=1"
        }
    }
}
```
