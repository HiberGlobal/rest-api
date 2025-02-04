# /file/{identifier}

Get a file with the given `identifier`, or 404 when not available.

## Usage

Simply navigate to [rest.api.hiber.cloud/file/{identifier}](https://rest.api.hiber.cloud/file/{identifier}),
where `{identifier}` is replaced with the file identifier.
Don't forget the auth token as described in the main [readme](../README.md#auth).

### Result

- If the file is a stored url, you will get a 301 response to that url.
- Otherwise, you will get the binary content of the file with the correct type.

For more details, see the definition
[in the grpc API](https://github.com/HiberGlobal/api/blob/master/docs/md/file.md#file).

### Example

```
$ curl -v https://rest.api.hiber.cloud/file/example-identifier -H "Authorization: Bearer <token>"
...
> GET /file/example-identifier HTTP/2
> Host: rest.api.hiber.cloud
> user-agent: curl/7.81.0
> accept: */*
> authorization: Bearer <token>
>
...
< HTTP/2 200
< date: Tue, 04 Feb 2025 09:56:13 GMT
< content-type: image/png
< content-length: 51166
...
```
