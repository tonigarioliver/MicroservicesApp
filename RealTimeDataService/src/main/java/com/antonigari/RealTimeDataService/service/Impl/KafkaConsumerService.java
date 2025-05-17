package com.antonigari.RealTimeDataService.service.Impl;


import com.antonigari.RealTimeDataService.model.DeviceMeasurementDto;
import com.antonigari.RealTimeDataService.service.IKafkaConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumerService implements IKafkaConsumerService {
    private static final String UNKNOWN_TOPIC_MESSAGE = "Received message for unknown topic {}: {}";

    private final MqttClientService mqttClientService;
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    @Override
    @KafkaListener(
            topics = "#{'${spring.kafka.consumer.topics}'.split(',')}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(final String payload, @Header("kafka_receivedTopic") final String topic) {
        log.info("Received Kafka message: {}", payload);
        try {
            final var measurement = this.deserializePayload(payload);
            this.processMeasurement(topic, measurement);
        } catch (final JsonProcessingException e) {
            throw new MessageProcessingException("Failed to process Kafka message", e);
        }
    }

    private DeviceMeasurementDto deserializePayload(final String payload) throws JsonProcessingException {
        return this.objectMapper.readValue(payload, DeviceMeasurementDto.class);
    }

    private void processMeasurement(final String topic, final DeviceMeasurementDto measure) {
        switch (topic) {
            case KafkaTopics.CREATE_MEASUREMENT -> this.mqttClientService.addSubscription(measure);
            case KafkaTopics.UPDATE_MEASUREMENT -> this.mqttClientService.updateSubscription(measure);
            case KafkaTopics.DELETE_MEASUREMENT -> this.mqttClientService.removeSubscription(measure);
            default -> log.warn(UNKNOWN_TOPIC_MESSAGE, topic, measure);
        }
    }

    private static class KafkaTopics {
        static final String CREATE_MEASUREMENT = "CREATE_DEVICE_MEASUREMENT";
        static final String UPDATE_MEASUREMENT = "UPDATE_DEVICE_MEASUREMENT";
        static final String DELETE_MEASUREMENT = "DELETE_DEVICE_MEASUREMENT";

        private KafkaTopics() {
        }
    }

    public static class MessageProcessingException extends RuntimeException {
        public MessageProcessingException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
