package com.antonigari.RealTimeDataService.service.Impl;


import com.antonigari.RealTimeDataService.config.mqtt.MqttClientConfig;
import com.antonigari.RealTimeDataService.config.mqtt.MqttCustomClient;
import com.antonigari.RealTimeDataService.model.MqttTopic;
import com.antonigari.RealTimeDataService.service.utilities.MqttMessageHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@AllArgsConstructor
public class MqttClientService {

    private final MqttClientConfig mqttClientConfig;
    private final MqttCustomClient mqttCustomClient;
    private final DeviceTopicService deviceTopicService;
    private final Set<MqttTopic> mqttTopics = new HashSet<>();

    @PostConstruct
    public void initialize() {
        this.mqttTopics.addAll(this.deviceTopicService.getDeviceTopics());
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
            this.mqttTopics.clear();
            this.deviceTopicService.getDeviceTopics().forEach(this::addSubscription);
        } catch (final MqttException e) {
            log.error("Error en la conexión MQTT: " + e.getMessage(), e);
            future.completeExceptionally(e);
        }
        return future;
    }

    public synchronized void addSubscription(final MqttTopic topic) {
        if (this.mqttTopics.contains(topic)) {
            log.info("Topic already exists: " + topic);
            return;
        }
        log.info("New topic added: " + topic);
        this.mqttTopics.add(topic);
        this.subscribeTopic(topic);
    }

    public synchronized void removeSubscription(final MqttTopic topic) {
        if (!this.mqttTopics.contains(topic)) {
            log.info("Topic does not exist: " + topic);
            return;
        }
        log.info("Topic to remove: " + topic);
        this.mqttTopics.remove(topic);
        this.unsubscribeTopic(topic);
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
        log.info("Topic updated: " + updatedTopic);
        this.unsubscribeTopic(existingTopic);
        this.addSubscription(updatedTopic);
    }

    private void unsubscribeTopic(final MqttTopic topic) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            this.mqttCustomClient.getMqttClient().unsubscribe(objectMapper.writeValueAsString(topic));
        } catch (final MqttException e) {
            log.error(e.getMessage());
        } catch (final JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    private void subscribeTopic(final MqttTopic topic) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            this.mqttCustomClient.getMqttClient().subscribe(objectMapper.writeValueAsString(topic), 1);
        } catch (final MqttException e) {
            log.error(e.getMessage());
        } catch (final JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
