package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.model.DeviceMeasurementPayloadRequestDto;
import com.antonigari.iotdeviceservice.service.IDeviceMeasurementPayloadService;
import com.antonigari.iotdeviceservice.service.IKafkaListenerService;
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
public class KafkaListenerService implements IKafkaListenerService<String, String> {
    private final IDeviceMeasurementPayloadService deviceMeasurementPayloadService;

    @Override
    @KafkaListener(topics = "#{'${spring.kafka.consumer.topics}'.split(',')}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(final String payload, @Header("kafka_receivedTopic") final String topic) {
        log.info(payload);
        final DeviceMeasurementPayloadRequestDto deviceMeasurementPayload;
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            deviceMeasurementPayload = objectMapper.readValue(payload, DeviceMeasurementPayloadRequestDto.class);
            this.processMessage(topic, deviceMeasurementPayload);

        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(final String topic, final DeviceMeasurementPayloadRequestDto deviceMeasurementPayload) {
        switch (topic) {
            case "new-measure-payload":
                final DeviceMeasurementPayloadRequestDto newPayload = DeviceMeasurementPayloadRequestDto.builder()
                        .booleanValue(deviceMeasurementPayload.getBooleanValue())
                        .deviceMeasurementId(deviceMeasurementPayload.getDeviceMeasurementId())
                        .numValue(deviceMeasurementPayload.getNumValue())
                        .stringValue(deviceMeasurementPayload.getStringValue())
                        .build();
                this.deviceMeasurementPayloadService.create(newPayload);
                break;
            default:
                log.warn("Received message for unknown topic {}: {}", topic, deviceMeasurementPayload);
                break;
        }
    }
}
