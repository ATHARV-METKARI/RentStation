# RenStation Backend

This directory contains the Spring Boot 3.x Microservices for the RenStation platform.

## Architecture

The backend consists of 13 independent microservices communicating via Spring Cloud Gateway and Eureka Discovery Server.

### Core Infrastructure Services
- **config-server**: Centralized configuration management.
- **discovery-server**: Eureka registry for service discovery.
- **api-gateway**: Spring Cloud Gateway for routing and cross-cutting concerns.

### Business Microservices
- **auth-service**: Authentication and Authorization (Spring Security).
- **user-service**: User profile management.
- **game-service**: PS5 games catalog.
- **inventory-service**: Stock and availability tracking.
- **rental-service**: Core rental process logic.
- **payment-service**: Payment processing.
- **notification-service**: Email and push notifications.
- **review-service**: Ratings and reviews.
- **wishlist-service**: User wishlists.
- **admin-service**: Administrative features and reporting.

## Tech Stack
- Java 21
- Spring Boot 3.x
- Spring Cloud
- MySQL 8
- Flyway
- Spring Data JPA
- OpenFeign

## Setup and Running

Each service can be run independently using Maven:
```bash
cd <service-name>
mvn spring-boot:run
```
*(Note: Ensure the Config and Discovery servers are running first, followed by the specific microservice).*
