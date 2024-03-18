package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.IotDeviceService.DeviceMeasurementGrpc;
import com.antonigari.IotDeviceService.DeviceMeasurementGrpcServiceGrpc;
import com.antonigari.IotDeviceService.GetAllDeviceMeasurementRequest;
import com.antonigari.IotDeviceService.GetAllDeviceMeasurementResponse;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementsDto;
import com.antonigari.iotdeviceservice.service.IDeviceMeasurementService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.core.convert.ConversionService;

@GrpcService
@AllArgsConstructor
public class GrpcDeviceMeasurementService extends DeviceMeasurementGrpcServiceGrpc.DeviceMeasurementGrpcServiceImplBase {

    private final IDeviceMeasurementService service;
    private final ConversionService conversionService;

    @Override
    public void getAllDeviceMeasurement(final GetAllDeviceMeasurementRequest request, final StreamObserver<GetAllDeviceMeasurementResponse> responseObserver) {
        final DeviceMeasurementsDto measures = this.service.getAllAsync().join();

        final GetAllDeviceMeasurementResponse response = GetAllDeviceMeasurementResponse.newBuilder()
                .addAllDeviceMeasurement(measures.getDeviceMeasurements().stream()
                        .map(measure -> this.conversionService.convert(measure, DeviceMeasurementGrpc.class))
                        .toList()
                )
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
