package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.IotDeviceService.DeviceTopicGrpc;
import com.antonigari.IotDeviceService.GetAllDeviceTopicRequest;
import com.antonigari.IotDeviceService.GetAllDeviceTopicResponse;
import com.antonigari.IotDeviceService.GrpcDeviceTopicServiceGrpc;
import com.antonigari.iotdeviceservice.model.DeviceTopicsDto;
import com.antonigari.iotdeviceservice.service.IDeviceTopicService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class GrpcDeviceTopicService extends GrpcDeviceTopicServiceGrpc.GrpcDeviceTopicServiceImplBase {
    private final IDeviceTopicService deviceTopicService;

    @Override
    public void getAllDeviceTopic(final GetAllDeviceTopicRequest request, final StreamObserver<GetAllDeviceTopicResponse> responseObserver) {
        final DeviceTopicsDto deviceTopics = this.deviceTopicService.getAllAsync().join();
        final GetAllDeviceTopicResponse response = GetAllDeviceTopicResponse.newBuilder()
                .addAllDeviceTopics(deviceTopics.getDeviceTopics().stream()
                        .map(deviceTopic -> DeviceTopicGrpc.newBuilder()
                                .setId(deviceTopic.getId())
                                .setTopic(deviceTopic.getTopic())
                                .setManufactureCode(deviceTopic.getManufactureCode())
                                .build())
                        .toList()
                )
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

