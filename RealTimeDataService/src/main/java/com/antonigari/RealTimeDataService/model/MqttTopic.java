package com.antonigari.RealTimeDataService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MqttTopic {
    private long id;
    private String topic;
    private String manufactureCode;
}