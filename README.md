# Hiber Rest API

This is the specification for the Hiber Rest API.

The Rest API is intended for read-only funcationality only.
Anything more complicated is handled by the [Hiber GRPC API](https://github.com/hiberglobal/api).

## Usage

Use any http client that supports setting (auth) headers, like, `curl`, Postman, etc.

### Auth

To authenticate when using the API, you will need:

- A token for the organizations you wish to use the Rest API for.
  (Impersonation is not currently supported)
  - Go to [Developer Tools / Tokens](https://hiber.cloud/developers/token) in Mission Control
  - Make a new token with the permissions you need, typically modems and messages.
  - Copy the token string.
- When doing a request on the API, add the `Authentication` header with the value `Bearer <your-token>`,
  where `<your-token>` is the token copied above.

## Endpoints

The currently available endpoints are:

- For assets:
  - [assets](docs/assets.md): list assets
  - [asset/{identifier}](docs/asset.md): get the details for an asset
  - [values/asset{?from,to,asset,type,size,page}](docs/asset-values.md): get the values for an asset
- For devices:
  - [devices](docs/devices.md): list devices
  - [device/{number}](docs/device.md): get the details for a device (spaces can be omitted from the number)
  - [values/device{?from,to,device,type,size,page}](docs/device-values.md): get the values for a device
  - [messages{?from,to,modems,devices,tags,groups,size,page}](docs/messages.md): list messages
  - [message/events{?from,to,modems,devices,tags,groups,size,page}](docs/message-events.md): list message events
- For modems (old terminology for devices):
  - [modem/{number}](docs/modem.md): get the details for a modem (spaces can be omitted from the number)
- For files:
  - [file/{identifier}](docs/file.md): get a file

### Simple example

For example:

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
            "href": "https://rest.api.hiber.cloud/modem/0E2C05C1"
        }
    }
}
```
