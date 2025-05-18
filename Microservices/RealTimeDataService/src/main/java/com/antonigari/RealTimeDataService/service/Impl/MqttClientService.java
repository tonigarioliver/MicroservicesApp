package com.antonigari.RealTimeDataService.service.Impl;


import com.antonigari.RealTimeDataService.config.mqtt.MqttClientConfig;
import com.antonigari.RealTimeDataService.config.mqtt.MqttCustomClient;
import com.antonigari.RealTimeDataService.service.utilities.MqttMessageHandler;
import com.antonigari.RealTimeDataService.service.utilities.MqttSubscriptionManager;
import com.antonigari.RealTimeDataService.service.utilities.WebSocketClientManager;
import com.antonigari.grpcclient.model.DeviceMeasurementDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
@Slf4j
@AllArgsConstructor
public class MqttClientService {
    private final MqttClientConfig mqttClientConfig;
    private final MqttCustomClient mqttCustomClient;
    private final DeviceMeasurementGrpcService measurementGrpcService;
    private final MqttSubscriptionManager subscriptionManager;
    private final WebSocketClientManager webSocketClientManager;
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
        this.webSocketClientManager.removeWebSocketClientsWhenTopicRemoved(measure.topic());
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
        this.webSocketClientManager.updateWebSocketClientWhenTopicUpdate(existingMeasure.topic(), measure.topic());
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

    // WebSocket session management delegates
    public void addWebSocketClientTopic(final WebSocketSession session, final String topic) {
        this.webSocketClientManager.subscribe(session, topic);
    }

    public void removeWebSocketClientTopic(final WebSocketSession session, final String topic) {
        this.webSocketClientManager.removeWebSocketClientTopic(session, topic);
    }

    public void webSocketClientListenerDisconnected(final WebSocketSession session) {
        this.webSocketClientManager.removeWebSocketClient(session);
    }
}
