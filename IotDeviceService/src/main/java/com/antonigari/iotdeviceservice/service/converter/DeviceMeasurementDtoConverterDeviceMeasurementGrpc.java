package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.IotDeviceService.DeviceGrpc;
import com.antonigari.IotDeviceService.DeviceMeasurementGrpc;
import com.antonigari.IotDeviceService.MeasurementTypeGrpc;
import com.antonigari.IotDeviceService.MeasurementTypeNameGrpc;
import com.antonigari.iotdeviceservice.data.model.MeasurementTypeName;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceMeasurementDtoConverterDeviceMeasurementGrpc implements Converter<DeviceMeasurementDto, DeviceMeasurementGrpc> {
    @Override
    public DeviceMeasurementGrpc convert(final DeviceMeasurementDto measure) {
        return DeviceMeasurementGrpc.newBuilder()
                .setDeviceMeasurementId(measure.getDeviceMeasurementId())
                .setTopic(measure.getTopic())
                .setDevice(DeviceGrpc.newBuilder()
                        .setDeviceId(measure.getDevice().getDeviceId())
                        .setManufactureCode(measure.getDevice().getManufactureCode())
                        .build())
                .setMeasurementType(MeasurementTypeGrpc.newBuilder()
                        .setMeasurementTypeId(measure.getMeasurementType().getMeasurementTypeId())
                        .setTypeName(this.getGrpcMeasurementTypeName(measure.getMeasurementType().getTypeName()))
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
        throw new UnsupportedOperationException("update method is not implemented yet");
    }

}
