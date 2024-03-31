package com.example.mqttclient.service.utilities;

import com.example.mqttclient.data.model.DeviceMeasurementDto;
import com.example.mqttclient.data.model.DeviceMeasurementPayloadDto;
import com.example.mqttclient.data.model.MeasurementTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class PayloadGenerator {

    public String generatePayload(final DeviceMeasurementDto measure) {
        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(this.buildPayload(measure.measurementType().typeName()));
        } catch (final JsonProcessingException e) {
            log.error(e.getMessage());
            return "";
        }
    }

    private DeviceMeasurementPayloadDto buildPayload(final MeasurementTypeName type) {
        switch (type) {
            case STRING -> {
                return DeviceMeasurementPayloadDto.builder()
                        .stringValue(this.getRandomString())
                        .build();
            }
            case BOOLEAN -> {
                return DeviceMeasurementPayloadDto.builder()
                        .booleanValue(this.getRandomBoolean())
                        .build();
            }
            case NUMERIC -> {
                return DeviceMeasurementPayloadDto.builder()
                        .numValue(this.getRandomNumeric())
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
