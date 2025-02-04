# /asset/{identifier}

Get the details for an asset with the given `identifier`, or 404 when not available.

## Usage

Simply navigate to [rest.api.hiber.cloud/asset/{identifier}](https://rest.api.hiber.cloud/asset/{identifier}),
where `{identifier}` is replaced with the asset identifier.
Don't forget the auth token as described in the main [readme](../README.md#auth).

### Output format

The output is an object with two fields.

The `asset` field is a json version of the `Asset` object
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/md/asset.md#asset),
though some fields may be omitted for simplicity.

The other field is a `_links` field, as described in [readme](../README.md#hal).

### Example

```
$ curl -v https://rest.api.hiber.cloud/modem/ABCD0123 -H "Authorization: Bearer <token>"
{
    "asset": {
        "organization": "hiber",
        "identifier": "your-asset-identifier",
        "name": "Asset name",
        "type": "WELL_ANNULUS_C",
        "description": "your asset description",
        "notes": "your asset notes",
        "devices": [
            {
                "number": "ABCD 0123",
                "name": "ABCD 0123",
                "type": "Pressure Sensor-1 EU",
                "numericValueTypes": [PRESSURE],
                "assignmentStart": "2024-01-01T00:00:00Z"
            }
        ],
        "inactiveDevices": [
            {
                "number": "ABCD 3210",
                "type": "Temperature Sensor-1 EU",
                "numericValueTypes": [TEMPERATURE],
                "assignmentStart": "2023-01-01T00:00:00Z",
                "assignmentEnd": "2024-01-01T00:00:00Z"
            }
        ],
        "tags": []
    },
    "_links": {
        "self": {
            "href": "https://rest.api.test.env.hiber.cloud/asset/your-asset-identifier"
        }
    }
}
```
