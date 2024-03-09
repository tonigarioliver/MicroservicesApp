package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.data.model.DeviceModel;
import com.antonigari.iotdeviceservice.data.repository.DeviceModelRepository;
import com.antonigari.iotdeviceservice.data.repository.DeviceRepository;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceRequestDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import com.antonigari.iotdeviceservice.service.IDeviceService;
import com.antonigari.iotdeviceservice.service.exception.ServiceErrorCatalog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class DeviceService implements IDeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceModelRepository deviceModelRepository;
    private final ConversionService conversionService;

    @Async
    @Override
    public CompletableFuture<DevicesDto> getAllAsync() {
        return CompletableFuture.completedFuture(DevicesDto.builder()
                .devices(this.deviceRepository.findAll().stream()
                        .map(device -> this.conversionService.convert(device, DeviceDto.class))
                        .toList())
                .build());
    }

    @Async
    @Override
    public CompletableFuture<DeviceDto> getAsyncByManufactureCode(final String manufactureCode) {
        return CompletableFuture.supplyAsync(() ->
                this.deviceRepository.findByManufactureCode(manufactureCode)
                        .map(device -> this.conversionService.convert(device, DeviceDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog
                                .NOT_FOUND.exception("Device with manufactureCode " + manufactureCode + " not found"))
        );
    }

    @Override
    @Async
    public CompletableFuture<DevicesDto> getAsyncByDeviceModelSerialNumber(final String serialNumber) {
        return CompletableFuture.completedFuture(DevicesDto.builder()
                .devices(this.deviceRepository.findByDeviceModelSerialNumber(serialNumber).stream()
                        .map(device -> this.conversionService.convert(device, DeviceDto.class))
                        .toList())
                .build());
    }

    @Override
    public CompletableFuture<DeviceDto> getAsyncById(final long id) {
        return CompletableFuture.supplyAsync(() ->
                this.deviceRepository.findById(id)
                        .map(device -> this.conversionService.convert(device, DeviceDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog
                                .NOT_FOUND.exception("Device with ID " + id + " not found"))
        );
    }

    @Override
    public DeviceDto create(final DeviceRequestDto deviceRequestDto) {
        this.deviceRepository.findByManufactureCode(deviceRequestDto.getManufactureCode())
                .ifPresent(device -> {
                    throw ServiceErrorCatalog
                            .CONFLICT.exception("Device with serialNumber: " + deviceRequestDto.getManufactureCode() + " already exists");
                });
        final DeviceModel deviceModel = this.deviceModelRepository.findById(deviceRequestDto.getDeviceModelId())
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("DeviceModel with ID " + deviceRequestDto.getDeviceModelId() + " not found"));
        final Device newDevice = this.conversionService.convert(deviceRequestDto, Device.class);
        newDevice.setDeviceModel(deviceModel);
        return this.conversionService.convert(this.deviceRepository.save(newDevice), DeviceDto.class);
    }

    @Override
    public DeviceDto update(final long id, final DeviceRequestDto deviceRequestDto) {
        final Device existingDevice = this.deviceRepository.findById(id)
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("Device with ID " + id + " not found"));
        existingDevice.setPrice(deviceRequestDto.getPrice());
        existingDevice.setManufactureCode(deviceRequestDto.getManufactureCode());
        existingDevice.setManufactureDate(deviceRequestDto.getManufactureDate());
        return this.conversionService.convert(this.deviceRepository.save(existingDevice), DeviceDto.class);
    }


    @Override
    public void delete(final long id) {
        final Device deviceToDelete = this.deviceRepository.findById(id)
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("Device with ID " + id + " not found"));
        this.deviceRepository.delete(deviceToDelete);
    }
}
