package com.antonigari.RealTimeDataService.service.Impl;

import com.antonigari.RealTimeDataService.model.MqttTopic;
import com.antonigari.RealTimeDataService.service.IKafkaService;
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
public class KafkaService implements IKafkaService {


    @Override
    @KafkaListener(topics = "#{'${spring.kafka.consumer.topics}'.split(',')}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(final String payload, @Header("kafka_receivedTopic") final String topic) {
        log.info(payload);
        final MqttTopic mqttTopic;
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            mqttTopic = objectMapper.readValue(payload, MqttTopic.class);
            this.processMessage(topic, mqttTopic);

        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(final String topic, final MqttTopic mqttTopic) {
        switch (topic) {
            case "new-device":
                //this.mqttClientService.addSubscription(mqttTopic);
                break;
            case "update-device":
                //this.mqttClientService.updateSubscription(mqttTopic);
                break;
            case "delete-device":
                //this.mqttClientService.removeSubscription(mqttTopic);
                break;
            default:
                log.warn("Received message for unknown topic {}: {}", topic, mqttTopic);
                break;
        }
    }

    @Override
    public boolean sendTopicDeviceMessageWithKafka(final String topic, final DeviceTopicDto deviceTopic) {
        return false;
    }
}
