package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.data.repository.DeviceRepository;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import com.antonigari.iotdeviceservice.model.NewDeviceRequestDto;
import com.antonigari.iotdeviceservice.model.UpdateDeviceRequestDto;
import com.antonigari.iotdeviceservice.service.IDeviceService;
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
public class DeviceService implements IDeviceService {
    private final DeviceRepository deviceRepository;
    private final ConversionService conversionService;

    @Async
    @Override
    public CompletableFuture<DevicesDto> getAllAsync() {
        return CompletableFuture.completedFuture(DevicesDto.builder()
                .devices(deviceRepository.findAll().stream()
                        .map(device -> conversionService.convert(device, DeviceDto.class))
                        .toList())
                .build());
    }
    @Async
    @Override
    public CompletableFuture<DeviceDto> getAsyncByManufactureCode(final String manufactureCode) {
        return CompletableFuture.supplyAsync(() ->
                deviceRepository.findByManufactureCode(manufactureCode)
                        .map(device -> conversionService.convert(device, DeviceDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("Device with serialNumber " + manufactureCode + " not found"))
        );
    }

    @Override
    public DeviceDto create(final NewDeviceRequestDto newDeviceRequestDto) {
        deviceRepository.findByManufactureCode(newDeviceRequestDto.getManufactureCode())
                .ifPresent(device -> {
                    throw ServiceErrorCatalog.CONFLICT.exception("Device with serialNumber: " + newDeviceRequestDto.getManufactureCode() + " already exists");
                });
        final Device newDevice=conversionService.convert(newDeviceRequestDto,Device.class);
        deviceRepository.save(newDevice);
        return conversionService.convert(newDevice,DeviceDto.class);
    }

    @Override
    public DeviceDto update(long id, final UpdateDeviceRequestDto updateDeviceRequestDto) {
        Device existingDevice = deviceRepository.findById(id)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("DeviceModel with ID " + id + " not found"));

        // Update fields as needed
        existingDevice.setPrice(updateDeviceRequestDto.getPrice());
        existingDevice.setManufactureCode(updateDeviceRequestDto.getManufactureCode());
        existingDevice.setManufactureDate(updateDeviceRequestDto.getManufactureDate());
        deviceRepository.save(existingDevice);
        return conversionService.convert(existingDevice, DeviceDto.class);
    }

    @Override
    public void delete(final long id) {
        Device deviceToDelete = deviceRepository.findById(id)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("Device with ID " + id + " not found"));

        deviceRepository.delete(deviceToDelete);
    }

}
