package com.antonigari.RealTimeDataService.service.Impl;

import com.antonigari.RealTimeDataService.config.mqtt.MqttClientConfig;
import com.antonigari.RealTimeDataService.config.mqtt.MqttCustomClient;
import com.antonigari.RealTimeDataService.model.DeviceMeasurementDto;
import com.antonigari.RealTimeDataService.service.utilities.MqttMessageHandler;
import com.antonigari.RealTimeDataService.service.utilities.WebSocketClientManager;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@AllArgsConstructor
public class MqttClientService {

    private final MqttClientConfig mqttClientConfig;
    private final MqttCustomClient mqttCustomClient;
    private final DeviceMeasurementGrpcService measurementGrpcService;
    private final Set<DeviceMeasurementDto> measurements = new HashSet<>();
    private MqttMessageHandler listener;
    private final WebSocketClientManager webSocketClientManager;


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
            this.listener = new MqttMessageHandler(this.mqttCustomClient, this.webSocketClientManager);
            this.mqttCustomClient.getMqttClient().setCallback(this.listener);
            this.measurements.clear();
            this.measurementGrpcService.getAllDeviceMeasurement().forEach(this::addSubscription);
            this.mqttCustomClient.setConnected(true);
        } catch (final MqttException e) {
            log.error("Error en la conexión MQTT: " + e.getMessage(), e);
            future.completeExceptionally(e);
        }
        return future;
    }

    public synchronized void addSubscription(final DeviceMeasurementDto measure) {
        if (this.measurements.contains(measure)) {
            log.warn("Topic already exists: " + measure);
            return;
        }
        log.info("New topic added: " + measure);
        this.measurements.add(measure);
        this.subscribeTopic(measure);
    }

    public synchronized void removeSubscription(final DeviceMeasurementDto measure) {
        if (!this.measurements.contains(measure)) {
            log.warn("Topic does not exist: " + measure);
            return;
        }
        log.info("Topic to remove: " + measure);
        this.measurements.remove(measure);
        this.unsubscribeTopic(measure);
        this.webSocketClientManager.removeWebSocketClientsWhenTopicRemoved(measure.topic());
    }

    public synchronized void updateSubscription(final DeviceMeasurementDto measure) {
        // Search for the topic with the same serialNumber
        final DeviceMeasurementDto existingMeasure = this.measurements.stream()
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
        this.webSocketClientManager.updateWebSocketClientWhenTopicUpdate(existingMeasure.topic(), measure.topic());
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

    public void addWebSocketClient(final WebSocketSession session, final String topic) {
        this.webSocketClientManager.subscribe(session, topic);
    }

    public void removeWebSocketClientTopic(final WebSocketSession session, final String topic) {
        this.webSocketClientManager.removeWebSocketClientTopic(session, topic);

        this.webSocketClientManager.getKeysWithEmptyLists().stream()
                .map(t -> this.measurements.stream().filter(measurementDto -> measurementDto.topic().equals(t)).findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(this::removeSubscription);

    }
}
