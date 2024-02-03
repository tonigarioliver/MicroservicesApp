package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.PhysicalMeasurement;
import com.antonigari.iotdeviceservice.data.repository.PhysicalMeasurementRepository;
import com.antonigari.iotdeviceservice.model.NewPhysicalMeasurementRequestDto;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementDto;
import com.antonigari.iotdeviceservice.model.PhysicalMeasurementsDto;
import com.antonigari.iotdeviceservice.model.UpdatePhysicalMeasurementRequestDto;
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
    public CompletableFuture<PhysicalMeasurementDto> findByName(String name) {
        return CompletableFuture.supplyAsync(() ->
                physicalMeasurementRepository.findByName(name)
                        .map(physicalMeasurement -> conversionService.convert(physicalMeasurement, PhysicalMeasurementDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("PhysicalMeasurement with ID " + name + " not found"))
        );
    }

    @Override
    public PhysicalMeasurementDto create(NewPhysicalMeasurementRequestDto newPhysicalMeasurementRequestDto) {
        PhysicalMeasurement newPhysicalMeasurement = conversionService.convert(newPhysicalMeasurementRequestDto, PhysicalMeasurement.class);
        physicalMeasurementRepository.save(newPhysicalMeasurement);
        return conversionService.convert(newPhysicalMeasurement, PhysicalMeasurementDto.class);
    }

    @Override
    public PhysicalMeasurementDto update(Long physicalMeasurementId, UpdatePhysicalMeasurementRequestDto updatePhysicalMeasurementRequestDto) {
        PhysicalMeasurement existingPhysicalMeasurement = physicalMeasurementRepository.findById(physicalMeasurementId)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("PhysicalMeasurement with ID " + physicalMeasurementId + " not found"));

        // Update fields as needed
        existingPhysicalMeasurement.setName(updatePhysicalMeasurementRequestDto.getName());
        existingPhysicalMeasurement.setUnit(updatePhysicalMeasurementRequestDto.getUnit());

        physicalMeasurementRepository.save(existingPhysicalMeasurement);
        return conversionService.convert(existingPhysicalMeasurement, PhysicalMeasurementDto.class);
    }

    @Override
    public void delete(Long physicalMeasurementId) {
        PhysicalMeasurement physicalMeasurementToDelete = physicalMeasurementRepository.findById(physicalMeasurementId)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("PhysicalMeasurement with ID " + physicalMeasurementId + " not found"));

        physicalMeasurementRepository.delete(physicalMeasurementToDelete);
    }
}
