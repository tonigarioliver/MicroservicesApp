package com.example.mqttclient.service.Impl;

import com.antonigari.MqttClient.GetAllDeviceTopicRequest;
import com.antonigari.MqttClient.GetAllDeviceTopicResponse;
import com.antonigari.MqttClient.GrpcDeviceTopicServiceGrpc;
import com.example.mqttclient.data.model.MqttTopic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceTopicService {
    private final GrpcDeviceTopicServiceGrpc.GrpcDeviceTopicServiceBlockingStub grpcDeviceTopicServiceBlockingStub;

    public List<MqttTopic> getDeviceTopics() {
        final GetAllDeviceTopicResponse response = this.grpcDeviceTopicServiceBlockingStub
                .getAllDeviceTopic(GetAllDeviceTopicRequest.newBuilder().build());
        System.out.println(response);
        return response.getDeviceTopicsList().stream()
                .map(deviceTopicGrpc -> MqttTopic.builder()
                        .id(deviceTopicGrpc.getId())
                        .topic(deviceTopicGrpc.getTopic())
                        .manufactureCode(deviceTopicGrpc.getManufactureCode())
                        .build())
                .toList();
    }
}
