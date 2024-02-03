package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.NewPhysicalMeasurementRequestDto;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementDto;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementsDto;
import com.antonigari.iotdeviceservice.model.UpdatePhysicalMeasurementRequestDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IPhysicalMeasurementService extends
        ICrudService<PhysicalMeasurementDto,
        NewPhysicalMeasurementRequestDto,
        UpdatePhysicalMeasurementRequestDto,
        PhysicalMeasurementsDto>{
    @Async
    CompletableFuture<PhysicalMeasurementDto> findByName(String name);
}