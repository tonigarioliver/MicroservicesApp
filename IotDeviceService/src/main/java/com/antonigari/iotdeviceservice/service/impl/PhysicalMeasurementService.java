package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.PhysicalMeasurement;
import com.antonigari.iotdeviceservice.data.repository.PhysicalMeasurementRepository;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementDto;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementRequestDto;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementsDto;
import com.antonigari.iotdeviceservice.service.IPhysicalMeasurementService;
import com.antonigari.iotdeviceservice.service.exception.ServiceErrorCatalog;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@AllArgsConstructor
public class PhysicalMeasurementService implements IPhysicalMeasurementService {
    private final PhysicalMeasurementRepository physicalMeasurementRepository;
    private final ConversionService conversionService;

    @Async
    @Override
    public CompletableFuture<PhysicalMeasurementsDto> getAllAsync() {
        return CompletableFuture.completedFuture(PhysicalMeasurementsDto.builder()
                .physicalMeasurements(physicalMeasurementRepository.findAll().stream()
                        .map(physicalMeasurement -> conversionService.convert(physicalMeasurement, PhysicalMeasurementDto.class))
                        .toList())
                .build());
    }

    @Async
    @Override
    public CompletableFuture<PhysicalMeasurementDto> findByName(final String name) {
        return CompletableFuture.supplyAsync(() ->
                physicalMeasurementRepository.findByName(name)
                        .map(physicalMeasurement -> conversionService.convert(physicalMeasurement, PhysicalMeasurementDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("PhysicalMeasurement with ID " + name + " not found"))
        );
    }

    @Override
    public PhysicalMeasurementDto create(final PhysicalMeasurementRequestDto physicalMeasurementRequestDto) {
        physicalMeasurementRepository.findByName(physicalMeasurementRequestDto.getName())
                .ifPresent(physicalMeasurement -> {
                    throw ServiceErrorCatalog.CONFLICT.exception("PhysicalMeasurement with NAME: " + physicalMeasurement.getName() + " already exists");
                });
        PhysicalMeasurement newPhysicalMeasurement = conversionService.convert(physicalMeasurementRequestDto, PhysicalMeasurement.class);
        physicalMeasurementRepository.save(newPhysicalMeasurement);
        return conversionService.convert(newPhysicalMeasurement, PhysicalMeasurementDto.class);
    }

    @Override
    public PhysicalMeasurementDto update(final long physicalMeasurementId, final PhysicalMeasurementRequestDto physicalMeasurementRequestDto) {
        PhysicalMeasurement existingPhysicalMeasurement = physicalMeasurementRepository.findById(physicalMeasurementId)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("PhysicalMeasurement with ID " + physicalMeasurementId + " not found"));

        existingPhysicalMeasurement.setName(physicalMeasurementRequestDto.getName());
        existingPhysicalMeasurement.setUnit(physicalMeasurementRequestDto.getUnit());

        physicalMeasurementRepository.save(existingPhysicalMeasurement);
        return conversionService.convert(existingPhysicalMeasurement, PhysicalMeasurementDto.class);
    }

    @Override
    public void delete(final long physicalMeasurementId) {
        PhysicalMeasurement physicalMeasurementToDelete = physicalMeasurementRepository.findById(physicalMeasurementId)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("PhysicalMeasurement with ID " + physicalMeasurementId + " not found"));

        physicalMeasurementRepository.delete(physicalMeasurementToDelete);
    }
}
