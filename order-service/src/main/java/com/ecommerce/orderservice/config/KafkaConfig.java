package com.ecommerce.orderservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import com.ecommerce.orderservice.dto.refund.RefundResultDTO;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Topic names
    @Value("${kafka.topic.order-cancel-requests:order.cancel.requests}")
    private String orderCancelRequestsTopic;
    
    @Value("${kafka.topic.payment-refund-requests:payment.refund.requests}")
    private String paymentRefundRequestsTopic;
    
    @Value("${kafka.topic.payment-refund-results:payment.refund.results}")
    private String paymentRefundResultsTopic;
    
    @Value("${kafka.topic.order-status-updates:order.status.updates}")
    private String orderStatusUpdatesTopic;
    
    @Value("${kafka.topic.notifications:notifications}")
    private String notificationsTopic;

    // Topic creation beans
    @Bean
    public NewTopic orderCancelRequestsTopic() {
        return TopicBuilder.name(orderCancelRequestsTopic)
                .partitions(3)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "604800000") // 7 days
                .build();
    }

    @Bean
    public NewTopic notificationsTopic() {
        return TopicBuilder.name(notificationsTopic)
                .partitions(3)
                .replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, "86400000") // 24 hours
                .build();
    }

    @Bean
    public NewTopic paymentRefundRequestsTopic() {
        return TopicBuilder.name(paymentRefundRequestsTopic)
                .partitions(3)
                .replicas(1)
                .configs(Map.of("retention.ms", "604800000"))
                .build();
    }

    @Bean
    public NewTopic paymentRefundResultsTopic() {
        return TopicBuilder.name(paymentRefundResultsTopic)
                .partitions(3)
                .replicas(1)
                .configs(Map.of("retention.ms", "604800000"))
                .build();
    }

    @Bean
    public NewTopic orderStatusUpdatesTopic() {
        return TopicBuilder.name(orderStatusUpdatesTopic)
                .partitions(3)
                .replicas(1)
                .configs(Map.of("retention.ms", "604800000"))
                .build();
    }

    // Producer configuration
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        
        // Configure JSON serializer
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        configProps.put(JsonSerializer.TYPE_MAPPINGS, 
            "notification:com.ecommerce.orderservice.dto.NotificationDTO");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ecommerce.*");
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.ecommerce.orderservice.dto.NotificationDTO");
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Kafka consumer factory
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "order-service");
        
        // Configure JSON deserializer with error handling
        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        
        // Configure deserializer properties
        Map<String, Object> deserializerConfig = new HashMap<>();
        deserializerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.ecommerce.orderservice.dto.status.OrderStatusUpdateDTO");
        deserializerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ecommerce.*");
        deserializerConfig.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        deserializerConfig.put(JsonDeserializer.KEY_DEFAULT_TYPE, String.class);
        deserializerConfig.put(JsonDeserializer.TYPE_MAPPINGS, 
            "notification:com.ecommerce.orderservice.dto.notification.NotificationRequestDTO");
        
        // Configure the deserializer
        jsonDeserializer.configure(deserializerConfig, false);
        
        // Configure error handling deserializer
        ErrorHandlingDeserializer<Object> errorHandlingDeserializer = 
            new ErrorHandlingDeserializer<>(jsonDeserializer);
        
        return new DefaultKafkaConsumerFactory<>(
            configProps,
            new ErrorHandlingDeserializer<>(new StringDeserializer()),
            errorHandlingDeserializer
        );
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        
        // Configure error handler with dead letter topic
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
            (record, exception) -> {
                log.error("Error processing message: {}", record, exception);
            },
            new FixedBackOff(1000L, 3) // 3 retries with 1 second interval
        );
        
        // Don't retry deserialization errors
        errorHandler.addNotRetryableExceptions(
            org.apache.kafka.common.errors.SerializationException.class,
            org.springframework.kafka.support.serializer.DeserializationException.class
        );
        
        factory.setCommonErrorHandler(errorHandler);
        
        // Set up container properties
        ContainerProperties containerProperties = factory.getContainerProperties();
        containerProperties.setAckMode(ContainerProperties.AckMode.RECORD);
        containerProperties.setSyncCommits(true);
        
        return factory;
    }
}
