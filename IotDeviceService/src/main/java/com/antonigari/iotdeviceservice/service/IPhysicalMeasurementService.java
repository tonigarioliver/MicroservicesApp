package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.PhysicalMeasurementRequestDto;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementDto;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementsDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IPhysicalMeasurementService extends
        ICrudService<PhysicalMeasurementDto,
                PhysicalMeasurementRequestDto,
                PhysicalMeasurementRequestDto,
        PhysicalMeasurementsDto>{
    @Async
    CompletableFuture<PhysicalMeasurementDto> findByName(String name);
}