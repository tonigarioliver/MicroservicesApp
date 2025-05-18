package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.MeasurementType;
import com.antonigari.iotdeviceservice.data.repository.MeasurementTypeRepository;
import com.antonigari.iotdeviceservice.model.MeasurementTypeDto;
import com.antonigari.iotdeviceservice.model.MeasurementTypesDto;
import com.antonigari.iotdeviceservice.service.IMeasurementTypeService;
import com.antonigari.iotdeviceservice.service.exception.ServiceErrorCatalog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
@Slf4j
public class MeasurementTypeService implements IMeasurementTypeService {
    private final MeasurementTypeRepository repository;
    private final ConversionService conversionService;

    @Override
    public CompletableFuture<MeasurementTypesDto> getAllAsync() {
        return CompletableFuture.completedFuture(MeasurementTypesDto.builder()
                .measurementTypes(this.repository.findAll().stream()
                        .map(payload -> this.conversionService.convert(payload, MeasurementTypeDto.class))
                        .toList())
                .build());
    }

    @Override
    public MeasurementTypeDto create(final MeasurementTypeDto Ndto) {
        throw new UnsupportedOperationException("update method is not implemented yet");
    }

    @Override
    public MeasurementTypeDto update(final long id, final MeasurementTypeDto Udto) {
        throw new UnsupportedOperationException("update method is not implemented yet");
    }

    @Override
    public void delete(final long id) {
        throw new UnsupportedOperationException("update method is not implemented yet");
    }

    @Override
    public CompletableFuture<MeasurementType> getAsyncById(final long id) {
        return CompletableFuture.supplyAsync(() ->
                this.repository.findById(id).orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("MeasurementType with ID " + id + " not found"))
        );
    }
}
