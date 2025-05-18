package com.antonigari.mqtt.client.service.Impl;

import com.antonigari.grpc.client.service.impl.DeviceMeasurementGrpcServiceImpl;
import com.antonigari.grpc.server.service.DeviceMeasurementGrpcServiceGrpc;
import org.springframework.stereotype.Service;

/**
 * Implementation of DeviceMeasurementGrpcService for MqttClient.
 * This class extends the shared implementation from the GrpcClientLibrary.
 */
@Service
public class DeviceMeasurementGrpcService extends DeviceMeasurementGrpcServiceImpl {

    public DeviceMeasurementGrpcService(
            final DeviceMeasurementGrpcServiceGrpc.DeviceMeasurementGrpcServiceBlockingStub measurementStub) {
        super(measurementStub);
    }
}
