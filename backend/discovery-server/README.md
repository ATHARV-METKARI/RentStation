# RenStation Eureka Discovery Server

This module acts as the Service Registry for all RenStation microservices. 

## Purpose & Responsibilities
- **Service Registration**: All backend microservices will register themselves here upon startup.
- **Service Discovery**: Allows services to find each other by logical name (e.g., `user-service`) instead of hardcoded IP addresses.
- **Health Monitoring**: Maintains an up-to-date registry by listening for client heartbeats.

## Architecture & Startup Sequence
1. The Discovery Server starts on port `8761`.
2. It immediately connects to the **Config Server** (`http://localhost:8888/`) via the `spring.config.import` directive in `application.yml` to fetch any remote profiles or overrides.
3. It initializes the Eureka registry.
4. It awaits incoming registrations from client microservices.

## Configuration
- `register-with-eureka` and `fetch-registry` are set to `false` since this is a standalone server (not a client).
- **Self-preservation** is disabled in dev but enabled in the `prod` profile to prevent mass deregistration during network blips.

## Future Clustering Strategy (High Availability)
In a production environment, multiple Eureka servers should be deployed for High Availability (HA). To do this:
1. Spin up multiple instances (e.g., `eureka-1` and `eureka-2`).
2. Set `register-with-eureka: true` and `fetch-registry: true`.
3. Point `eureka-1`'s `defaultZone` to `eureka-2`, and vice-versa (peer awareness).

## How to Run
Ensure the Config Server is already running.
```bash
mvn clean install
mvn spring-boot:run
```
Access the Eureka Dashboard at `http://localhost:8761`.
