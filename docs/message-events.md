# /message/events{?from,to,modems,devices,tags,groups,size,page}

List the message events for a modem, or the whole organization.

## Usage

This endpoint is available at
[rest.api.hiber.cloud/message/events{?from,to,modems,devices,tags,groups,size,page}](https://rest.api.hiber.cloud/message/events),
with a number of query parameters to select the message events to return.

The query parameters are:
- `from`  (timestamp): starting time for a time range, to list message events for a specific time
- `to` (timestamp): end time for a time range, to list message events for a specific time
- `modems`, `devices` (text, default all): list message events for the given modem(s) only, comma-separated list of modem numbers
- `tags`, `groups` (text): list message events for modems with the given tag(s) only, comma-separated list of tag names
- `size` (integer, default 10): the amount of message events to return per page
- `page` (integer, default 0): the page to return

Don't forget the auth token as described in the main [readme](../README.md#auth).

Since modem numbers are often "AAAA BBBB", the space can be omitted when using this call (and anywhere else in the API).

We support a number of different timestamp formats, but regardless of input we output time as
[ISO-8601](https://www.iso.org/iso-8601-date-and-time-format.html) `YYYY-MM-DDTHH:mm:ss.sssZ`.

### Output format

The output is an object with two fields.

The `messageEvents` field is a json array containing messages, which are a json version of
the `ModemEvent.MessageEvent.ModemMessageReceivedEvent` object
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/event.md#eventmodemeventmessageeventmodemmessagereceivedevent).

The other field is a `_links` field, as described in [readme](../README.md#hal).

### Example

For the url `https://rest.api.hiber.cloud/message/events?devices=ABCD0123&size=2`

```
curl https://rest.api.hiber.cloud/message/events\?devices\=ABCD0123\&size\=2 -H "Authorization: Bearer <token>"
{
    "messageEvents": [
        {
            "organization": "hiber",
            "modemNumber": "ABCD 0123",
            "message": {
                "modemNumber": "ABCD 0123",
                "sentAt": {
                    "timestamp": "2022-05-10T08:09:48Z",
                    "textual": "2022-05-10T08:09:48Z"
                },
                "location": {
                    "latitude": 1.2345,
                    "longitude": 1.2345,
                    "textual": "[1.2345,1.2345]"
                },
                "body": "E1nfozK2Y5IzfdH2B6M18gVmwve21mL4giwG3tQw8bE=",
                "receivedAt": {
                    "timestamp": "2022-05-10T08:12:02.302532Z",
                    "textual": "2022-05-10T08:12:02.302532Z"
                },
                "messageId": "1744298",
                "bodyBytes": {
                    "bytes": "E1nfozK2Y5IzfdH2B6M18gVmwve21mL4giwG3tQw8bE=",
                    "hex": "1359DFA332B66392337DD1F607A335F20566C2F7B6D662F8822C06DED430F1B1"
                },
                "sources": [
                    "SIMULATION"
                ],
                "bodyParsed": [
                    {
                        "parserId": 3,
                        "parserName": "example: 8x float parser",
                        "result": {
                            "first": 2.749323539E9,
                            "second": 2.456008242E9,
                            "third": 4.140924211E9,
                            "fourth": 4.063601415E9,
                            "fifth": 4.156712453E9,
                            "sixth": 4.167227062E9,
                            "seventh": 3.724946562E9,
                            "eighth": 2.985373908E9
                        },
                        "parserIdentifier": "message-body-parser-14b865d4d34bc4a2505938b87061f28c"
                    },
                ],
                "bodyParsedSuccessfully": true,
                "bodyFields": {
                    "first": 2.749323539E9,
                    "second": 2.456008242E9,
                    "third": 4.140924211E9,
                    "fourth": 4.063601415E9,
                    "fifth": 4.156712453E9,
                    "sixth": 4.167227062E9,
                    "seventh": 3.724946562E9,
                    "eighth": 2.985373908E9
                },
                "modemName": "ABCD 0123",
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
            "tags": [
                {
                    "id": "3",
                    "label": {
                        "name": "my group",
                        "type": "group"
                    }
                }
            ],
            "title": "New message from modem ABCD 0123 was received and processed. It was sent at 2022-05-10 08:09:48Z.",
            "description": "A new message from modem ABCD 0123 was received and processed.\n\nThe message was sent at 2022-05-10 08:09:48Z with location [1.2345,1.2345].\n\nThe body (hex) was: 1359DFA332B66392337DD1F607A335F20566C2F7B6D662F8822C06DED430F1B1\n\nIt was parsed to:\n\n{\"text\":\"\\u0013Yߣ2�c�3}��\\u0007�5�\\u0005f����b��,\\u0006��0�\"}\n{\"first\":10548475080845777000,\"second\":17453035185545249000,\"third\":17898103950452877000,\"fourth\":12822083304916660000}\n{\"first\":2749323539,\"second\":2456008242,\"third\":4140924211,\"fourth\":4063601415,\"fifth\":4156712453,\"sixth\":4167227062,\"seventh\":3724946562,\"eighth\":2985373908}\n{\"first\":2749323539,\"second\":17785134063915612000,\"third\":4063601415,\"text\":\"\\u0005f����b��,\\u0006��0�\"}",
            "time": {
                "timestamp": "2022-05-10T08:15:51.797599Z",
                "textual": "2022-05-10T08:15:51.797599Z"
            }
        }
    ],
    "_links": {
        "self": {
            "href": "https://rest.api.hiber.cloud/messages?devices=ABCD0123&size=2&page=0"
        },
        "next": {
            "href": "https://rest.api.hiber.cloud/messages?devices=ABCD0123&size=2&page=1"
        },
        "hiber:modem": [
            {
                "href": "https://rest.api.hiber.cloud/modem/ABCD0123",
                "title": "ABCD 0123"
            }
        ],
        "hiber:messages": {
            "href": "https://rest.api.hiber.cloud/messages{?from,to,devices,tags,groups,size,page}",
            "templated": true
        },
        "hiber:message-events": {
            "href": "https://rest.api.hiber.cloud/message/events{?from,to,devices,tags,groups,size,page}",
            "templated": true
        },
        "curies": [
            {
                "href": "https://rest.api.hiber.cloud/docs/rels/{rel}",
                "name": "hiber",
                "templated": true
            }
        ]
    }
}
```
