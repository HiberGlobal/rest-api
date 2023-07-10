# Changelog Hiber Rest API

### 0.157 (2023-07-10)

- Add `/modems` to list modems.

### 0.114 (2022-07-18)

- Added support for textual shortcuts in `from` and `to`:
  - "now": converted to the request time
  - a duration as an offset of now, i.e. "-10h" or "PT-10h": converted to now + offset, so -10h is 10 hours before now
    - For example, to fetch messages in the past hour, a request could use `from=-1h&to=now`.

### 0.106 (2022-05-16)

First release of the Rest API. See the [readme](./README.md) for more details.
