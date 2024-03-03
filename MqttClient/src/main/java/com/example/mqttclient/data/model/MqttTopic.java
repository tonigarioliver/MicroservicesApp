package com.example.mqttclient.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MqttTopic {
    private long id;
    private String topic;
    private String manufactureCode;
}