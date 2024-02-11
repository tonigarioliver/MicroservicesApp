package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.PhysicalMeasurement;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementRequestDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class PhysicalMeasurementRequestDtoConverterPhysicalMeasurement implements Converter<PhysicalMeasurementRequestDto, PhysicalMeasurement> {
    @Override
    @NonNull
    public PhysicalMeasurement convert(PhysicalMeasurementRequestDto physicalMeasurementRequestDto) {
        return  PhysicalMeasurement.builder()
                .name(physicalMeasurementRequestDto.getName())
                .unit(physicalMeasurementRequestDto.getUnit())
                .build();
    }
}