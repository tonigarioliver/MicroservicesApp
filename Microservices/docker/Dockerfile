# Build stage
FROM maven:3.9.6-amazoncorretto-21 AS builder

ARG MODULE_NAME

WORKDIR /app

# Copiar pom raíz
COPY pom.xml ./pom.xml

# Copiar todos los submódulos (copiar las carpetas completas)
COPY ./grpc-client-library ./grpc-client-library
COPY ./stream-service ./stream-service
COPY ./device-service ./device-service
COPY ./grpc-server-service ./grpc-server-service
COPY ./api-gateway-service ./api-gateway-service
COPY ./discovery-service ./discovery-service
COPY ./user-service ./user-service
COPY ./mqtt-client ./mqtt-client

# Construir solo el módulo solicitado y sus dependencias
RUN mvn clean install -pl ${MODULE_NAME} -am -DskipTests

# Runtime stage
FROM amazoncorretto:21

ARG MODULE_NAME

WORKDIR /app

COPY --from=builder /app/${MODULE_NAME}/target/*.jar /app/

# Declaramos ENV para tiempo de ejecución
ENV MODULE_NAME=${MODULE_NAME}
ENV SPRING_PROFILES_ACTIVE=prod

CMD ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar /app/${MODULE_NAME}-0.0.1-SNAPSHOT.jar"]

LABEL version="${MODULE_NAME}_service"
