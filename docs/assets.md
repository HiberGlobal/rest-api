# /assets

Get the list of assets.

## Usage

Simply navigate to [rest.api.hiber.cloud/assets](https://rest.api.hiber.cloud/assets).
Don't forget the auth token as described in the main [readme](../README.md#auth).

### Output format

The output is an object with two fields.

The `assets` field is a json version of the `Asset` object
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/md/asset.md#asset),
though some fields may be omitted for simplicity.

The `pagination` field is a simple object with some pagination-related information, like the total amount of results
and the current page number (starting from 0).

The other field is a `_links` field, for pagination.

### Example

```
$ curl -v https://rest.api.hiber.cloud/assets?size=1&page=2 -H "Authorization: Bearer <token>"
{
    "assets": [
        {
            "organization": "hiber",
            "identifier": "asset-a",
            "name": "Asset A",
            "type": "WELL_ANNULUS_A",
            "description": "your asset description",
            "notes": "your asset notes",
            "devices": [
                {
                    "number": "ABCD 0123",
                    "name": "ABCD 0123",
                    "type": "Pressure Sensor-1 EU",
                    "numericValueTypes": [
                        PRESSURE
                    ],
                    "assignmentStart": "2024-01-01T00:00:00Z"
                }
            ],
            "inactiveDevices": [
                {
                    "number": "ABCD 3210",
                    "type": "Temperature Sensor-1 EU",
                    "numericValueTypes": [
                        TEMPERATURE
                    ],
                    "assignmentStart": "2023-01-01T00:00:00Z",
                    "assignmentEnd": "2024-01-01T00:00:00Z"
                }
            ],
            "tags": []
        },
        {
            "organization": "hiber",
            "identifier": "asset-b",
            "name": "Asset B",
            "type": "WELL_ANNULUS_B",
            "description": "your asset description",
            "notes": "your asset notes",
            "devices": [
                {
                    "number": "ABCD 0123",
                    "name": "ABCD 0123",
                    "type": "Pressure Sensor-1 EU",
                    "numericValueTypes": [
                        PRESSURE
                    ],
                    "assignmentStart": "2024-01-01T00:00:00Z"
                }
            ],
            "inactiveDevices": [
                {
                    "number": "ABCD 3210",
                    "type": "Temperature Sensor-1 EU",
                    "numericValueTypes": [
                        TEMPERATURE
                    ],
                    "assignmentStart": "2023-01-01T00:00:00Z",
                    "assignmentEnd": "2024-01-01T00:00:00Z"
                }
            ],
            "tags": []
        },
        {
            "organization": "hiber",
            "identifier": "asset-c",
            "name": "Asset C",
            "type": "WELL_ANNULUS_C",
            "description": "your asset description",
            "notes": "your asset notes",
            "devices": [
                {
                    "number": "ABCD 0123",
                    "name": "ABCD 0123",
                    "type": "Pressure Sensor-1 EU",
                    "numericValueTypes": [
                        PRESSURE
                    ],
                    "assignmentStart": "2024-01-01T00:00:00Z"
                }
            ],
            "inactiveDevices": [
                {
                    "number": "ABCD 3210",
                    "type": "Temperature Sensor-1 EU",
                    "numericValueTypes": [
                        TEMPERATURE
                    ],
                    "assignmentStart": "2023-01-01T00:00:00Z",
                    "assignmentEnd": "2024-01-01T00:00:00Z"
                }
            ],
            "tags": []
        },
        {
            "organization": "hiber",
            "identifier": "asset-d",
            "name": "Asset D",
            "type": "WELL_ANNULUS_D",
            "description": "your asset description",
            "notes": "your asset notes",
            "devices": [
                {
                    "number": "ABCD 0123",
                    "name": "ABCD 0123",
                    "type": "Pressure Sensor-1 EU",
                    "numericValueTypes": [
                        PRESSURE
                    ],
                    "assignmentStart": "2024-01-01T00:00:00Z"
                }
            ],
            "inactiveDevices": [
                {
                    "number": "ABCD 3210",
                    "type": "Temperature Sensor-1 EU",
                    "numericValueTypes": [
                        TEMPERATURE
                    ],
                    "assignmentStart": "2023-01-01T00:00:00Z",
                    "assignmentEnd": "2024-01-01T00:00:00Z"
                }
            ],
            "tags": []
        },
        {
            "organization": "hiber",
            "identifier": "asset-e",
            "name": "Asset E",
            "type": "WELL_TUBING_HEAD",
            "description": "your asset description",
            "notes": "your asset notes",
            "devices": [
                {
                    "number": "ABCD 0123",
                    "name": "ABCD 0123",
                    "type": "Pressure Sensor-1 EU",
                    "numericValueTypes": [
                        PRESSURE
                    ],
                    "assignmentStart": "2024-01-01T00:00:00Z"
                }
            ],
            "inactiveDevices": [
                {
                    "number": "ABCD 3210",
                    "type": "Temperature Sensor-1 EU",
                    "numericValueTypes": [
                        TEMPERATURE
                    ],
                    "assignmentStart": "2023-01-01T00:00:00Z",
                    "assignmentEnd": "2024-01-01T00:00:00Z"
                }
            ],
            "tags": []
        }
    ],
    "_links": {
        "self": {
            "href": "https://rest.api.hiber.cloud/assets?size=1&page=2"
        },
        "next": {
            "href": "https://rest.api.hiber.cloud/assets?size=1&page=3"
        },
        "previous": {
            "href": "https://rest.api.hiber.cloud/assets?size=1&page=1"
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
