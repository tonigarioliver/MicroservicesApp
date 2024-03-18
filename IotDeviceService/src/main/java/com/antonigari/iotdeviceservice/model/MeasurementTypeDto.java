package com.antonigari.iotdeviceservice.model;

import com.antonigari.iotdeviceservice.data.model.MeasurementTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class MeasurementTypeDto {
    Long measurementTypeId;
    MeasurementTypeName typeName;
}
