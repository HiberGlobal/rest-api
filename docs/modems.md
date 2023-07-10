# /modems

Get the list of modems (/devices).

## Usage

Simply navigate to [rest.api.hiber.cloud/modems](https://rest.api.hiber.cloud/modems).
Don't forget the auth token as described in the main [readme](../README.md#auth).

### Output format

The output is an object with two fields.

The `modems` field is a json version of the `Modem` object
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/modem.md#modem),
though some fields may be omitted for simplicity.

The other field is a `_links` field, as described in [readme](../README.md#hal).

### Example

```
$ curl -v https://rest.api.hiber.cloud/modems?size=1&page=2 -H "Authorization: Bearer <token>"
{
    "modems": [{
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
    }],
    "_links": {
        "self": {
            "href": "https://rest.api.hiber.cloud/modems?size=1&page=2"
        },
        "next": {
            "href": "https://rest.api.hiber.cloud/modems?size=1&page=3"
        },
        "previous": {
            "href": "https://rest.api.hiber.cloud/modems?size=1&page=1"
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
