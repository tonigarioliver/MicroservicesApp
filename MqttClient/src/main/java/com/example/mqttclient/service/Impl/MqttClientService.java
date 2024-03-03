package com.example.mqttclient.service.Impl;

import com.example.mqttclient.config.mqtt.MqttClientConfig;
import com.example.mqttclient.data.model.MqttTopic;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class MqttClientService {

    private final MqttClientConfig mqttClientConfig;
    private final MqttMessage message;
    private MqttClient mqttClient;
    private MqttConnectionOptions mqttConnectionOptions;
    private final Set<MqttTopic> mqttTopics = new HashSet<>();

    @Autowired
    public MqttClientService(final MqttClientConfig mqttClientConfig) {
        this.mqttClientConfig = mqttClientConfig;
        this.message = new MqttMessage("helloworld".getBytes(StandardCharsets.UTF_8));
    }

    @PostConstruct
    public void initialize() {
        try {
            this.mqttClient = this.mqttClientConfig.mqttClient();
            this.mqttConnectionOptions = this.mqttClientConfig.mqttConnectionOptions();
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
            final MqttMessageHandler listener = new MqttMessageHandler();
            this.mqttClient.setCallback(listener);
            this.mqttClient.connect(this.mqttConnectionOptions);
            future.complete(null);
        } catch (final MqttException e) {
            log.error("Error en la conexión MQTT: " + e.getMessage(), e);
            future.completeExceptionally(e);
        }
        return future;
    }

    private class MqttMessageHandler implements org.eclipse.paho.mqttv5.client.MqttCallback {

        @Override
        public void disconnected(final MqttDisconnectResponse mqttDisconnectResponse) {
        }

        @Override
        public void mqttErrorOccurred(final MqttException e) {
        }

        @Override
        public void messageArrived(final String topic, final MqttMessage message) throws Exception {
            final String messagePayload = new String(message.getPayload(), StandardCharsets.UTF_8);
            //log.info("Received message on topic '{}': {}", topic, messagePayload);
            MqttClientService.this.mqttClient.publish("pipo", message);
        }

        @Override
        public void deliveryComplete(final IMqttToken iMqttToken) {

        }

        @Override
        public void connectComplete(final boolean b, final String s) {
        }

        @Override
        public void authPacketArrived(final int i, final MqttProperties mqttProperties) {
        }

    }

    private synchronized void subscribeTopics() {
        try {
            final String[] topicsArray = this.mqttTopics.stream().map(MqttTopic::getTopic).toArray(String[]::new);
            final int[] qos = new int[topicsArray.length];
            this.mqttClient.unsubscribe(topicsArray);
            this.mqttClient.subscribe(topicsArray, qos);
            for (final String mqttTopic : topicsArray) {
                try {
                    this.mqttClient.publish(mqttTopic, this.message);
                } catch (final MqttException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (final MqttException e) {
            log.error("Error while updating subscription: " + e.getMessage(), e);
        }
    }


    public synchronized void addSubscription(final MqttTopic topic) {
        if (this.mqttTopics.contains(topic)) {
            log.info("Topic already exists: " + topic);
            return;
        }
        log.info("New topic added: " + topic);
        this.mqttTopics.add(topic);
        this.subscribeTopics();
    }

    public synchronized void removeSubscription(final MqttTopic topic) {
        if (!this.mqttTopics.contains(topic)) {
            log.info("Topic does not exist: " + topic);
            return;
        }
        log.info("Topic to remove: " + topic);
        this.mqttTopics.remove(topic);
        this.subscribeTopics();
    }

    public synchronized void updateSubscription(final MqttTopic updatedTopic) {
        // Search for the topic with the same serialNumber
        final MqttTopic existingTopic = this.mqttTopics.stream()
                .filter(topic -> topic.getManufactureCode().equals(updatedTopic.getManufactureCode()))
                .findFirst()
                .orElse(null);

        if (existingTopic == null) {
            log.info("Topic not found for update: " + updatedTopic);
            return;
        }

        // Remove existing topic and add the updated one
        this.mqttTopics.remove(existingTopic);
        this.mqttTopics.add(updatedTopic);

        log.info("Topic updated: " + updatedTopic);
        this.subscribeTopics();
    }
}
