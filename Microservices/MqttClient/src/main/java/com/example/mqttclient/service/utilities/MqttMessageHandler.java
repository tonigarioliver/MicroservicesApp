package com.example.mqttclient.service.utilities;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


@Slf4j
@Component
@AllArgsConstructor
public class MqttMessageHandler implements org.eclipse.paho.mqttv5.client.MqttCallback {


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
