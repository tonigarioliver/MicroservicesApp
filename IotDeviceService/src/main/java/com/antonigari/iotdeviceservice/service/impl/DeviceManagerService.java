package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceTopicDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import com.antonigari.iotdeviceservice.service.ICrudService;
import com.antonigari.iotdeviceservice.service.IDeviceService;
import com.antonigari.iotdeviceservice.service.IDeviceTopicService;
import com.antonigari.iotdeviceservice.service.IKafkaProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
@Slf4j
public class DeviceManagerService implements ICrudService<DeviceDto, DeviceRequestDto, DeviceRequestDto, DevicesDto> {
    private final IKafkaProducerService kafkaProducerService;
    private final IDeviceTopicService deviceTopicService;
    private final IDeviceService deviceService;

    @Override
    @Async
    public CompletableFuture<DevicesDto> getAllAsync() {
        return this.deviceService.getAllAsync();
    }

    @Async
    public CompletableFuture<DeviceDto> getAsyncByManufactureCode(final String manufactureCode) {
        return this.deviceService.getAsyncByManufactureCode(manufactureCode);
    }

    @Override
    public DeviceDto create(final DeviceRequestDto deviceRequestDto) {
        final DeviceDto deviceCreated = this.deviceService.create(deviceRequestDto);
        final DeviceTopicDto deviceTopicCreated = this.deviceTopicService.create(deviceCreated);
        this.kafkaProducerService.sendDeviceTopicMessageToKafka("new-device", deviceTopicCreated);
        return deviceCreated;
    }

    @Override
    public DeviceDto update(final long id, final DeviceRequestDto deviceRequestDto) {
        final DeviceDto deviceUpdated = this.deviceService.update(id, deviceRequestDto);
        final DeviceTopicDto existingDeviceTopic = this.deviceTopicService.getAsyncByManufactureCode(deviceUpdated.getManufactureCode()).join();
        final DeviceTopicDto deviceTopicUpdated = this.deviceTopicService.update(existingDeviceTopic.getId(), deviceUpdated);
        this.kafkaProducerService.sendDeviceTopicMessageToKafka("update-device", deviceTopicUpdated);
        return deviceUpdated;
    }

    @Override
    public void delete(final long id) {
        final DeviceDto deviceToDelete = this.deviceService.getAsyncById(id).join();
        final DeviceTopicDto deviceTopic = this.deviceTopicService.getAsyncByManufactureCode(deviceToDelete.getManufactureCode()).join();
        this.deviceService.delete(id);
        this.kafkaProducerService.sendDeviceTopicMessageToKafka("delete-device", deviceTopic);
    }
}
