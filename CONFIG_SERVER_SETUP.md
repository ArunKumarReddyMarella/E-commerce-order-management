# Config Server Setup Guide

This document explains how the Spring Cloud Config Server is implemented in this project. It covers dependencies, configuration, code changes, and usage, so anyone can understand and replicate the setup.

---

## 1. What is Config Server?
Spring Cloud Config Server provides server-side and client-side support for externalized configuration in a distributed system. It allows you to manage all your microservices' configuration in a central place (e.g., a Git repository).

---

## 2. Project Structure

```
E-commerce-order-management/
  config-server/
    pom.xml
    src/
      main/
        java/
          com/ecommerce/configserver/ConfigServerApplication.java
        resources/
          application.yml
  config-repo/
    application.yml
    <service-name>.yml
```

---

## 3. Dependencies (`pom.xml`)

Add the following dependency to `config-server/pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
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

Create the main class at `src/main/java/com/ecommerce/configserver/ConfigServerApplication.java`:

```java
package com.ecommerce.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```
- `@EnableConfigServer` turns this Spring Boot app into a Config Server.

---

## 5. Configuration (`application.yml`)

Create `src/main/resources/application.yml` with the following content:

```yaml
server:
  port: 8888
spring:
  application:
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: file:///${user.dir}/../config-repo
          default-label: main
          search-paths: config-repo
```
- `port: 8888` is the default Config Server port.
- The `git.uri` points to your local `config-repo` directory (can be a remote Git URL in production).
- All configuration files are stored in `config-repo/`.

---

## 6. Centralized Configuration Repository (`config-repo/`)

- `application.yml`: Common configuration for all services.
- `<service-name>.yml`: Service-specific configuration (e.g., `product-service.yml`, `order-service.yml`).

**Example `config-repo/application.yml`:**
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
logging:
  level:
    com.ecommerce: DEBUG
```

**Example `config-repo/product-service.yml`:**
```yaml
server:
  port: 8081
spring:
  application:
    name: product-service
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce_productdb?createDatabaseIfNotExist=true
    username: root
    password: drowssap
```

---

## 7. How to Run

1. Open a terminal and navigate to the `config-server` directory:
   ```sh
   cd config-server
   ```
2. Start the Config Server:
   ```sh
   mvn spring-boot:run
   ```
3. The Config Server will be available at [http://localhost:8888](http://localhost:8888)

---

## 8. How to Test

- Check health: [http://localhost:8888/actuator/health](http://localhost:8888/actuator/health)
- Fetch config for a service: [http://localhost:8888/product-service/default](http://localhost:8888/product-service/default)

---

## 9. Config Client Setup (for other services)

Each microservice should have the following in its `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

And in `src/main/resources/bootstrap.yml`:

```yaml
spring:
  config:
    import: "configserver:"
  application:
    name: <service-name>
  cloud:
    config:
      uri: http://localhost:8888
```

---

## 10. Summary Table

| File/Config         | Purpose                                  |
|---------------------|------------------------------------------|
| pom.xml             | Add Config Server dependency             |
| application.yml     | Configure as Config Server, set repo     |
| Main class          | Annotate with `@EnableConfigServer`      |
| config-repo/        | Centralized configuration files          |

---

## 11. Useful Links
- [Spring Cloud Config Docs](https://cloud.spring.io/spring-cloud-config/reference/html/)
- [Config Server Health](http://localhost:8888/actuator/health)

---

**This setup ensures all your microservices fetch their configuration from a single, centralized source, making your system easier to manage and more robust.** 