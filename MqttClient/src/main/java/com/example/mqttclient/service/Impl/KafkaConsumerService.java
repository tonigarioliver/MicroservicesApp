package com.example.mqttclient.service.Impl;

import com.example.mqttclient.data.model.MqttTopic;
import com.example.mqttclient.service.IKafkaConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumerService implements IKafkaConsumerService {
    private final ObjectMapper objectMapper;
    private final MqttClientService mqttClientService;

    @Override
    @KafkaListener(topics = "#{'${spring.kafka.consumer.topics}'.split(',')}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(final String payload, @Header("kafka_receivedTopic") final String topic) {
        log.info(payload);
        final MqttTopic mqttTopic;
        try {
            mqttTopic = this.objectMapper.readValue(payload, MqttTopic.class);
            this.processMessage(topic, mqttTopic);

        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(final String topic, final MqttTopic mqttTopic) {
        switch (topic) {
            case "new-devie":
                this.mqttClientService.addSubscription(mqttTopic);
                break;
            case "update-device":
                this.mqttClientService.updateSubscription(mqttTopic);
                break;
            case "delete-device":
                this.mqttClientService.removeSubscription(mqttTopic);
                break;
            default:
                log.warn("Received message for unknown topic {}: {}", topic, mqttTopic);
                break;
        }
    }

}
