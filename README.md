# Search Service API
카카오,네이버 장소 API를 활용하여 Search Service를 제공하는 RESTful API 

## 주요 알고리즘


## USAGE

### Search Places

**Endpoint**: `/v1/place`

**Method**: `GET`

**Query Parameters**:
- `keyword`: The search keyword string.

**Example**: `/v1/place?query=카카오뱅크`

**Request**:

```bash
curl -X GET "https://limitless-inlet-46201-af0b6f1ae40f.herokuapp.com/v1/place?keyword=$(echo -n '카카오뱅크' | curl -Gso /dev/null -w %{url_effective} --data-urlencode @- '' | cut -c 3-)" -H "Accept: application/json"
```
**Response**

The response is returned in JSON format and follows the structure below:

#### Search Places

```json
[
    {
        "name": "신진BANK충전소",
        "address": ""
    },
    {
        "name": "아이스 Bank",
        "address": "경북 포항시 남구 지곡로 253"
    },
    {
        "name": "부동산BANK공인중개사사무소",
        "address": "광주 서구 죽봉대로 86"
    },
    {
        "name": "OA BANK",
        "address": "제주특별자치도 제주시 고사마루길 30"
    },
    {
        "name": "BANK부동산중개사무소",
        "address": "경남 밀양시 시청서길 32"
    },
    {
        "name": "중국은행 서울지점",
        "address": "서울특별시 종로구 청계천로 41 영풍빌딩"
    },
    {
        "name": "중국공상은행 서울지점",
        "address": "서울특별시 중구 세종대로 73 태평로빌딩"
    },
    {
        "name": "NH농협은행 광화문금융센터",
        "address": "서울특별시 중구 세종대로 124 프레스센터(서울신문사) 2층"
    },
    {
        "name": "중국농업은행",
        "address": "서울특별시 중구 세종대로 136 서울파이낸스센터 14층"
    },
    {
        "name": "KB국민은행 태평로",
        "address": "서울특별시 중구 세종대로 135 코리아나호텔"
    }
]
```

### Get Top 10 Queries

**Endpoint**: `/v1/place/top`

**Method**: `GET`

**Request**:

```bash
curl -X GET "https://limitless-inlet-46201-af0b6f1ae40f.herokuapp.com/v1/place/top"
```
**Response**

The response is returned in JSON format and follows the structure below:

#### Search Places

```json
[
    {
        "카카오뱅크": 2
    },
    {
        "스타벅스": 1
    },
  ...
]
```

