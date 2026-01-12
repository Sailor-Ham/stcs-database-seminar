# ========================= #
# 빌드 스테이지 (JDK)
# ========================= #
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# 빌드 도구 파일 복사 (캐시 활용)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 의존성 미리 내려받기
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사 및 빌드
COPY src src
RUN ./gradlew clean bootJar -x test --no-daemon


# ========================= #
# 실행 스테이지 (JRE)
# ========================= #
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 빌드 스테이지에서 생성된 jar 파일만 복사
COPY --from=builder /app/build/libs/app.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]