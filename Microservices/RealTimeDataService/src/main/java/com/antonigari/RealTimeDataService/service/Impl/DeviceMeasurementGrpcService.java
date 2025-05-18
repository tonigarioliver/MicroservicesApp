package com.antonigari.RealTimeDataService.service.Impl;

import com.antonigari.grpcclient.service.impl.DeviceMeasurementGrpcServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Implementation of DeviceMeasurementGrpcService for RealTimeDataService.
 * This class extends the shared implementation from the GrpcClientLibrary.
 */
@Service
public class DeviceMeasurementGrpcService extends DeviceMeasurementGrpcServiceImpl {

    public DeviceMeasurementGrpcService(
            com.antonigari.grpcclient.grpc.DeviceMeasurementGrpcServiceGrpc.DeviceMeasurementGrpcServiceBlockingStub measurementStub) {
        super(measurementStub);
    }
}
