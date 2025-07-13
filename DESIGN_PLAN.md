# E-Commerce Order Management System - Implementation Guide

## Project Architecture Overview

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

## Phase 1: Foundation Setup (Week 1)

### 1.1 Create Parent Project Structure
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

### 1.2 Technology Stack
- **Spring Boot**: 3.2.x
- **Spring Cloud**: 2023.0.x
- **Java**: 17+
- **Database**: PostgreSQL for each service
- **Message Broker**: Apache Kafka
- **Cache**: Redis
- **Build Tool**: Maven

### 1.3 Parent POM Dependencies
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<properties>
    <spring-cloud.version>2023.0.0</spring-cloud.version>
</properties>

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

## Phase 2: Core Services (Week 2)

### 2.1 Eureka Server Setup
```yaml
# eureka-server/application.yml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
  server:
    enable-self-preservation: false
```

### 2.2 Config Server Setup
```yaml
# config-server/application.yml
server:
  port: 8888

spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/your-repo/config-repo
          default-label: main
```

### 2.3 Product Service Implementation

#### Product Entity
```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private String brand;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Getters, setters, constructors
}
```

#### Product Service Configuration
```yaml
# product-service/application.yml
server:
  port: 8081

spring:
  application:
    name: product-service
  datasource:
    url: jdbc:postgresql://localhost:5432/product_db
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

### 2.4 Inventory Service Implementation

#### Inventory Entity
```java
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;
    
    @Column(name = "quantity_reserved", nullable = false)
    private Integer quantityReserved;
    
    @Column(name = "reorder_level")
    private Integer reorderLevel;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    // Getters, setters, constructors
}
```

## Phase 3: Order Processing & Events (Week 3)

### 3.1 Order Service with Saga Pattern

#### Order Entity
```java
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Getters, setters, constructors
}

enum OrderStatus {
    PENDING, CONFIRMED, PAYMENT_PENDING, PAYMENT_FAILED, 
    PROCESSING, SHIPPED, DELIVERED, CANCELLED
}
```

#### Event-Driven Architecture
```java
// Order Events
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    // constructors, getters, setters
}

public class OrderConfirmedEvent {
    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;
    // constructors, getters, setters
}

public class OrderCancelledEvent {
    private Long orderId;
    private String reason;
    // constructors, getters, setters
}
```

### 3.2 Kafka Configuration
```java
@Configuration
@EnableKafka
public class KafkaConfig {
    
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    
    // Producer Configuration
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return new DefaultKafkaProducerFactory<>(props);
    }
    
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    // Consumer Configuration
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ecommerce-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ecommerce.events");
        return new DefaultKafkaConsumerFactory<>(props);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.setCommonErrorHandler(new DefaultErrorHandler());
        return factory;
    }
}

// Topic Configuration
@Configuration
public class KafkaTopicConfig {
    
    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name("order.created")
                .partitions(3)
                .replicas(1)
                .build();
    }
    
    @Bean
    public NewTopic orderConfirmedTopic() {
        return TopicBuilder.name("order.confirmed")
                .partitions(3)
                .replicas(1)
                .build();
    }
    
    @Bean
    public NewTopic orderCancelledTopic() {
        return TopicBuilder.name("order.cancelled")
                .partitions(3)
                .replicas(1)
                .build();
    }
    
    @Bean
    public NewTopic inventoryUpdatedTopic() {
        return TopicBuilder.name("inventory.updated")
                .partitions(3)
                .replicas(1)
                .build();
    }
    
    @Bean
    public NewTopic paymentProcessedTopic() {
        return TopicBuilder.name("payment.processed")
                .partitions(3)
                .replicas(1)
                .build();
    }
}

// Event Publisher
@Component
public class EventPublisher {
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    public void publishOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send("order.created", event.getOrderId().toString(), event);
    }
    
    public void publishOrderConfirmed(OrderConfirmedEvent event) {
        kafkaTemplate.send("order.confirmed", event.getOrderId().toString(), event);
    }
    
    public void publishOrderCancelled(OrderCancelledEvent event) {
        kafkaTemplate.send("order.cancelled", event.getOrderId().toString(), event);
    }
}

// Event Listeners
@Component
public class OrderEventListener {
    
    @KafkaListener(topics = "order.created", groupId = "inventory-service")
    public void handleOrderCreated(OrderCreatedEvent event) {
        // Reserve inventory for the order
        log.info("Processing order created event: {}", event.getOrderId());
        inventoryService.reserveInventory(event.getItems());
    }
    
    @KafkaListener(topics = "order.confirmed", groupId = "payment-service")
    public void handleOrderConfirmed(OrderConfirmedEvent event) {
        // Process payment
        log.info("Processing order confirmed event: {}", event.getOrderId());
        paymentService.processPayment(event);
    }
    
    @KafkaListener(topics = "order.cancelled", groupId = "inventory-service")
    public void handleOrderCancelled(OrderCancelledEvent event) {
        // Release reserved inventory
        log.info("Processing order cancelled event: {}", event.getOrderId());
        inventoryService.releaseReservedInventory(event.getOrderId());
    }
}
```

## Phase 4: Advanced Features (Week 4)

### 4.1 API Gateway Configuration
```yaml
# api-gateway/application.yml
server:
  port: 8080

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: product-service
          uri: lb://product-service
          predicates:
            - Path=/api/products/**
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/orders/**
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/api/users/**
      default-filters:
        - DedupeResponseHeader=Access-Control-Allow-Credentials Access-Control-Allow-Origin
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins: "*"
            allowedMethods: "*"
            allowedHeaders: "*"
```

### 4.2 Circuit Breaker Implementation
```java
@Component
public class PaymentServiceClient {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallbackPayment")
    @TimeLimiter(name = "payment-service")
    public CompletableFuture<PaymentResponse> processPayment(PaymentRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            return restTemplate.postForObject(
                "http://payment-service/api/payments/process", 
                request, 
                PaymentResponse.class
            );
        });
    }
    
    public CompletableFuture<PaymentResponse> fallbackPayment(PaymentRequest request, Exception ex) {
        return CompletableFuture.completedFuture(
            PaymentResponse.builder()
                .status("FAILED")
                .message("Payment service is currently unavailable")
                .build()
        );
    }
}
```

### 4.3 Distributed Tracing Setup
```yaml
# Add to each service's application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  tracing:
    sampling:
      probability: 1.0
  zipkin:
    tracing:
      endpoint: http://localhost:9411/api/v2/spans
```

## Docker Compose Setup
```yaml
version: '3.8'
services:
  postgres-product:
    image: postgres:15
    environment:
      POSTGRES_DB: product_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5433:5432"
  
  postgres-order:
    image: postgres:15
    environment:
      POSTGRES_DB: order_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5434:5432"
  
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
  
  kafka:
    image: confluentinc/cp-kafka:7.5.0
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
    depends_on:
      - zookeeper
  
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
  
  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    ports:
      - "8090:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: local
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9092
    depends_on:
      - kafka
  
  zipkin:
    image: openzipkin/zipkin
    ports:
      - "9411:9411"
```

## Testing Strategy

### Integration Tests with TestContainers
```java
@SpringBootTest
@Testcontainers
class OrderServiceIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");
    
    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"));
    
    @Test
    void shouldProcessOrderSuccessfully() {
        // Test implementation
    }
}
```

## Key Learning Outcomes

1. **Service Decomposition**: Understanding how to split business logic into services
2. **Event-Driven Architecture**: Implementing asynchronous communication patterns
3. **Distributed Transactions**: Saga pattern implementation
4. **Service Discovery**: Eureka integration
5. **API Gateway**: Routing and cross-cutting concerns
6. **Circuit Breakers**: Fault tolerance patterns
7. **Distributed Tracing**: Observability across services
8. **Testing**: Integration testing with TestContainers

## Next Steps

After completing this project, you'll have hands-on experience with:
- Spring Boot microservices ecosystem
- Event-driven architecture patterns
- Distributed system challenges and solutions
- Production-ready microservices setup

This foundation prepares you for more advanced projects involving real-time communication, complex business logic, and enterprise-grade scalability patterns.