package com.antonigari.GrpcServerService.service.converter;

import com.antonigari.GrpcServerService.data.model.DeviceMeasurement;
import com.antonigari.GrpcServerService.model.DeviceDto;
import com.antonigari.GrpcServerService.model.DeviceMeasurementDto;
import com.antonigari.GrpcServerService.model.MeasurementTypeDto;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceMeasurementConverterDeviceMeasurementDto implements Converter<DeviceMeasurement, DeviceMeasurementDto> {
    private final ConversionService conversionService;

    public DeviceMeasurementConverterDeviceMeasurementDto(@Lazy final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    @NonNull
    public DeviceMeasurementDto convert(final DeviceMeasurement deviceMeasurement) {
        return DeviceMeasurementDto.builder()
                .deviceMeasurementId(deviceMeasurement.getDeviceMeasurementId())
                .device(this.conversionService.convert(deviceMeasurement.getDevice(), DeviceDto.class))
                .measurementType(this.
                        conversionService.convert(deviceMeasurement.getMeasurementType(), MeasurementTypeDto.class))
                .topic(deviceMeasurement.getTopic())
                .name(deviceMeasurement.getName())
                .unit(deviceMeasurement.getUnit())
                .build();
    }
}