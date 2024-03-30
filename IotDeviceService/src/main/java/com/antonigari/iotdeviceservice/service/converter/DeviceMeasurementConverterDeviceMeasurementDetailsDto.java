package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.DeviceMeasurement;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDetailsDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementPayloadDto;
import com.antonigari.iotdeviceservice.model.MeasurementTypeDto;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceMeasurementConverterDeviceMeasurementDetailsDto implements Converter<
        DeviceMeasurement,
        DeviceMeasurementDetailsDto
        > {
    private final ConversionService conversionService;

    DeviceMeasurementConverterDeviceMeasurementDetailsDto(@Lazy final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    @NonNull
    public DeviceMeasurementDetailsDto convert(final DeviceMeasurement deviceMeasurement) {
        return DeviceMeasurementDetailsDto.builder()
                .deviceMeasurementId(deviceMeasurement.getDeviceMeasurementId())
                .device(this.conversionService.convert(deviceMeasurement.getDevice(), DeviceDto.class))
                .measurementType(this.
                        conversionService.convert(deviceMeasurement.getMeasurementType(), MeasurementTypeDto.class))
                .topic(deviceMeasurement.getTopic())
                .name(deviceMeasurement.getName())
                .unit(deviceMeasurement.getUnit())
                .measures(deviceMeasurement.getDeviceMeasurementPayloads().stream()
                        .map(measure -> this.
                                conversionService.convert(deviceMeasurement.getMeasurementType(), DeviceMeasurementPayloadDto.class))
                        .toList())
                .build();
    }
}