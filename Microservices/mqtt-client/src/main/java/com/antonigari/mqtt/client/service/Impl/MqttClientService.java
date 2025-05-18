package com.antonigari.mqtt.client.service.Impl;

import com.antonigari.grpc.client.model.DeviceMeasurementDto;
import com.antonigari.mqtt.client.config.mqtt.MqttClientConfig;
import com.antonigari.mqtt.client.config.mqtt.MqttCustomClient;
import com.antonigari.mqtt.client.service.utilities.MqttMessageHandler;
import com.antonigari.mqtt.client.service.utilities.MqttSubscriptionManager;
import com.antonigari.mqtt.client.service.utilities.PayloadGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
@Slf4j
@AllArgsConstructor
@EnableScheduling
public class MqttClientService {
    private final MqttClientConfig mqttClientConfig;
    private final MqttCustomClient mqttCustomClient;
    private final DeviceMeasurementGrpcService measurementGrpcService;
    private final MqttSubscriptionManager subscriptionManager;
    private final PayloadGenerator payloadGenerator;
    private MqttMessageHandler messageHandler;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        this.establishMqttConnection()
                .exceptionally(MqttClientService::handleConnectionError);
    }

    private CompletableFuture<Void> establishMqttConnection() {
        return CompletableFuture.runAsync(() -> {
            try {
                this.connectMqttClient();
                this.initializeSubscriptions();
            } catch (final MqttException e) {
                throw new CompletionException("Failed to establish MQTT connection", e);
            }
        });
    }

    private void connectMqttClient() throws MqttException {
        this.mqttCustomClient.getMqttClient().connect(this.mqttClientConfig.mqttConnectionOptions());
        this.mqttCustomClient.getMqttClient().setCallback(this.messageHandler);
        this.mqttCustomClient.setConnected(true);
    }

    private void initializeSubscriptions() {
        this.subscriptionManager.clearSubscriptions();
        this.measurementGrpcService.getAllDeviceMeasurement()
                .forEach(this::addSubscription);
    }

    public synchronized void addSubscription(final DeviceMeasurementDto measure) {
        if (!this.subscriptionManager.addMeasurement(measure)) {
            return;
        }
        this.subscribeTopic(measure);
    }

    public synchronized void removeSubscription(final DeviceMeasurementDto measure) {
        if (!this.subscriptionManager.removeMeasurement(measure)) {
            return;
        }
        this.unsubscribeTopic(measure);
    }

    public synchronized void updateSubscription(final DeviceMeasurementDto measure) {
        final DeviceMeasurementDto existingMeasure =
                this.subscriptionManager.findMeasurementById(measure.deviceMeasurementId());
        if (existingMeasure == null) {
            log.warn("Cannot update subscription: Topic not found for measurement ID {}",
                    measure.deviceMeasurementId());
            return;
        }

        this.unsubscribeTopic(existingMeasure);
        this.addSubscription(measure);
    }

    private void subscribeTopic(final DeviceMeasurementDto measure) {
        try {
            this.mqttCustomClient.getMqttClient().subscribe(measure.topic(), 1);
            log.info("Successfully subscribed to topic: {}", measure.topic());
        } catch (final MqttException e) {
            log.error("Failed to subscribe to topic {}: {}", measure.topic(), e.getMessage());
        }
    }

    private void unsubscribeTopic(final DeviceMeasurementDto measure) {
        try {
            this.mqttCustomClient.getMqttClient().unsubscribe(measure.topic());
            log.info("Successfully unsubscribed from topic: {}", measure.topic());
        } catch (final MqttException e) {
            log.error("Failed to unsubscribe from topic {}: {}", measure.topic(), e.getMessage());
        }
    }

    private static Void handleConnectionError(final Throwable exception) {
        log.error("MQTT connection error: {}", exception.getMessage(), exception);
        return null;
    }

    @Scheduled(fixedRate = 1000)
    private void sendRandomMessages() {
        if (this.mqttCustomClient.isConnected()) {
            this.subscriptionManager.getAllDeviceMeasurement().forEach(measurementDto -> {
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

}
