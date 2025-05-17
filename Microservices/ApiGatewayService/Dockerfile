# Use Maven image as the builder stage
FROM maven:3.9.6-amazoncorretto-21 AS builder

WORKDIR /app

COPY . .

# Build the application
RUN mvn clean install -DskipTests

# Use OpenJDK image as the final stage
FROM amazoncorretto:21

WORKDIR /app

# Copy only the JAR file from the builder stage
COPY --from=builder /app/target/*.jar /app/

# Define el perfil activo (cambia "prod" según tus necesidades)
ENV SPRING_PROFILES_ACTIVE=prod

# Define the command to run the application
CMD ["java","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app/ApiGatewayService-0.0.1-SNAPSHOT.jar"]

# Add metadata to the image
LABEL version="apigatewayservice"
