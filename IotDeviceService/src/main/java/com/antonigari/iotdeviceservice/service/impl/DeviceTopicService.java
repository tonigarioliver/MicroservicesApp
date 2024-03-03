package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.data.model.DeviceTopic;
import com.antonigari.iotdeviceservice.data.repository.DeviceRepository;
import com.antonigari.iotdeviceservice.data.repository.DeviceTopicRepository;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceTopicDto;
import com.antonigari.iotdeviceservice.model.DeviceTopicsDto;
import com.antonigari.iotdeviceservice.service.IDeviceTopicService;
import com.antonigari.iotdeviceservice.service.exception.ServiceErrorCatalog;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class DeviceTopicService implements IDeviceTopicService {
    private final DeviceTopicRepository deviceTopicRepository;
    private final DeviceRepository deviceRepository;
    private final ConversionService conversionService;

    @Override
    public CompletableFuture<DeviceTopicsDto> getAllAsync() {
        return CompletableFuture.completedFuture(DeviceTopicsDto.builder()
                .deviceTopics(this.deviceTopicRepository.findAll().stream()
                        .map(deviceTopic -> this.conversionService.convert(deviceTopic, DeviceTopicDto.class))
                        .toList())
                .build());
    }

    @Override
    public DeviceTopicDto create(final DeviceDto Ndto) {
        this.deviceTopicRepository.findByDeviceManufactureCode(Ndto.getManufactureCode())
                .ifPresent(device -> {
                    throw ServiceErrorCatalog
                            .CONFLICT.exception("DeviceTopic with manufacutreCode: " + Ndto.getManufactureCode() + " already exists");
                });
        final Device existingDevice = this.deviceRepository.findByManufactureCode(Ndto.getManufactureCode())
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("Device with manufacutreCode" + Ndto.getManufactureCode() + " not found"));
        final DeviceTopic newDeviceTopic = DeviceTopic.builder().device(existingDevice).topic(DeviceTopic.getTopic(existingDevice)).build();
        return this.conversionService.convert(this.deviceTopicRepository.save(newDeviceTopic), DeviceTopicDto.class);
    }

    @Override
    public DeviceTopicDto update(final long id, final DeviceDto Udto) {
        final Device existingDevice = this.deviceRepository.findByManufactureCode(Udto.getManufactureCode())
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("Device with manufacutreCode" + Udto.getManufactureCode() + " not found"));
        final DeviceTopic existingDeviceTopic = this.deviceTopicRepository.findByDeviceManufactureCode(Udto.getManufactureCode())
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("DeviceTopic with manufacutreCode" + Udto.getManufactureCode() + " not found"));
        existingDeviceTopic.setTopic(DeviceTopic.getTopic(existingDevice));
        return this.conversionService.convert(this.deviceTopicRepository.save(existingDeviceTopic), DeviceTopicDto.class);
    }

    @Override
    public void delete(final long id) {
        final DeviceTopic deviceTopicToDelete = this.deviceTopicRepository.findById(id)
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("DeviceTopic with ID " + id + " not found"));
        this.deviceTopicRepository.delete(deviceTopicToDelete);
    }


    @Override
    public CompletableFuture<DeviceTopicDto> getAsyncByManufactureCode(final String manufactureCode) {
        return CompletableFuture.supplyAsync(() ->
                this.deviceTopicRepository.findByDeviceManufactureCode(manufactureCode)
                        .map(deviceTopic -> this.conversionService.convert(deviceTopic, DeviceTopicDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog
                                .NOT_FOUND.exception("DeviceTopic with manufacture code " + manufactureCode + " not found"))
        );
    }

}

