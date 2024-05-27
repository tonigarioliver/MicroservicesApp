package com.antonigari.GrpcServerService.service.impl;


import com.antonigari.GrpcServerService.data.repository.DeviceMeasurementRepository;
import com.antonigari.GrpcServerService.model.DeviceMeasurementDto;
import com.antonigari.GrpcServerService.model.DeviceMeasurementsDto;
import com.antonigari.GrpcServerService.service.IDeviceMeasurementService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
@Transactional
public class DeviceMeasurementService implements IDeviceMeasurementService {
    private final DeviceMeasurementRepository repository;
    private final ConversionService conversionService;

    @Async
    @Override
    public CompletableFuture<DeviceMeasurementsDto> getAllAsync() {
        return CompletableFuture.completedFuture(DeviceMeasurementsDto.builder()
                .deviceMeasurements(this.repository.findAll().stream()
                        .map(deviceMeasurement -> this.conversionService.convert(deviceMeasurement, DeviceMeasurementDto.class))
                        .toList())
                .build());
    }
}
