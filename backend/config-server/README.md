# RenStation Config Server

This module acts as the centralized Spring Cloud Config Server for the RenStation platform. 
It provides configuration values to all other microservices at runtime.

## Architecture
The server runs on port `8888`. All microservices should be configured to fetch their properties from this server using `spring.config.import=optional:configserver:http://localhost:8888/`.

## Configuration Profiles
This server supports two modes:

### 1. Native Profile (Local Development)
By default, the active profile is `native`. 
The server will look for configuration files in `classpath:/shared-config` or a local folder named `central-config` in the root of the project.

### 2. Git Profile (Production)
Run the server with `-Dspring.profiles.active=git`.
It will clone the configuration from a dedicated Git repository.
You must set the environment variable `SPRING_CLOUD_CONFIG_SERVER_GIT_URI` to point to the repository.

## How to Run
```bash
mvn clean install
mvn spring-boot:run
```
To verify it is working, access the health endpoint: `http://localhost:8888/actuator/health`

## Future Improvements
- Implement Spring Security (Basic Auth) to secure the configuration endpoints.
- Integrate Spring Cloud Bus (RabbitMQ/Kafka) so configuration changes can be broadcasted dynamically to all microservices without restarting them.
