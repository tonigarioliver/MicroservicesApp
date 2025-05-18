package com.antonigari.grpc.server.service.service.converter;

import com.antonigari.grpc.server.service.data.model.DeviceMeasurement;
import com.antonigari.grpc.server.service.model.DeviceDto;
import com.antonigari.grpc.server.service.model.DeviceMeasurementDto;
import com.antonigari.grpc.server.service.model.MeasurementTypeDto;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceMeasurementConverterDeviceMeasurementDto implements Converter<DeviceMeasurement,
        DeviceMeasurementDto> {
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