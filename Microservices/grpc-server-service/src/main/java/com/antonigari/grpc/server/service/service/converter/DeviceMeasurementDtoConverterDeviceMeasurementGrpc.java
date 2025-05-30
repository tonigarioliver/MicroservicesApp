package com.antonigari.grpc.server.service.service.converter;

import com.antonigari.grpc.server.service.DeviceGrpc;
import com.antonigari.grpc.server.service.DeviceMeasurementGrpc;
import com.antonigari.grpc.server.service.MeasurementTypeGrpc;
import com.antonigari.grpc.server.service.MeasurementTypeNameGrpc;
import com.antonigari.grpc.server.service.data.model.MeasurementTypeName;
import com.antonigari.grpc.server.service.model.DeviceMeasurementDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceMeasurementDtoConverterDeviceMeasurementGrpc implements Converter<DeviceMeasurementDto,
        DeviceMeasurementGrpc> {
    @Override
    public DeviceMeasurementGrpc convert(final DeviceMeasurementDto measure) {
        return DeviceMeasurementGrpc.newBuilder()
                .setDeviceMeasurementId(measure.deviceMeasurementId())
                .setTopic(measure.topic())
                .setDevice(DeviceGrpc.newBuilder()
                        .setDeviceId(measure.device().getDeviceId())
                        .setManufactureCode(measure.device().getManufactureCode())
                        .build())
                .setMeasurementType(MeasurementTypeGrpc.newBuilder()
                        .setMeasurementTypeId(measure.measurementType().measurementTypeId())
                        .setTypeName(this.getGrpcMeasurementTypeName(measure.measurementType().typeName()))
                        .build())
                .build();
    }

    private MeasurementTypeNameGrpc getGrpcMeasurementTypeName(final MeasurementTypeName type) {
        switch (type) {
            case STRING -> {
                return MeasurementTypeNameGrpc.STRING;
            }
            case BOOLEAN -> {
                return MeasurementTypeNameGrpc.BOOLEAN;
            }
            case NUMERIC -> {
                return MeasurementTypeNameGrpc.NUMERIC;
            }
        }
        throw new UnsupportedOperationException("type name is not implemented yet");
    }

}
