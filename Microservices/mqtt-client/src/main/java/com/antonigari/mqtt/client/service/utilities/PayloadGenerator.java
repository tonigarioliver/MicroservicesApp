package com.antonigari.mqtt.client.service.utilities;

import com.antonigari.grpc.client.model.DeviceMeasurementDto;
import com.antonigari.grpc.client.model.MeasurementTypeName;
import com.antonigari.mqtt.client.data.model.DeviceMeasurementPayloadDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
@AllArgsConstructor
public class PayloadGenerator {
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    public String generatePayload(final DeviceMeasurementDto measure) {
        try {
            return this.objectMapper.writeValueAsString(this.buildPayload(measure.measurementType().typeName(),
                    measure));
        } catch (final JsonProcessingException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    private DeviceMeasurementPayloadDto buildPayload(final MeasurementTypeName type,
                                                     final DeviceMeasurementDto measurementDto) {
        switch (type) {
            case STRING -> {
                return DeviceMeasurementPayloadDto.builder()
                        .stringValue(this.getRandomString())
                        .deviceMeasurementId(measurementDto.deviceMeasurementId())
                        .topic(measurementDto.topic())
                        .build();
            }
            case BOOLEAN -> {
                return DeviceMeasurementPayloadDto.builder()
                        .booleanValue(this.getRandomBoolean())
                        .deviceMeasurementId(measurementDto.deviceMeasurementId())
                        .topic(measurementDto.topic())
                        .build();
            }
            case NUMERIC -> {
                return DeviceMeasurementPayloadDto.builder()
                        .numValue(this.getRandomNumeric())
                        .deviceMeasurementId(measurementDto.deviceMeasurementId())
                        .topic(measurementDto.topic())
                        .build();
            }
        }
        throw new UnsupportedOperationException("type name is not implemented yet");
    }

    private Boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }

    private Float getRandomNumeric() {
        return new Random().nextFloat();
    }

    private String getRandomString() {
        return "RandomString";
    }
}
