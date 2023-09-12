# RenStation

RenStation is an online platform where gamers can rent PlayStation 5 games at affordable prices. 

This repository contains the foundational scaffolding for the enterprise-grade RenStation application. It follows a microservices architecture for the backend (Spring Boot) and a modern component-driven approach for the frontend (React + Vite).

## Architecture Overview

- **Backend**: Java 21, Spring Boot 3.x, Spring Cloud (Gateway, Config, Eureka), MySQL 8.
- **Frontend**: React 19, Vite, TypeScript, Tailwind CSS, Zustand, TanStack Query.
- **Database Strategy**: Database-per-service pattern (using MySQL) with Flyway migrations.
- **Design Pattern**: Layered clean architecture with clear separation of concerns.

## Project Structure

- `/backend` - Contains 13 independent Spring Boot microservices.
- `/frontend` - Contains the React Vite SPA.
- `/docker` - Global Docker configurations (future docker-compose).
- `/docs` - Project-level documentation.
- `/scripts` - Automation scripts.
- `/postman` - Postman collections for API testing.

## Getting Started

Refer to the individual README files in `/backend` and `/frontend` for setup instructions.
