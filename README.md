# Docker, Cucumber를 활용한 재현 가능한 환경 구축 레포지토리

## 실행 방법

```
# 1. Docker로 App, Mysql, Redis, Kafka 컨테이너 띄우기
docker-compose up --build

# 2. GET /health API 요청 방법
http://localhost:8080/health
```

## 테스트 실행
```
# 환경 검증(Cucumber & Testcontainers)
./gradlew test
```

