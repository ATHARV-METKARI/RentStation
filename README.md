# RenStation Platform

A robust, highly scalable, peer-to-peer game rental marketplace built on a distributed microservice architecture.

## Architecture Overview

RenStation is built using Domain-Driven Design (DDD) principles. The backend consists of 11 distinct Spring Boot 3.x microservices, orchestrated via Spring Cloud (Eureka/Config). The frontend is a modern React 18 SPA utilizing Zustand for state management and Tailwind CSS for styling.

### Infrastructure Layers
1. **API Gateway**: Spring Cloud Gateway (Port 8080). Handles Rate Limiting (Redis), CORS, and Global Error Handling.
2. **Service Discovery**: Netflix Eureka (Port 8761).
3. **Configuration**: Spring Cloud Config (Port 8888).
4. **Business Services**:
   - `auth-service`: JWT & OTP issuance.
   - `user-service`: Profiles & Addresses.
   - `inventory-service`: Seller listings (Optimistic Locking).
   - `game-service`: Global catalog.
   - `rental-service`: Distributed transactions & sagas.
   - `payment-service`: Financial engine & Webhooks (Idempotent).
   - `notification-service`: Multi-channel alerts.
   - `review-service`: Reputation & Trust.
   - `wishlist-service`: User engagement.
   - `admin-service`: Platform moderation.
5. **Data Layer**: MySQL 8 (9 independent schemas) & Redis (Rate Limiting).
6. **Client**: Vite + React 18 + Zustand.

---

## Local Development Setup

### Prerequisites
- Docker & Docker Compose
- Java 21 & Maven (if running backend locally without Docker)
- Node.js v20+ & npm

### 1. Start Infrastructure (Databases & Discovery)
To spin up MySQL (with auto-provisioned schemas) and Redis:
```bash
docker-compose up -d mysql redis
```

### 2. Build and Run Backend
*(Option A: Fully Containerized)*
You can build all microservice docker images and run them.
```bash
# Ensure common is installed in your local maven cache first!
cd backend/renstation-common
mvn clean install

cd ../..
docker-compose up --build
```

*(Option B: IDE Development - Recommended)*
Start the core infrastructure via IDE in this strict order:
1. `config-server` (Wait for initialization)
2. `discovery-server` (Wait for port 8761)
3. `api-gateway` (Port 8080)
4. All other business microservices (order does not matter).

### 3. Start Frontend
```bash
cd frontend
npm install
npm run dev
```
The React application will be available at `http://localhost:3000`. It is configured to automatically proxy `/api` calls to the API Gateway at `localhost:8080`.

---

## Security Model
- **Authentication**: Stateless JWT (RS256).
- **Gateway Isolation**: The frontend exclusively communicates with the API Gateway. The Gateway cryptographically validates the JWT.
- **Identity Propagation**: The Gateway strips malicious headers and injects `X-User-Id` and `X-User-Role` into the downstream internal network. Microservices trust these headers completely, preventing the need for internal JWT validation parsing.

## Flyway Database Migrations
All databases are version-controlled using Flyway. When a service boots up, it will automatically connect to its respective MySQL database and execute any pending `V*.sql` scripts found in `src/main/resources/db/migration`.
