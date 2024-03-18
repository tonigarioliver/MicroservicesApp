package com.antonigari.RealTimeDataService.service.Impl;

import com.antonigari.RealTimeDataService.GetAllDeviceTopicRequest;
import com.antonigari.RealTimeDataService.GetAllDeviceTopicResponse;
import com.antonigari.RealTimeDataService.GrpcDeviceTopicServiceGrpc;
import com.antonigari.RealTimeDataService.model.MqttTopic;
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
