# Plant Watering Tracker

식물의 마지막 물 준 날짜와 물주기 주기를 기반으로 다음 물 주는 날짜와 현재 상태를 계산하는 미니 풀스택 웹 애플리케이션입니다.

React 프론트엔드에서 식물을 등록하고, Spring Boot 백엔드 API를 통해 PostgreSQL에 데이터를 저장합니다.  
Docker Compose를 사용해 Spring Boot 백엔드와 PostgreSQL 데이터베이스를 함께 실행할 수 있도록 구성했습니다.

## 프로젝트 목적

이 프로젝트는 Spring Boot, React, PostgreSQL, Docker Compose를 사용해 풀스택 웹 애플리케이션의 기본 구조를 실습하기 위해 제작했습니다.

주요 목표는 다음과 같습니다.

- Spring Boot 기반 REST API 구현
- React에서 백엔드 API 호출
- PostgreSQL 데이터베이스 연동
- Docker Compose를 이용한 백엔드 + DB 실행 환경 구성
- 날짜 계산 로직을 포함한 간단한 비즈니스 로직 구현

## 기술 스택

### Backend

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Validation
- Lombok

### Frontend

- React
- Vite
- Axios
- CSS

### Database / Infra

- PostgreSQL
- Docker
- Docker Compose

## 주요 기능

- 식물 등록
- 식물 목록 조회
- 식물 단건 조회
- 식물 정보 수정
- 식물 삭제
- 오늘 물 줬어요 처리
- 마지막 물 준 날짜 기준 다음 물 주는 날짜 계산
- 물주기 상태 표시

## 물주기 상태값

| 상태 | 설명 |
|---|---|
| OK | 아직 물 줄 때가 아님 |
| DUE_TODAY | 오늘 물 줘야 함 |
| OVERDUE | 물 주는 날짜가 지남 |

## 프로젝트 구조

```text
Plant_Watering_Tracker
├── backend
│   ├── src
│   ├── build.gradle
│   └── Dockerfile
├── frontend
│   ├── src
│   ├── package.json
│   └── vite.config.js
├── docker-compose.yml
└── README.md
```

## 실행 방법

### 1. Docker Desktop 실행

먼저 Docker Desktop을 실행합니다.

### 2. 프로젝트 루트로 이동

```bash
cd Plant_Watering_Tracker
```

### 3. 백엔드와 데이터베이스 실행

```bash
docker compose up --build
```

실행 후 백엔드 API는 아래 주소에서 확인할 수 있습니다.

```text
http://localhost:8088
```

Health Check:

```text
GET http://localhost:8088/api/health
```

### 4. 프론트엔드 실행

새 터미널을 열고 아래 명령어를 실행합니다.

```bash
cd frontend
npm install
npm run dev
```

프론트엔드 주소:

```text
http://localhost:5173
```

## API 목록

| Method | Endpoint | 설명 |
|---|---|---|
| GET | /api/health | 서버 상태 확인 |
| GET | /api/plants | 식물 목록 조회 |
| GET | /api/plants/{id} | 식물 단건 조회 |
| POST | /api/plants | 식물 등록 |
| PUT | /api/plants/{id} | 식물 정보 수정 |
| PATCH | /api/plants/{id}/water | 오늘 물 줬어요 처리 |
| DELETE | /api/plants/{id} | 식물 삭제 |

## 요청 예시

### 식물 등록

```http
POST /api/plants
Content-Type: application/json
```

```json
{
  "name": "몬스테라",
  "wateringIntervalDays": 7,
  "lastWateredDate": "2026-06-10",
  "note": "거실 창가"
}
```

## 응답 예시

```json
{
  "id": 1,
  "name": "몬스테라",
  "wateringIntervalDays": 7,
  "lastWateredDate": "2026-06-10",
  "nextWateringDate": "2026-06-17",
  "status": "OK",
  "note": "거실 창가"
}
```

## 핵심 로직

마지막 물 준 날짜와 물주기 주기를 더해 다음 물 주는 날짜를 계산합니다.

```java
public LocalDate getNextWateringDate() {
    return lastWateredDate.plusDays(wateringIntervalDays);
}
```

오늘 날짜와 다음 물 주는 날짜를 비교해 상태값을 반환합니다.

```java
public PlantStatus getStatus() {
    LocalDate today = LocalDate.now();
    LocalDate nextWateringDate = getNextWateringDate();

    if (today.isBefore(nextWateringDate)) {
        return PlantStatus.OK;
    }

    if (today.isEqual(nextWateringDate)) {
        return PlantStatus.DUE_TODAY;
    }

    return PlantStatus.OVERDUE;
}
```

## Docker Compose 구성

이 프로젝트는 Docker Compose를 통해 PostgreSQL과 Spring Boot 백엔드를 함께 실행합니다.

```text
db       → PostgreSQL 컨테이너
backend  → Spring Boot 컨테이너
```

백엔드 컨테이너는 Docker Compose 네트워크 안에서 `db`라는 서비스 이름으로 PostgreSQL에 연결합니다.

```yaml
SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/plantdb
```

## 구현하면서 해결한 문제

- Docker Desktop 실행을 위해 Windows 가상화 설정 확인
- PostgreSQL 컨테이너와 Spring Boot 백엔드 연결
- Spring Controller 매핑 충돌 해결
- PowerShell JSON 입력 시 한글 깨짐 문제 해결
- Thunder Client를 사용한 API 테스트
- React에서 Spring Boot API 호출 및 CORS 설정

## 향후 개선 사항

- 식물 정보 수정 UI 추가
- 상태별 필터 기능 추가
- 프론트엔드 Dockerfile 추가
- 백엔드 배포 환경 구성
- 테스트 코드 작성
- 예외 응답 형식 개선

## 포트폴리오 설명

이 프로젝트는 Spring Boot와 React를 사용해 구현한 식물 물주기 관리 미니 풀스택 애플리케이션입니다.

백엔드에서는 식물 CRUD API, 물주기 처리 API, 다음 물주기 날짜 계산 로직을 구현했습니다.  
PostgreSQL을 데이터베이스로 사용했으며, Docker Compose를 통해 백엔드와 데이터베이스를 함께 실행하도록 구성했습니다.

프론트엔드에서는 React와 Axios를 사용해 식물 등록, 목록 조회, 물주기 처리, 삭제 기능을 구현했습니다.


## Kubernetes 실습

Docker Compose로 실행하던 Spring Boot 백엔드와 PostgreSQL 환경을 Kubernetes Deployment와 Service로 구성했습니다.

React 프론트엔드는 로컬 개발 서버에서 실행하고, API 요청은 Kubernetes NodePort Service를 통해 Spring Boot 백엔드 Pod로 전달되도록 구성했습니다. 백엔드는 ClusterIP Service를 통해 PostgreSQL Pod에 접근하며, 식물 등록과 목록 조회가 정상 동작하는 것을 확인했습니다.

- Spring Boot 백엔드 Deployment 구성
- PostgreSQL Deployment 구성
- PostgreSQL 내부 접근을 위한 ClusterIP Service 구성
- 백엔드 외부 접근을 위한 NodePort Service 구성
- React 화면에서 Kubernetes 백엔드 API로 식물 등록/조회 확인