# E-commerce Microservices Architecture

## Overview

This project is a microservices-based e-commerce backend system, built with Spring Boot and Spring Cloud. It demonstrates a modular, scalable architecture with service discovery, centralized configuration, API gateway, messaging, and distributed data management.

### Architecture

```
API Gateway (Spring Cloud Gateway)
├── Product Service (Port 8081)
├── Inventory Service (Port 8082)
├── Order Service (Port 8083)
├── Payment Service (Port 8084)
├── Notification Service (Port 8085)
└── User Service (Port 8086)

Service Discovery: Eureka Server (Port 8761)
Configuration Server: Spring Cloud Config (Port 8888)
Message Broker: Apache Kafka (Port 9092)
```

### Directory Structure

```
ecommerce-microservices/
├── eureka-server/
├── config-server/
├── api-gateway/
├── product-service/
├── inventory-service/
├── order-service/
├── payment-service/
├── notification-service/
├── user-service/
└── docker-compose.yml
```

### Technology Stack
- **Spring Boot**: 3.2.x
- **Spring Cloud**: 2023.0.x
- **Java**: 17+
- **Database**: PostgreSQL for each service
- **Message Broker**: Apache Kafka
- **Cache**: Redis
- **Build Tool**: Maven

## Quick Start

1. Build all services with Maven:
   ```sh
   mvn clean package -DskipTests
   ```
2. Start the stack with Docker Compose:
   ```sh
   docker-compose up --build
   ```

## Services
- **eureka-server**: Service discovery
- **config-server**: Centralized configuration
- **api-gateway**: Entry point for all clients
- **product-service**: Manages products
- **inventory-service**: Manages inventory
- **order-service**: Handles orders
- **payment-service**: Handles payments
- **notification-service**: Sends notifications
- **user-service**: Manages users 