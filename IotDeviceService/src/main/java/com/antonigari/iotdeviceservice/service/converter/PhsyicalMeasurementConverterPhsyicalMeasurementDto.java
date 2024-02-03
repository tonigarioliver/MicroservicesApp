package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.PhysicalMeasurement;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PhsyicalMeasurementConverterPhsyicalMeasurementDto implements Converter<PhysicalMeasurement, PhysicalMeasurementDto> {
    @Override
    @NonNull
    public PhysicalMeasurementDto convert(PhysicalMeasurement physicalMeasurement) {
        return PhysicalMeasurementDto.builder()
                .name(physicalMeasurement.getName())
                .unit(physicalMeasurement.getUnit())
                .build();
    }
}
