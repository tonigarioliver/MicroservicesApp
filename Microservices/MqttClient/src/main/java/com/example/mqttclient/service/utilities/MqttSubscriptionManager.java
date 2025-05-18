package com.example.mqttclient.service.utilities;

import com.antonigari.grpcclient.model.DeviceMeasurementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class MqttSubscriptionManager {
    private final Set<DeviceMeasurementDto> measurements = new HashSet<>();

    public void clearSubscriptions() {
        this.measurements.clear();
    }

    public boolean addMeasurement(final DeviceMeasurementDto measure) {
        if (this.measurements.contains(measure)) {
            log.warn("Topic already exists: {}", measure);
            return false;
        }
        log.info("New topic added: {}", measure);
        this.measurements.add(measure);
        return true;
    }

    public boolean removeMeasurement(final DeviceMeasurementDto measure) {
        if (!this.measurements.contains(measure)) {
            log.warn("Topic does not exist: {}", measure);
            return false;
        }
        log.info("Topic removed: {}", measure);
        this.measurements.remove(measure);
        return true;
    }

    public DeviceMeasurementDto findMeasurementById(final Long measurementId) {
        return this.measurements.stream()
                .filter(topic -> topic.deviceMeasurementId().equals(measurementId))
                .findFirst()
                .orElse(null);
    }

    public Set<DeviceMeasurementDto> getAllDeviceMeasurement() {
        return this.measurements;
    }
}