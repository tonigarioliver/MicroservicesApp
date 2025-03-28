package com.example.mqttclient.service.Impl;

import com.example.mqttclient.config.mqtt.MqttClientConfig;
import com.example.mqttclient.config.mqtt.MqttCustomClient;
import com.example.mqttclient.data.model.DeviceMeasurementDto;
import com.example.mqttclient.service.utilities.MqttMessageHandler;
import com.example.mqttclient.service.utilities.PayloadGenerator;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@AllArgsConstructor
@EnableScheduling
public class MqttClientService {

    private final MqttClientConfig mqttClientConfig;
    private final MqttCustomClient mqttCustomClient;
    private final DeviceMeasurementGrpcService measurementGrpcService;
    private final PayloadGenerator payloadGenerator;
    private final Set<DeviceMeasurementDto> deviceMeasurementList = new HashSet<>();

    @PostConstruct
    public void initialize() {
        try {
            final CompletableFuture<Void> connectionFuture = this.mqttConnectAsync();
            connectionFuture.thenAccept(result -> {
            }).exceptionally(exception -> {
                log.error("Error en la conexión MQTT: " + exception.getMessage(), exception);
                return null;
            });
        } catch (final Exception e) {
            log.error("Error en la inicialización: " + e.getMessage(), e);
        }
    }

    public CompletableFuture<Void> mqttConnectAsync() {
        final CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            this.mqttCustomClient.getMqttClient().connect(this.mqttClientConfig.mqttConnectionOptions());
            future.complete(null);
            final MqttMessageHandler listener = new MqttMessageHandler(this.mqttCustomClient);
            this.mqttCustomClient.getMqttClient().setCallback(listener);
            this.deviceMeasurementList.clear();
            this.measurementGrpcService.getAllDeviceMeasurement().forEach(this::addSubscription);
            this.mqttCustomClient.setConnected(true);
        } catch (final MqttException e) {
            log.error("Error en la conexión MQTT: " + e.getMessage(), e);
            future.completeExceptionally(e);
        }
        return future;
    }

    public synchronized void addSubscription(final DeviceMeasurementDto measure) {
        if (this.deviceMeasurementList.contains(measure)) {
            log.warn("Topic already exists: " + measure);
            return;
        }
        log.info("New topic added: " + measure);
        this.deviceMeasurementList.add(measure);
        this.subscribeTopic(measure);
    }

    public synchronized void removeSubscription(final DeviceMeasurementDto measure) {
        if (!this.deviceMeasurementList.contains(measure)) {
            log.warn("Topic does not exist: " + measure);
            return;
        }
        log.info("Topic to remove: " + measure);
        this.deviceMeasurementList.remove(measure);
        this.unsubscribeTopic(measure);
    }

    public synchronized void updateSubscription(final DeviceMeasurementDto measure) {
        // Search for the topic with the same serialNumber
        final DeviceMeasurementDto existingMeasure = this.deviceMeasurementList.stream()
                .filter(topic -> topic.deviceMeasurementId().equals(measure.deviceMeasurementId()))
                .findFirst()
                .orElse(null);

        if (existingMeasure == null) {
            log.warn("Topic not found for update: " + measure);
            return;
        }
        log.info("Topic updated: " + measure);
        this.unsubscribeTopic(existingMeasure);
        this.addSubscription(measure);
    }

    @Scheduled(fixedRate = 1000)
    private void sendRandomMessages() {
        if (this.mqttCustomClient.isConnected()) {
            this.deviceMeasurementList.forEach(measurementDto -> {
                final MqttMessage message = new MqttMessage(
                        this.payloadGenerator.generatePayload(measurementDto).getBytes(StandardCharsets.UTF_8)
                );
                try {
                    this.mqttCustomClient.getMqttClient().publish(measurementDto.topic(), message);
                } catch (final MqttException e) {
                    log.error(e.getMessage());
                }
            });
        } else {
            log.warn("MQTT connection not established yet. Skipping message sending.");
        }
    }

    private void unsubscribeTopic(final DeviceMeasurementDto measure) {
        try {
            this.mqttCustomClient.getMqttClient().unsubscribe(measure.topic());
        } catch (final MqttException e) {
            log.error(e.getMessage());
        }
    }

    private void subscribeTopic(final DeviceMeasurementDto measure) {
        try {
            this.mqttCustomClient.getMqttClient().subscribe(measure.topic(), 1);
        } catch (final MqttException e) {
            log.error(e.getMessage());
        }
    }
}
