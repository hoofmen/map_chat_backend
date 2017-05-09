MapChat Service (backend)
=========================

General idea is to post messages in a Map.

### Message Format (JSON)
```json
{
	"id":"1d4f35e5-ded5-fd9b-ed15-867d35ce21bf",
	"location": {
		"lat":37.7634744,
		"lng":-121.9046315
	},
	"message":"This pharmacy has very cheap stuff",
	"duration":4
}
```

### Relevant Development Tools
* Java 8
* Spring 4.3.5
* Jackson 2.8.5
* MongoDB Java Driver 3.3
* Spring Data MongoDB 1.9.6

### Data
Mongo DB 3 - Hosted on mLab

### Status
[![Build Status](https://travis-ci.org/hoofmen/map_chat_backend.svg?branch=master)](https://github.com/hoofmen/mapchat_service)

