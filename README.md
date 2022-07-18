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

### HAL

We've added some additional information to API responses using the `_links` field,
following the [HAL specification](https://en.wikipedia.org/wiki/Hypertext_Application_Language).

For example, each response will contain a field at `_links.self.href` that links back to the current page,
or, when requesting a list (i.e. messages or message-events), it will contain `_links.next.href` for the next page.

The `_links` also contains some simple specifications of available commands and documentation,
defined using a [CURIE](https://en.wikipedia.org/wiki/CURIE) under `_links.curies`
and using [URI Templates](https://www.ietf.org/rfc/rfc6570.txt) like `messages{?from,to,devices,tags,groups,size,page}`.

## Endpoints

The currently available endpoints are:

- [modem/{number}](docs/modem.md): get the details for a modem (spaces can be omitted from the number)
- [messages{?from,to,modems,devices,tags,groups,size,page}](docs/messages.md): list messages
- [message/events{?from,to,modems,devices,tags,groups,size,page}](docs/message-events.md): list message events

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
