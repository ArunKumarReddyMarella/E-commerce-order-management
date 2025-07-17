# Eureka Server Setup Guide

This document explains how the Eureka Service Discovery Server is implemented in this project. It covers dependencies, configuration, code changes, and usage, so anyone can understand and replicate the setup.

---

## 1. What is Eureka Server?
Eureka Server is a service registry from Netflix, used in microservices architectures to allow services to discover and communicate with each other dynamically. It is a core part of Spring Cloud Netflix.

---

## 2. Project Structure

```
E-commerce-order-management/
  eureka-server/
    pom.xml
    src/
      main/
        java/
          com/ecommerce/eurekaserver/EurekaServerApplication.java
        resources/
          application.yml
```

---

## 3. Dependencies (`pom.xml`)

Add the following dependency to `eureka-server/pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
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

Create the main class at `src/main/java/com/ecommerce/eurekaserver/EurekaServerApplication.java`:

```java
package com.ecommerce.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```
- `@EnableEurekaServer` turns this Spring Boot app into a Eureka registry server.

---

## 5. Configuration (`application.yml`)

Create `src/main/resources/application.yml` with the following content:

```yaml
server:
  port: 8761
spring:
  application:
    name: eureka-server
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```
- `port: 8761` is the default Eureka dashboard port.
- `register-with-eureka: false` and `fetch-registry: false` make this a standalone registry (not a client).

---

## 6. How to Run

1. Open a terminal and navigate to the `eureka-server` directory:
   ```sh
   cd eureka-server
   ```
2. Start the Eureka server:
   ```sh
   mvn spring-boot:run
   ```
3. Open your browser and go to [http://localhost:8761](http://localhost:8761) to see the Eureka dashboard.

---

## 7. Registering Other Services

To register other microservices with Eureka, add the following to their `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

And in their `bootstrap.yml` or `application.yml`:

```yaml
spring:
  application:
    name: <service-name>
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

No annotation is needed in the main class for Eureka client registration in modern Spring Cloud.

---

## 8. Summary Table

| File/Config         | Purpose                                  |
|---------------------|------------------------------------------|
| pom.xml             | Add Eureka server dependency             |
| application.yml     | Configure as standalone registry         |
| Main class          | Annotate with `@EnableEurekaServer`      |

---

## 9. Useful Links
- [Spring Cloud Netflix Eureka Docs](https://cloud.spring.io/spring-cloud-netflix/reference/html/)
- [Eureka Dashboard](http://localhost:8761)

---

**This setup ensures your microservices can discover each other dynamically, enabling robust, scalable communication in your architecture.** 