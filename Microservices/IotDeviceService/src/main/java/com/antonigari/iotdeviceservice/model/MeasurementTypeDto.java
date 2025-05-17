package com.antonigari.iotdeviceservice.model;

import com.antonigari.iotdeviceservice.data.model.MeasurementTypeName;
import lombok.Builder;


@Builder
public record MeasurementTypeDto(Long measurementTypeId, MeasurementTypeName typeName) {
}
