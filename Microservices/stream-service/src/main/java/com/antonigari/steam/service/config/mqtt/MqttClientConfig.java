package com.antonigari.steam.service.config.mqtt;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@Slf4j
public class MqttClientConfig {
    @Value("${mqtt.server.ip}")
    private String serverIp;
    @Value("${mqtt.server.port}")
    private String serverPort;
    @Value("${mqtt.qos}")
    private String qos;

    public MqttConnectionOptions mqttConnectionOptions() {
        final MqttConnectionOptions options = new MqttConnectionOptions();
        options.setAutomaticReconnect(true);
        options.setCleanStart(true);
        options.setConnectionTimeout(10);
        return options;
    }

}
