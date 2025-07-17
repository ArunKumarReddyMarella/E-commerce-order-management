# API Gateway Setup Guide

This document explains how the API Gateway is implemented in this project using Spring Cloud Gateway. It covers dependencies, configuration, code changes, and usage, so anyone can understand and replicate the setup.

---

## 1. What is an API Gateway?
Spring Cloud Gateway provides a simple, effective way to route requests to multiple microservices, acting as a single entry point. It supports routing, load balancing, security, and more.

---

## 2. Project Structure

```
E-commerce-order-management/
  api-gateway/
    pom.xml
    src/
      main/
        java/
          com/ecommerce/apigateway/ApiGatewayApplication.java
        resources/
          application.yml
```

---

## 3. Dependencies (`pom.xml`)

Add the following dependencies to `api-gateway/pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

Also, import the Spring Cloud BOM in `<dependencyManagement>`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

**Example:**
```xml
<properties>
    <java.version>17</java.version>
    <spring-cloud.version>2025.0.0</spring-cloud.version>
</properties>
```

---

## 4. Main Application Class

Create the main class at `src/main/java/com/ecommerce/apigateway/ApiGatewayApplication.java`:

```java
package com.ecommerce.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```
- No special annotation is needed for discovery; the dependency is enough.

---

## 5. Configuration (`application.yml`)

Create or update `src/main/resources/application.yml` with the following content:

```yaml
server:
  port: 8080
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: product-service
          uri: lb://product-service
          predicates:
            - Path=/products/**
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/orders/**
        - id: inventory-service
          uri: lb://inventory-service
          predicates:
            - Path=/inventory/**
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/users/**
        - id: payment-service
          uri: lb://payment-service
          predicates:
            - Path=/payments/**
        - id: notification-service
          uri: lb://notification-service
          predicates:
            - Path=/notifications/**
  config:
    import: "configserver:"
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```
- `lb://` means load-balance using Eureka service discovery.
- Each route maps a path to a microservice.

---

## 6. How It Works
- Requests to `/products/**` are routed to the product-service, `/orders/**` to order-service, etc.
- The gateway uses Eureka to discover service instances and load-balance requests.
- All configuration is centralized and fetched from the config-server.

---

## 7. How to Run

1. Start `config-server`
2. Start `eureka-server`
3. Start `api-gateway`
4. Start all other microservices

---

## 8. How to Test

- Access your services via the gateway:
  - `http://localhost:8080/products/...`
  - `http://localhost:8080/orders/...`
  - etc.

---

## 9. Summary Table

| File/Config         | Purpose                                  |
|---------------------|------------------------------------------|
| pom.xml             | Add Gateway, Eureka client dependencies  |
| application.yml     | Configure routes and discovery           |
| Main class          | Standard Spring Boot main class          |

---

## 10. Useful Links
- [Spring Cloud Gateway Docs](https://docs.spring.io/spring-cloud-gateway/docs/current/reference/html/)
- [Eureka Dashboard](http://localhost:8761)

---

**This setup ensures all your microservices are accessible through a single, robust entry point, enabling routing, load balancing, and centralized management.** 