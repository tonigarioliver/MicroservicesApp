package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.MeasurementType;
import com.antonigari.iotdeviceservice.model.MeasurementTypeDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MeasurementTypeConverterMeasurementTypeDto implements Converter<MeasurementType, MeasurementTypeDto> {
    @Override
    @NonNull
    public MeasurementTypeDto convert(final MeasurementType measurementType) {
        return MeasurementTypeDto.builder()
                .measurementTypeId(measurementType.getMeasurementTypeId())
                .typeName(measurementType.getTypeName())
                .build();
    }
}