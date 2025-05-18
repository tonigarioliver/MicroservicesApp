package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.DeviceModel;
import com.antonigari.iotdeviceservice.data.repository.DeviceModelRepository;
import com.antonigari.iotdeviceservice.model.DeviceModelDto;
import com.antonigari.iotdeviceservice.model.DeviceModelRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceModelsDto;
import com.antonigari.iotdeviceservice.service.IDeviceModelService;
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
public class DeviceModelService implements IDeviceModelService {
    private final DeviceModelRepository deviceModelRepository;
    private final ConversionService conversionService;

    @Async
    @Override
    public CompletableFuture<DeviceModelsDto> getAllAsync() {
        return CompletableFuture.completedFuture(DeviceModelsDto.builder()
                .deviceModels(this.deviceModelRepository.findAll().stream()
                        .map(deviceModel -> this.conversionService.convert(deviceModel, DeviceModelDto.class))
                        .toList())
                .build());
    }

    @Async
    @Override
    public CompletableFuture<DeviceModelDto> getAsyncBySerialNumber(final String serialNumber) {
        return CompletableFuture.supplyAsync(() ->
                this.deviceModelRepository.findBySerialNumber(serialNumber)
                        .map(deviceModel -> this.conversionService.convert(deviceModel, DeviceModelDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog
                                .NOT_FOUND.exception("DeviceModel with ID " + serialNumber + " not found"))
        );
    }

    @Override
    public CompletableFuture<DeviceModel> getAsyncById(final long id) {
        return CompletableFuture.supplyAsync(() ->
                this.deviceModelRepository.findById(id)
                        .orElseThrow(() -> ServiceErrorCatalog
                                .NOT_FOUND.exception("DeviceModel with ID " + id + " not found"))
        );
    }

    @Override
    public DeviceModelDto create(final DeviceModelRequestDto deviceModelRequestDto) {
        this.deviceModelRepository.findBySerialNumber(deviceModelRequestDto.getSerialNumber())
                .ifPresent(device -> {
                    throw ServiceErrorCatalog
                            .CONFLICT.exception("DeviceModel with serialNumber: " + deviceModelRequestDto.getSerialNumber() + " already exists");
                });
        final DeviceModel newDeviceModel = this.conversionService.convert(deviceModelRequestDto, DeviceModel.class);
        return this.conversionService.convert(this.deviceModelRepository.save(newDeviceModel), DeviceModelDto.class);
    }

    @Override
    public DeviceModelDto update(final long deviceModelId, final DeviceModelRequestDto deviceModelRequestDto) {
        final DeviceModel existingDeviceModel = this.deviceModelRepository.findById(deviceModelId)
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("DeviceModel with ID " + deviceModelId + " not found"));

        existingDeviceModel.setName(deviceModelRequestDto.getName());
        existingDeviceModel.setSerialNumber(deviceModelRequestDto.getSerialNumber());
        return this.conversionService.convert(this.deviceModelRepository.save(existingDeviceModel), DeviceModelDto.class);
    }

    @Override
    public void delete(final long deviceModelId) {
        final DeviceModel deviceModelToDelete = this.deviceModelRepository.findById(deviceModelId)
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("DeviceModel with ID " + deviceModelId + " not found"));

        this.deviceModelRepository.delete(deviceModelToDelete);
    }
}
