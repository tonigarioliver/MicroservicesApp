FROM maven:3.8.2-openjdk-17-slim AS builder

WORKDIR /app

COPY . .

# Build the application
RUN mvn clean install -DskipTests

# Use OpenJDK image as the final stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy only the JAR file from the builder stage
COPY --from=builder /app/target/*.jar /app/

# Define el perfil activo (cambia "prod" según tus necesidades)
ENV SPRING_PROFILES_ACTIVE=prod

# Define el comando para ejecutar la aplicación con el perfil activo
CMD ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app/MqttClient-0.0.1-SNAPSHOT.jar"]

# Add metadata to the image
LABEL version="mqtt_client_service"