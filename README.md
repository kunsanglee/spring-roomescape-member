# 방탈출 사용자 예약
### 요구사항
- [x] 요청에 대한 적절한 상태코드를 반환하도록 수정
  - 생성 시 201, 삭제 시 204

- [x] 시간에서 발생할 수 있는 예외 사항 처리
  - [x] 유효하지 않은 시작 시간
  - [x] 중복되는 시작 시간
  - [x] 초 단위 시간은 무시하도록 처리
  - [x] 없는 id를 삭제하는 경우
  - [x] 예약이 존재하는 시간을 삭제하는 경우

- [x] 예약에서 발생할 수 있는 예외 사항 처리
  - [x] 시간 id가 존재하지 않는 경우
  - [x] 이름 제약조건 (길이 등)
  - [x] 과거 시간을 예약하는 경우
  - [x] 같은 날짜에 같은 시간을 예약하는 경우 중복 예약
  
  - [x] 테마에서 발생할 수 있는 예외 사항 처리
    - [x] 예약이 존재하는 테마를 삭제하는 경우

# 방탈출 API 명세

## 예약 조회

### Request

- GET /reservations

### Response

- 200 OK
- content-ype: application/json

``` json
[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-08-05",
        "time": {
            "id": 1,
            "startAt": "10:00"
        }
    }
]
```

---

## 예약 추가

### Request

- POST /reservations
- content-type: application/json

```json
{
  "date": "2023-08-05",
  "name": "브라운",
  "timeId": 1,
  "themeId": 1
}
```

### Response

- 201 Created
- Location: /reservations/1
- content-type: application/json

```json
{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": {
    "id": 1,
    "startAt": "10:00"
  },
  "theme": {
    "id": 1,
    "themeName": "테마",
    "description": "설명",
    "thumbnail": "url"
  }
}
```

---

## 예약 삭제

### Request

- DELETE /reservations/{id}

### Response

- 204 No Content

---

## 시간 추가

### Request

- POST /times
- content-type: application/json

```json
{
  "startAt": "10:00"
}
```

### Response

- 201 Created
- Location: /times/1
- content-type: application/json

```json
{
  "id": 1,
  "startAt": "10:00"
}
```

---

## 시간 조회

### Request

- GET /times

### Response

- 200 OK
- content-type: application/json

```json
[
  {
    "id": 1,
    "startAt": "10:00"
  }
]
```

---

## 시간 삭제

### Request

- DELETE /times/{id}

### Response

- 204 No Content

---

## 테마 추가

### Request

- POST /themes
- Location: /themes/1
- content-type: application/json

```json
{
  "name": "테마",
  "description": "테마 설명",
  "thumbnail": "테마 이미지"
}
```

### Response

- 201 Created
- content-type: application/json

```json
{
  "id": 1,
  "name": "테마",
  "description": "테마 설명",
  "thumbnail": "테마 이미지"
}
```

---

## 테마 조회

### Request

- GET /themes

### Response

- 200 OK
- content-type: application/json

```json
[
  {
    "id": 1,
    "name": "테마",
    "description": "테마 설명",
    "thumbnail": "테마 이미지"
  }
]
```

---

## 테마 삭제

### Request

- DELETE /themes/{id}

### Response

- 204 No Content

---

## 인기 테마

### Request

- GET /themes/popular

### Response

- 200 OK
- content-type: application/json

```json
[
  {
    "id": 2,
    "name": "테마2",
    "description": "설명2",
    "thumbnail": "url2"
  },
  {
  "id": 1,
  "name": "테마1",
  "description": "설명1",
  "thumbnail": "url1"
  }
]
```

---

## 예약 가능 시간

### Request

- GET /times/available?date={date}&themeId={id}
- date: yyyy-MM-dd
- themeId: 테마 id

### Response

- 200 OK
- content-type: application/json

```json
[
  {
    "id": 1,
    "startAt": "10:00",
    "isBooked": true
  },
  {
    "id": 2,
    "startAt": "11:00",
    "isBooked": false
  }
]
```
