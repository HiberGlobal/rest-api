# /values/device{?from,to,device,type,size,page}

List the values for one of more devices, or the whole organization.

## Usage

This endpoint is available at
[rest.api.hiber.cloud/values/device{?from,to,device,type,size,page}](https://rest.api.hiber.cloud/values/device),
with a number of query parameters to select the values to return.

The query parameters are:
- `from` (timestamp): starting time for a time range, to list values for a specific time
- `to` (timestamp): end time for a time range, to list values for a specific time
- `device` (text, default all): list values for the given device(s) only, comma-separated list or added multiple times
- `type` (value type or numeric value type): list values of the given value type (text, enum) or numeric value type
  (pressure, temperature, etc).
- `size` (integer, default 100): the amount of values to return per page
- `page` (integer, default 0): the page to return

Don't forget the auth token as described in the main [readme](../README.md#auth).

Since device numbers are often "AAAA BBBB", the space can be omitted when using this call (and anywhere else in the API).

We support a number of different timestamp formats, but regardless of input we output time as
[ISO-8601](https://www.iso.org/iso-8601-date-and-time-format.html) `YYYY-MM-DDTHH:mm:ss.sssZ`.

Textual shortcuts for timestamps are also supported, like:
  - "now": converted to the request time
  - a duration as an offset of now, i.e. "-10h" or "PT-10h": converted to now + offset, so -10h is 10 hours before now
    - For example, to fetch values in the past hour, a request could use `from=-1h&to=now`.

### Output format

The output is an object with two fields.

The `values` field is a json array containing values, which are a json objects similar to the `ValueContext` object
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/md/value_service.md#valuecontext),
though some fields have been omitted and the structure has been flattened for simplicity.

The `pagination` field is a simple object with some pagination-related information, like the total amount of results
and the current page number (starting from 0).

The other field is a `_links` field, for pagination.

### Example

For the url `https://rest.api.hiber.cloud/values/device?device=ABCD1234&size=2` with the device `ABCD 1234`:

```
curl https://rest.api.hiber.cloud/values/device\?device\=ABCD1234\&size\=2 -H "Authorization: Bearer <token>"
{
    "values": [
        {
            "device": "ABCD 1234",
            "time": "2022-01-01T20:05:00Z",
            "value": 3.5,
            "textual": "3.5000 bar",
            "type": "PRESSURE"
        },
        {
            "device": "ABCD 1234",
            "time": "2022-01-01T19:05:00Z",
            "textual": "The status is B",
            "type": "TEXT"
        }
    ],
    "_links": {
        "self": {
            "href": "https://rest.api.test.env.hiber.cloud/values/device?size=100&page=0&device=ABCD1234"
        }
    },
    "pagination": {
        "size": 2,
        "page": 0,
        "total": 2000,
        "approximation": false,
        "totalPages": 1000
    }
}
```
