# Changelog Hiber Rest API

### 0.228 (2025-02-25)

- Extended the parameter options for `/device` (and the old `/modem`) to allow more parameters:
  - Parameter options:
    - modem numbers
    - external device identifiers
  - For a device with number `ABCD EFGH` and external identifier `11 22 33 44 55 66 77 88` (e.g. a LoRaWAN DevEUI):
    - `/device/ABCDEFGH` and `/device/ABCD%20EFGH` (spaces are ignored)
    - `/device/1122334455667788` and any spaced variants (spaces are ignored)
    - and the old `/modem` routes
      - `/modem/ABCDEFGH` and `/device/ABCD%20EFGH` (spaces are ignored)
      - `/modem/1122334455667788` and any spaced variants (spaces are ignored)

- Added `/device/{number-or-identifier}/file` (POST only) to upload a file to a device (using `multipart/form-data`).
  - Added it to the old `/modem` routes as well: `/modem/{number-or-identifier}/file`.

### 0.227 (2025-02-18)

- added `/me` to get information about the current token and permissions

- Added `/file/{identifier}` to get a file from the Rest API
  - Also supports DELETE to delete a file.
  - Added `/file/{identifier}/metadata` to get metadata for a file.
- Added `/asset/{identifier}/file/` (POST only) to upload a file to an asset (using `multipart/form-data`).

### 0.225 (2025-02-04)

- Update README with all the paths.
- Fix an issue where the documentation was not published.

### 0.212 (2024-10-08)

- Added `/assets` path to get a list of assets.

- Added `/asset/{identifier}` path to get a single asset.

- Added `/values/asset` path to get values for assets.
- Added `/values/device` path to get values for devices.
- Added `/device/{number}` as alias for `/modem/{number}`.
- Added `/devices` as alias for `/modems`.

### 0.209 (2024-09-17)

- Improved Basic Auth header support:
  - Allow encoded newline at the end of the basic auth encoded value.
    - Typically, the Basic Auth value is the string of `$username:$password` base64 encoded.
      Now we also allow a bas64 encoded string of `$username:$password\n`.

### 0.208 (2024-09-10)

- Added Basic Auth header support, where
  - username is ignored;
  - password is your API token (the same you would use for Bearer auth).

### 0.164 (2023-09-19)

- Added `pagination` to all list-type results, with
  - `size`: the number of items per page
  - `page`: the current page
  - `total`: the total number of results
  - `totalPages`: the total number of pages
  - `approximation`: whether the total number of results/pages is an approximation

### 0.157 (2023-07-10)

- Add `/modems` to list modems.

### 0.114 (2022-07-18)

- Added support for textual shortcuts in `from` and `to`:
  - "now": converted to the request time
  - a duration as an offset of now, i.e. "-10h" or "PT-10h": converted to now + offset, so -10h is 10 hours before now
    - For example, to fetch messages in the past hour, a request could use `from=-1h&to=now`.

### 0.106 (2022-05-16)

First release of the Rest API. See the [readme](./README.md) for more details.
