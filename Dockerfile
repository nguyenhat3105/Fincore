FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /workspace

COPY gradle gradle
COPY gradlew gradlew
COPY gradlew.bat gradlew.bat
COPY build.gradle settings.gradle gradle.properties ./
RUN chmod +x gradlew

COPY src src
RUN ./gradlew clean bootJar --no-daemon --console=plain

FROM eclipse-temurin:21-jre-alpine AS runtime

RUN addgroup -S fincore && adduser -S fincore -G fincore

WORKDIR /app

COPY --from=builder --chown=fincore:fincore /workspace/build/libs/fincore-*.jar app.jar

USER fincore
EXPOSE 8080

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -Dfile.encoding=UTF-8"

HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]

