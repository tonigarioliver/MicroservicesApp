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
    private final MqttClientService mqttClientService;
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();
    ;

    @Override
    @KafkaListener(topics = "#{'${spring.kafka.consumer.topics}'.split(',')}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(final String payload, @Header("kafka_receivedTopic") final String topic) {
        log.info(payload);
        final DeviceMeasurementDto measure;
        try {
            measure = this.objectMapper.readValue(payload, DeviceMeasurementDto.class);
            this.processMessage(topic, measure);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(final String topic, final DeviceMeasurementDto measure) {
        switch (topic) {
            case "CREATE_DEVICE_MEASUREMENT":
                this.mqttClientService.addSubscription(measure);
                break;
            case "UPDATE_DEVICE_MEASUREMENT":
                this.mqttClientService.updateSubscription(measure);
                break;
            case "DELETE_DEVICE_MEASUREMENT":
                this.mqttClientService.removeSubscription(measure);
                break;
            default:
                log.warn("Received message for unknown topic {}: {}", topic, measure);
                break;
        }
    }

}
