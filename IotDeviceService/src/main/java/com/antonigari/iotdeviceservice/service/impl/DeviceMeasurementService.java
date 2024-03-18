package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.data.model.DeviceMeasurement;
import com.antonigari.iotdeviceservice.data.repository.DeviceMeasurementRepository;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementsDto;
import com.antonigari.iotdeviceservice.service.IDeviceMeasurementService;
import com.antonigari.iotdeviceservice.service.IDeviceService;
import com.antonigari.iotdeviceservice.service.IMeasurementTypeService;
import com.antonigari.iotdeviceservice.service.exception.ServiceErrorCatalog;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
@Transactional
public class DeviceMeasurementService implements IDeviceMeasurementService {
    private final DeviceMeasurementRepository repository;
    private final IDeviceService deviceService;
    private final ConversionService conversionService;
    private final IMeasurementTypeService measurementTypeService;

    @Async
    @Override
    public CompletableFuture<DeviceMeasurementsDto> getAllAsync() {
        return CompletableFuture.completedFuture(DeviceMeasurementsDto.builder()
                .deviceMeasurements(this.repository.findAll().stream()
                        .map(deviceMeasurement -> this.conversionService.convert(deviceMeasurement, DeviceMeasurementDto.class))
                        .toList())
                .build());
    }

    @Async
    @Override
    public CompletableFuture<DeviceMeasurementDto> getAsyncByTopic(final String topic) {
        return CompletableFuture.supplyAsync(() ->
                this.repository.findByTopic(topic)
                        .map(measurement -> this.conversionService.convert(measurement, DeviceMeasurementDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog
                                .NOT_FOUND.exception("DeviceMeasurement with topic " + topic + " not found"))
        );
    }

    @Override
    public CompletableFuture<DeviceMeasurementDto> getAsyncById(final long id) {
        return CompletableFuture.supplyAsync(() ->
                this.repository.findById(id)
                        .map(measurement -> this.conversionService.convert(measurement, DeviceMeasurementDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog
                                .NOT_FOUND.exception("DeviceMeasurement with ID " + id + " not found"))
        );
    }

    @Override
    public DeviceMeasurementDto create(final DeviceMeasurementRequestDto deviceMeasurementRequestDto) {
        final Device device = this.deviceService.getAsyncById(deviceMeasurementRequestDto.getDeviceId()).join();
        final String topic = DeviceMeasurement.getTopic(device, deviceMeasurementRequestDto.getMeasurementName());
        this.checkTopic(topic);
        final DeviceMeasurement newMeasure = DeviceMeasurement.builder()
                .device(device)
                .measurementType(this.measurementTypeService
                        .getAsyncById(deviceMeasurementRequestDto.getMeasurementTypeId()).join())
                .topic(topic)
                .unit(deviceMeasurementRequestDto.getUnits())
                .build();
        return this.conversionService
                .convert(this.repository.save(newMeasure), DeviceMeasurementDto.class);
    }

    @Override
    public DeviceMeasurementDto update(final long deviceMeasurementId, final DeviceMeasurementRequestDto deviceMeasurementRequestDto) {
        final DeviceMeasurement existing = this.repository.findById(deviceMeasurementId)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND
                        .exception("DeviceMeasurement with ID " + deviceMeasurementId + " not found"));
        existing.setUnit(deviceMeasurementRequestDto.getUnits());
        final String newTopic = DeviceMeasurement.getTopic(existing.getDevice(), deviceMeasurementRequestDto.getMeasurementName());
        this.checkTopic(newTopic);
        existing.setName(deviceMeasurementRequestDto.getMeasurementName());
        existing.setTopic(newTopic);
        return this.conversionService.convert(existing, DeviceMeasurementDto.class);
    }

    @Override
    public void delete(final long deviceMeasurementId) {
        final DeviceMeasurement deviceMeasurementDelete = this.repository.findById(deviceMeasurementId)
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("DeviceMeasurement with ID " + deviceMeasurementId + " not found"));
        this.repository.delete(deviceMeasurementDelete);
    }

    private void checkTopic(final String topic) {
        this.repository.findByTopic(topic)
                .ifPresent(measurement -> {
                    throw ServiceErrorCatalog
                            .CONFLICT.exception("DeviceMeasuremnt with topic " + topic + " already exists");
                });
    }
}
