package com.ecommerce.paymentservice.config;

import jakarta.validation.ValidationException;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.util.backoff.FixedBackOff;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.nio.charset.StandardCharsets;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:payment-service}")
    private String groupId;

    // Topic names
    @Value("${kafka.topic.payment-refund-requests:payment.refund.requests}")
    private String paymentRefundRequestsTopic;
    
    @Value("${kafka.topic.payment-refund-results:payment.refund.results}")
    private String paymentRefundResultsTopic;

    // Topic creation beans with proper configuration
    @Bean
    public NewTopic paymentRefundRequestsTopic() {
        return TopicBuilder.name(paymentRefundRequestsTopic)
                .partitions(3)
                .replicas(1)
                .configs(Map.of("retention.ms", "604800000")) // 7 days retention
                .build();
    }
    
    @Bean
    public NewTopic paymentRefundResultsTopic() {
        return TopicBuilder.name(paymentRefundResultsTopic)
                .partitions(3)
                .replicas(1)
                .configs(Map.of("retention.ms", "604800000")) // 7 days retention
                .build();
    }

    // Consumer configuration
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        
        // Create a custom deserializer with better error handling
        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>() {
            @Override
            public Object deserialize(String topic, byte[] data) {
                try {
                    return super.deserialize(topic, data);
                } catch (Exception e) {
                    log.error("Error deserializing message from topic {}: {}", topic, 
                        data != null ? new String(data, StandardCharsets.UTF_8) : "null", e);
                    throw e;
                }
            }
        };
        
        // Configure the deserializer properties
        Map<String, Object> deserializerProps = new HashMap<>();
        deserializerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.ecommerce.*");
        deserializerProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        deserializerProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, 
            "com.ecommerce.paymentservice.dto.refund.RefundRequestDTO");
        
        jsonDeserializer.configure(deserializerProps, false);
        
        return new DefaultKafkaConsumerFactory<>(
            configProps,
            new StringDeserializer(),
            jsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        
        // Configure concurrency (number of consumer threads)
        factory.setConcurrency(3);
        
        // Configure error handler with retries
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
            (record, exception) -> {
                // Log the error with context
                log.error("Failed to process message from topic={}, partition={}, offset={}",
                    record.topic(), record.partition(), record.offset(), exception);
            },
            new FixedBackOff(1000L, 3) // 3 retries with 1 second interval
        );
        
        // Don't retry on validation exceptions
        errorHandler.addNotRetryableExceptions(ValidationException.class);
        
        factory.setCommonErrorHandler(errorHandler);
        
        return factory;
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
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
