package com.antonigari.GrpcServerService.service.converter;


import com.antonigari.GrpcServerService.data.model.MeasurementType;
import com.antonigari.GrpcServerService.model.MeasurementTypeDto;
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