# /modem/{number}

Get the details for a modem with the given `identifier`, or 404 when not available.

## Usage

Simply navigate to [rest.api.hiber.cloud/modem/{identifier}](https://rest.api.hiber.cloud/modem/{identifier}),
where `{identifier}` is replaced with a modem identifier (like the number, e.g. `ABCD EFGH`, or the DevEUI).
Don't forget the auth token as described in the main [readme](../README.md#auth).

While identifiers are often formatted with spaces, these can be omitted when using this call
(and anywhere else in the API).

### Output format

The output is an object with two fields.

The `modem` field is a json version of the `Modem` object
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/md/modem.md#modem),
though some fields may be omitted for simplicity.

The other field is a `_links` field.

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
        }
    }
}
```
