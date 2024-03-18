package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.DeviceMeasurement;
import com.antonigari.iotdeviceservice.data.model.DeviceMeasurementPayload;
import com.antonigari.iotdeviceservice.data.repository.DeviceMeasurementPayloadRepository;
import com.antonigari.iotdeviceservice.data.repository.DeviceMeasurementRepository;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementPayloadDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementPayloadRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementPayloadsDto;
import com.antonigari.iotdeviceservice.service.IDeviceMeasurementPayload;
import com.antonigari.iotdeviceservice.service.exception.ServiceErrorCatalog;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class DeviceMeasurementPayloadService implements IDeviceMeasurementPayload {
    private final DeviceMeasurementPayloadRepository repository;
    private final ConversionService conversionService;
    private final DeviceMeasurementRepository measurementRepository;

    @Override
    public CompletableFuture<DeviceMeasurementPayloadsDto> getAllAsync() {
        return CompletableFuture.completedFuture(DeviceMeasurementPayloadsDto.builder()
                .deviceMeasurementPayloads(this.repository.findAll().stream()
                        .map(payload -> this.conversionService.convert(payload, DeviceMeasurementPayloadDto.class))
                        .toList())
                .build());
    }

    @Override
    public DeviceMeasurementPayloadDto create(final DeviceMeasurementPayloadRequestDto Ndto) {
        final DeviceMeasurement measurement = this.measurementRepository.findById(Ndto.getDeviceMeasurementId()).orElseThrow(() -> ServiceErrorCatalog
                .NOT_FOUND.exception("DeviceMeasurement with ID " + Ndto.getDeviceMeasurementId() + " not found"));
        final DeviceMeasurementPayload payload = DeviceMeasurementPayload.builder()
                .deviceMeasurement(measurement)
                .booleanValue(Ndto.getBooleanValue())
                .numValue(Ndto.getNumValue())
                .stringValue(Ndto.getStringValue())
                .build();
        return this.conversionService.convert(this.repository.save(payload), DeviceMeasurementPayloadDto.class);
    }

    @Override
    public DeviceMeasurementPayloadDto update(final long id, final DeviceMeasurementPayloadRequestDto Udto) {
        throw new UnsupportedOperationException("update method is not implemented yet");
    }


    @Override
    public void delete(final long id) {
        final DeviceMeasurementPayload payload = this.repository.findById(id)
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("Payload with ID " + id + " not found"));

        this.repository.delete(payload);


    }
}
