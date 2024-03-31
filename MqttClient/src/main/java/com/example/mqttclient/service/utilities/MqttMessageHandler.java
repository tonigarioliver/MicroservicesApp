package com.example.mqttclient.service.utilities;

import com.example.mqttclient.config.mqtt.MqttCustomClient;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

import java.nio.charset.StandardCharsets;


@Slf4j
public class MqttMessageHandler implements org.eclipse.paho.mqttv5.client.MqttCallback {
    private final MqttCustomClient mqttCustomClient;

    public MqttMessageHandler(final MqttCustomClient mqttCustomClient) {
        this.mqttCustomClient = mqttCustomClient;
    }

    @Override
    public void disconnected(final MqttDisconnectResponse mqttDisconnectResponse) {
        this.mqttCustomClient.setConnected(false);
    }

    @Override
    public void mqttErrorOccurred(final MqttException e) {
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) throws Exception {
        final String messagePayload = new String(message.getPayload(), StandardCharsets.UTF_8);
        log.info("Received message on topic '{}': {}", topic, messagePayload);
    }

    @Override
    public void deliveryComplete(final IMqttToken iMqttToken) {

    }

    @Override
    public void connectComplete(final boolean b, final String s) {
        this.mqttCustomClient.setConnected(true);
    }

    @Override
    public void authPacketArrived(final int i, final MqttProperties mqttProperties) {
    }

}
