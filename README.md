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

### Option 1: Using PowerShell Script (Recommended)
1. Run the startup script:
   ```powershell
   .\start-services.ps1
   ```

### Option 2: Manual Startup
1. Start Config Server first:
   ```bash
   cd config-server
   mvn spring-boot:run
   ```

2. Start Eureka Server:
   ```bash
   cd eureka-server
   mvn spring-boot:run
   ```

3. Start other services (in any order):
   ```bash
   cd product-service
   mvn spring-boot:run
   ```

### Option 3: Docker Compose
1. Build all services with Maven:
   ```sh
   mvn clean package -DskipTests
   ```
2. Start the stack with Docker Compose:
   ```sh
   docker-compose up --build
   ```

## Configuration Management

The project uses Spring Cloud Config Server for centralized configuration management:

- **Config Server**: `http://localhost:8888`
- **Config Repository**: `config-repo/` directory
- **Common Config**: `config-repo/application.yml`
- **Service Configs**: `config-repo/{service-name}.yml`

### Testing Config Server
```powershell
.\test-config-server.ps1
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