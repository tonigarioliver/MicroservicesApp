package com.example.mqttclient.config.mqtt;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Slf4j
@Getter
public class MqttCustomClient {
    final MqttClient mqttClient;

    public MqttCustomClient(final MqttClientConfig mqttClientConfig) {
        final String clientId = UUID.randomUUID().toString();
        final String serverUrl = mqttClientConfig.getServerIp() + ":" + mqttClientConfig.getServerPort();
        try {
            this.mqttClient = new MqttClient(serverUrl, clientId, new MemoryPersistence());

        } catch (final MqttException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
