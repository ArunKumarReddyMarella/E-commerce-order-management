# E-commerce Config Repository

This repository contains centralized configuration for all microservices in the E-commerce order management system.

## Structure

- `application.yml` - Common configuration shared across all services
- `{service-name}.yml` - Service-specific configuration files

## Services

| Service | Port | Database | Config File |
|---------|------|----------|-------------|
| Product Service | 8081 | ecommerce_productdb | product-service.yml |
| Inventory Service | 8082 | ecommerce_inventorydb | inventory-service.yml |
| Order Service | 8083 | ecommerce_orderdb | order-service.yml |
| User Service | 8084 | ecommerce_userdb | user-service.yml |
| Payment Service | 8085 | ecommerce_paymentdb | payment-service.yml |
| Notification Service | 8086 | ecommerce_notificationdb | notification-service.yml |

## Configuration Hierarchy

1. **application.yml** - Common settings (JPA, logging, management endpoints, OpenAPI)
2. **{service-name}.yml** - Service-specific settings (port, database, service-specific logging)

## Usage

### Starting Services

1. Start the **Config Server** first:
   ```bash
   cd config-server
   mvn spring-boot:run
   ```

2. Start other services (they will automatically fetch config from config-server):
   ```bash
   cd product-service
   mvn spring-boot:run
   ```

### Configuration Refresh

To refresh configuration without restarting services, you can:
1. Update the config files in this repository
2. Use Spring Boot Actuator refresh endpoint (if enabled):
   ```bash
   POST /actuator/refresh
   ```

### Local Development

For local development overrides, you can:
1. Modify the service's local `application.yml` file
2. Use environment variables
3. Use command-line arguments

## Environment-Specific Configs

You can create environment-specific configurations:
- `application-dev.yml` - Development environment
- `application-prod.yml` - Production environment
- `{service-name}-dev.yml` - Service-specific dev config
- `{service-name}-prod.yml` - Service-specific prod config

## Security

For production environments:
- Use encrypted configuration values
- Store sensitive data in secure vaults
- Use environment variables for secrets
- Implement proper authentication for config-server access 