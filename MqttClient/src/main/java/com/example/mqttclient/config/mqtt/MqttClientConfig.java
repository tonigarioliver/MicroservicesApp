package com.example.mqttclient.config.mqtt;

import lombok.Data;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Data
public class MqttClientConfig {
    @Value("${mqtt.server.ip}")
    private String serverIp;
    @Value("${mqtt.server.port}")
    private String serverPort;
    @Value("${mqtt.qos}")
    private String qos;

    public MqttClient mqttClient() throws Exception {
        final String clientId = UUID.randomUUID().toString();
        final String serverUrl = this.serverIp + ":" + this.serverPort;
        final MqttClient mqttClient = new MqttClient(serverUrl, clientId, new MemoryPersistence());
        return mqttClient;
    }

    public MqttConnectionOptions mqttConnectionOptions() throws Exception {
        final MqttConnectionOptions options = new MqttConnectionOptions();
        options.setAutomaticReconnect(true);
        options.setCleanStart(true);
        options.setConnectionTimeout(10);
        return options;
    }

}
