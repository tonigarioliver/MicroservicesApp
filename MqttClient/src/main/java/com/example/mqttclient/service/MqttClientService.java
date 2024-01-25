package com.example.mqttclient.service;

import com.example.mqttclient.config.MqttClientConfig;
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
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class MqttClientService {

    private final MqttClientConfig mqttClientConfig;
    private final MqttMessage message;
    private MqttClient mqttClient;
    private MqttConnectionOptions mqttConnectionOptions;

    @Autowired
    public MqttClientService(final MqttClientConfig mqttClientConfig) {
        this.mqttClientConfig = mqttClientConfig;
        this.message = new MqttMessage("helloworld".getBytes(StandardCharsets.UTF_8));
    }

    @PostConstruct
    public void initialize() {
        try {
            mqttClient = mqttClientConfig.mqttClient();
            mqttConnectionOptions = mqttClientConfig.mqttConnectionOptions();
            CompletableFuture<Void> connectionFuture = mqttConnectAsync();

            connectionFuture.thenAccept(result -> {
                addSubscription("pipo");
                try {
                    mqttClient.publish("pipo", message);
                } catch (MqttException e) {
                    throw new RuntimeException(e);
                }
            }).exceptionally(exception -> {
                log.error("Error en la conexión MQTT: " + exception.getMessage(), exception);
                return null;
            });
        } catch (Exception e) {
            log.error("Error en la inicialización: " + e.getMessage(), e);
        }
    }

    public CompletableFuture<Void> mqttConnectAsync() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            MqttMessageHandler listener = new MqttMessageHandler();
            mqttClient.setCallback(listener);
            mqttClient.connect(mqttConnectionOptions);
            future.complete(null);
        } catch (MqttException e) {
            log.error("Error en la conexión MQTT: " + e.getMessage(), e);
            future.completeExceptionally(e);
        }
        return future;
    }

    private class MqttMessageHandler implements org.eclipse.paho.mqttv5.client.MqttCallback {


        @Override
        public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {

        }

        @Override
        public void mqttErrorOccurred(MqttException e) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String messagePayload = new String(message.getPayload(), StandardCharsets.UTF_8);
            log.info("Received message on topic '{}': {}", topic, messagePayload);
            mqttClient.publish("pipo", message);
        }

        @Override
        public void deliveryComplete(IMqttToken iMqttToken) {

        }

        @Override
        public void connectComplete(boolean b, String s) {

        }

        @Override
        public void authPacketArrived(int i, MqttProperties mqttProperties) {

        }

    }

    public synchronized void addSubscription(String topic) {
        log.info("New topic added: " + topic);
        try {
            mqttClient.subscribe(topic, 1);
        } catch (MqttException e) {
            log.error("Error to subscribe new topic:" + topic + " error: " + e.getMessage(), e);
        }
    }
}
