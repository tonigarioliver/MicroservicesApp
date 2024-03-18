package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.data.model.MeasurementType;
import com.antonigari.iotdeviceservice.model.MeasurementTypeDto;
import com.antonigari.iotdeviceservice.model.MeasurementTypesDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IMeasurementTypeService extends ICrudService<
        MeasurementTypeDto,
        MeasurementTypeDto,
        MeasurementTypeDto,
        MeasurementTypesDto> {
    @Async
    CompletableFuture<MeasurementType> getAsyncById(long id);
}
