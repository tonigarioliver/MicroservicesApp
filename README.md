# MicroservicesApp

A microservices playground: service discovery, gateway routing, gRPC,
MQTT and event streaming wired together with Docker Compose.

## Services
- `discovery-service` — Eureka service registry
- `api-gateway-service` — Spring Cloud Gateway, routes to registered services
- `user-service`, `device-service`, `stream-service` — domain services
- `grpc-server-service` / `grpc-client-library` — gRPC communication
- `mqtt-client` + Eclipse Mosquitto — MQTT messaging
- Kafka + Zookeeper — event streaming
- PostgreSQL — persistence

## Run locally
```bash
docker-compose up -d
```
Swagger UI: `http://localhost:8082/swagger-ui/index.html`
