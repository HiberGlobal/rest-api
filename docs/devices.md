# /devices

Get the list of devices (/devices).

## Usage

Simply navigate to [rest.api.hiber.cloud/devices](https://rest.api.hiber.cloud/devices).
Don't forget the auth token as described in the main [readme](../README.md#auth).

### Output format

The output is an object with two fields.

The `devices` field is a json version of the `Device` object
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/md/device.md#device),
though some fields may be omitted for simplicity.

The `pagination` field is a simple object with some pagination-related information, like the total amount of results
and the current page number (starting from 0).

The other field is a `_links` field, for pagination.

### Example

```
$ curl -v https://rest.api.hiber.cloud/devices?size=1&page=2 -H "Authorization: Bearer <token>"
{
    "devices": [{
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
            "href": "https://rest.api.hiber.cloud/devices?size=1&page=2"
        },
        "next": {
            "href": "https://rest.api.hiber.cloud/devices?size=1&page=3"
        },
        "previous": {
            "href": "https://rest.api.hiber.cloud/devices?size=1&page=1"
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
