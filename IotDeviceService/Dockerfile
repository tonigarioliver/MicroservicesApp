FROM openjdk:17-jdk-slim as builder

WORKDIR /app

COPY . .

RUN ./mvnw clean install -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/

CMD ["java", "-jar", "/app/IotDeviceService-0.0.1-SNAPSHOT.jar"]

LABEL version="iot_device_service"

