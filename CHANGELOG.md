# Changelog Hiber Rest API

#### Upcoming Changes

### 0.120 (2022-09-12)

### 0.119 (2022-09-05)

### 0.118 (2022-08-22)

### 0.117 (2022-08-15)

### 0.116 (2022-08-01)

### 0.115 (2022-07-25)

### 0.114 (2022-07-18)

- Added support for textual shortcuts in `from` and `to`:
  - "now": converted to the request time
  - a duration as an offset of now, i.e. "-10h" or "PT-10h": converted to now + offset, so -10h is 10 hours before now
    - For example, to fetch messages in the past hour, a request could use `from=-1h&to=now`.

### 0.106 (2022-05-16)

First release of the Rest API. See the [readme](./README.md) for more details.
