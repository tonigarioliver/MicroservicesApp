package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.DeviceModel;
import com.antonigari.iotdeviceservice.data.repository.DeviceModelRepository;
import com.antonigari.iotdeviceservice.model.DeviceModelDto;
import com.antonigari.iotdeviceservice.model.DeviceModelsDto;
import com.antonigari.iotdeviceservice.model.DeviceModelRequestDto;
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
                .deviceModels(deviceModelRepository.findAll().stream()
                        .map(deviceModel -> conversionService.convert(deviceModel, DeviceModelDto.class))
                        .toList())
                .build());
    }

    @Async
    @Override
    public CompletableFuture<DeviceModelDto> getAsyncBySerialNumber(String serialNumber) {
        return CompletableFuture.supplyAsync(() ->
                deviceModelRepository.findBySerialNumber(serialNumber)
                        .map(deviceModel -> conversionService.convert(deviceModel, DeviceModelDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("DeviceModel with ID " + serialNumber + " not found"))
        );
    }

    @Override
    public DeviceModelDto create(DeviceModelRequestDto deviceModelRequestDto) {
        deviceModelRepository.findBySerialNumber(deviceModelRequestDto.getSerialNumber())
                .ifPresent(device -> {
                    throw ServiceErrorCatalog.CONFLICT.exception("DeviceModel with serialNumber: " + deviceModelRequestDto.getSerialNumber() + " already exists");
                });
        DeviceModel newDeviceModel = conversionService.convert(deviceModelRequestDto, DeviceModel.class);
        deviceModelRepository.save(newDeviceModel);
        return conversionService.convert(newDeviceModel, DeviceModelDto.class);
    }

    @Override
    public DeviceModelDto update(long deviceModelId, DeviceModelRequestDto deviceModelRequestDto) {
        DeviceModel existingDeviceModel = deviceModelRepository.findById(deviceModelId)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("DeviceModel with ID " + deviceModelId + " not found"));

        // Update fields as needed
        existingDeviceModel.setName(deviceModelRequestDto.getName());
        existingDeviceModel.setSerialNumber(deviceModelRequestDto.getSerialNumber());

        deviceModelRepository.save(existingDeviceModel);
        return conversionService.convert(existingDeviceModel, DeviceModelDto.class);
    }

    @Override
    public void delete(long deviceModelId) {
        DeviceModel deviceModelToDelete = deviceModelRepository.findById(deviceModelId)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("DeviceModel with ID " + deviceModelId + " not found"));

        deviceModelRepository.delete(deviceModelToDelete);
    }
}
