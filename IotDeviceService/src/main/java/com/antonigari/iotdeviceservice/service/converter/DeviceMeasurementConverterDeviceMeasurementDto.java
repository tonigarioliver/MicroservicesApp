package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.DeviceMeasurement;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;
import com.antonigari.iotdeviceservice.model.MeasurementTypeDto;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceMeasurementConverterDeviceMeasurementDto implements Converter<DeviceMeasurement, DeviceMeasurementDto> {
    private final ConversionService conversionService;

    DeviceMeasurementConverterDeviceMeasurementDto(@Lazy final ConversionService conversionService) {
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
                .build();
    }
}