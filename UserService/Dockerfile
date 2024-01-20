FROM openjdk:17-jdk-slim as builder

WORKDIR /app

COPY . .

RUN ./mvnw clean install -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/

CMD ["java", "-jar", "/app/UserService-0.0.1-SNAPSHOT.jar"]

LABEL version="user_service"

