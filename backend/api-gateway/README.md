# RenStation API Gateway

This module serves as the **Single Entry Point** for all external traffic entering the RenStation platform.

## Architecture
- **Framework**: Spring Cloud Gateway (built on Spring WebFlux).
- **Port**: `8080`
- **Routing**: Uses `lb://` protocol to dynamically resolve service IP addresses via the Eureka Discovery Server.

## Filter Chain
All requests pass through a sequence of Global Filters:
1. `CorrelationIdFilter`: Injects `X-Correlation-ID` if absent.
2. `LoggingFilter`: Logs incoming requests and outgoing responses.
3. `JwtAuthenticationFilter` (Placeholder): Where future JWT validation and token extraction will occur.
4. `RequestTimingFilter`: Logs total request execution time.

## Routing Strategy
The `application.yml` statically maps URL prefixes to service instances:
- `/api/v1/auth/**` -> `auth-service`
- `/api/v1/games/**` -> `game-service`
...and so on.

## Security Preparation
Currently, the gateway allows all traffic through. 
In the future, `JwtAuthenticationFilter` will intercept requests. We will define lists of public endpoints (e.g. `/auth/login`, `/games`) that bypass the filter, while throwing 401s for unauthenticated access to protected routes (e.g., `/rentals/book`).

## CORS
CORS is configured globally in `application.yml` (`spring.cloud.gateway.globalcors`). It allows standard React frontend ports (`3000`, `5173`) in development and can be overridden via `cors.allowed-origins` for production domains.

## How to Run
Ensure Config Server and Discovery Server are running.
```bash
mvn clean install
mvn spring-boot:run
```
