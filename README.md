# Search Service API
카카오,네이버 장소 API를 활용하여 Search Service를 제공하는 RESTful API 

## 주요 알고리즘


## USAGE

### Search Places

**Endpoint**: `/places`

**Method**: `GET`

**Query Parameters**:
- `query`: The search query string.

**Example**: `/v1/place?query=카카오뱅크`

**Request**:

```bash
curl -X GET 'https://limitless-inlet-46201-af0b6f1ae40f.herokuapp.com/v1/place?query=카카오뱅크'
```
**Response**

The response is returned in JSON format and follows the structure below:

#### Search Places

```json
[
  {
    "name": "Coffee Shop A",
    "address": "123 Main St, City"
  },
  {
    "name": "Coffee Shop B",
    "address": "456 Park Ave, Town"
  }
]
```

### Get Top 10 Queries

**Endpoint**: `/top-queries`

**Method**: `GET`

**Request**:

```bash
curl -X GET 'https://limitless-inlet-46201-af0b6f1ae40f.herokuapp.com/v1/place/top'
```
**Response**

The response is returned in JSON format and follows the structure below:

#### Search Places

```json
[
  {
    "query": "coffee",
    "count": 10
  },
  {
    "query": "restaurant",
    "count": 8
  },
  ...
]
```

