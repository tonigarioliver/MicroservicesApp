package com.antonigari.grpc.server.service.service.impl;


import com.antonigari.grpc.server.service.DeviceMeasurementGrpc;
import com.antonigari.grpc.server.service.DeviceMeasurementGrpcServiceGrpc;
import com.antonigari.grpc.server.service.GetAllDeviceMeasurementRequest;
import com.antonigari.grpc.server.service.GetAllDeviceMeasurementResponse;
import com.antonigari.grpc.server.service.model.DeviceMeasurementsDto;
import com.antonigari.grpc.server.service.service.IDeviceMeasurementService;
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
    public void getAllDeviceMeasurement(final GetAllDeviceMeasurementRequest request,
                                        final StreamObserver<GetAllDeviceMeasurementResponse> responseObserver) {
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
