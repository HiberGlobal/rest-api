# /modem/{number}

Get the details for a modem with the given `number`, or 404 when not available.

## Usage

Simply navigate to [rest.api.hiber.cloud/modem/{number}](https://rest.api.hiber.cloud/modem/{number}),
where `{number}` is replaced with a modem number.
Don't forget the auth token as described in the main [readme](../README.md#auth).

Since numbers are often "AAAA BBBB", the space can be omitted when using this call (and anywhere else in the API).

### Output format

The output is an object with two fields.

The `modem` field is a json version of the `Modem` object
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/modem.md#modem),
though some fields may be omitted for simplicity.

The other field is a `_links` field, as described in [readme](../README.md#hal).

### Example

```
$ curl -v https://rest.api.hiber.cloud/modem/ABCD0123 -H "Authorization: Bearer <token>"
{
    "modem": {
        "organization": "hiber",
        "number": "ABCD 0123",
        "name": "ABCD 0123",
        "identifier": "00 00 00 00 00 00 00 00",
        "location": {
            "latitude": 1.2345,
            "longitude": 1.2345,
            "textual": "[1.2345,1.2345]"
        },
        "healthLevel": {
            "level": "Warning",
            "color": "#FFD67A",
            "colorData": {
                "fill": "#FFD67A",
                "markerBackground": "#EDB63C",
                "markerForeground": "#FFFFFF",
                "text": "#FFD67A"
            },
            "severity": "2"
        },
        "notes": "My important device",
        "tags": [
            {
                "id": "3",
                "label": {
                    "name": "my group",
                    "type": "group"
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "https://rest.api.hiber.cloud/modem/ABCD0123"
        },
        "hiber:messages": {
            "href": "https://rest.api.hiber.cloud/messages{?from,to,modems,devices,tags,groups,size,page}",
            "templated": true
        },
        "hiber:message-events": {
            "href": "https://rest.api.hiber.cloud/message/events{?from,to,modems,devices,tags,groups,size,page}",
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
