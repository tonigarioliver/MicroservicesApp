package com.antonigari.RealTimeDataService.service.Impl;


import com.antonigari.RealTimeDataService.model.DeviceMeasurementDto;
import com.antonigari.RealTimeDataService.service.IKafkaConsumerService;
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
    private final MqttClientService mqttClientService;

    @Override
    @KafkaListener(topics = "#{'${spring.kafka.consumer.topics}'.split(',')}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(final String payload, @Header("kafka_receivedTopic") final String topic) {
        log.info(payload);
        final DeviceMeasurementDto measure;
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            measure = objectMapper.readValue(payload, DeviceMeasurementDto.class);
            this.processMessage(topic, measure);

        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(final String topic, final DeviceMeasurementDto measure) {
        switch (topic) {
            case "new-device":
                this.mqttClientService.addSubscription(measure);
                break;
            case "update-device":
                this.mqttClientService.updateSubscription(measure);
                break;
            case "delete-device":
                this.mqttClientService.removeSubscription(measure);
                break;
            default:
                log.warn("Received message for unknown topic {}: {}", topic, measure);
                break;
        }
    }

}
