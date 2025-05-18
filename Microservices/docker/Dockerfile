# Build stage
FROM maven:3.9.6-amazoncorretto-21 AS builder

ARG MODULE_NAME

WORKDIR /app

# Copiar pom raíz
COPY pom.xml ./pom.xml

# Copiar todos los submódulos (copiar las carpetas completas)
COPY ./GrpcClientLibrary ./GrpcClientLibrary
COPY ./RealTimeDataService ./RealTimeDataService
COPY ./IotDeviceService ./IotDeviceService
COPY ./GrpcServerService ./GrpcServerService
COPY ./ApiGatewayService ./ApiGatewayService
COPY ./EurekaServer ./EurekaServer
COPY ./UserService ./UserService
COPY ./MqttClient ./MqttClient

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
