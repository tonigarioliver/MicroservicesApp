package com.antonigari.RealTimeDataService.service.utilities;

import com.antonigari.RealTimeDataService.config.mqtt.MqttCustomClient;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
@Component
public class MqttMessageHandler implements org.eclipse.paho.mqttv5.client.MqttCallback {
    private final MqttCustomClient mqttCustomClient;
    private final WebSocketClientManager webSocketClientManager;

    public MqttMessageHandler(final MqttCustomClient mqttCustomClient, final WebSocketClientManager webSocketClientManager) {
        this.mqttCustomClient = mqttCustomClient;
        this.webSocketClientManager = webSocketClientManager;
    }

    @Override
    public void disconnected(final MqttDisconnectResponse mqttDisconnectResponse) {
    }

    @Override
    public void mqttErrorOccurred(final MqttException e) {
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) throws Exception {
        final String messagePayload = new String(message.getPayload(), StandardCharsets.UTF_8);
        log.info("Received message on topic '{}': {}", topic, messagePayload);
        this.sendToSubscribers(topic, messagePayload);
    }

    private void sendToSubscribers(final String topic, final String messagePayload) {
        this.webSocketClientManager.getAllSessionsByTopic(topic)
                .forEach(webSocketSession -> {
                            try {
                                webSocketSession
                                        .sendMessage(new TextMessage(messagePayload));
                            } catch (final IOException e) {
                                log.error(String.format("Error Websocket with reason %s", e.getMessage()));
                            }
                        }
                );

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
