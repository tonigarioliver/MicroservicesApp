package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceModelDto;
import com.antonigari.iotdeviceservice.model.DeviceModelRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceModelsDto;
import com.antonigari.iotdeviceservice.model.DeviceTopicDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import com.antonigari.iotdeviceservice.service.ICrudService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class DeviceModelManagerService implements ICrudService<DeviceModelDto, DeviceModelRequestDto, DeviceModelRequestDto, DeviceModelsDto> {
    private final KafkaProducerService kafkaProducerService;
    private final DeviceTopicService deviceTopicService;
    private final DeviceModelService deviceModelService;
    private final DeviceService deviceService;

    @Override
    @Async
    public CompletableFuture<DeviceModelsDto> getAllAsync() {
        return this.deviceModelService.getAllAsync();
    }

    @Async
    public CompletableFuture<DeviceModelDto> getAsyncBySerialNumber(final String serialNumber) {
        return this.deviceModelService.getAsyncBySerialNumber(serialNumber);
    }

    @Override
    public DeviceModelDto create(final DeviceModelRequestDto deviceModelRequestDto) {
        return this.deviceModelService.create(deviceModelRequestDto);
    }

    @Override
    public DeviceModelDto update(final long id, final DeviceModelRequestDto deviceModelRequestDto) {
        final DeviceModelDto deviceModelUpdated = this.deviceModelService.update(id, deviceModelRequestDto);
        final DevicesDto devicesUpdated = this.deviceService.getAsyncByDeviceModelSerialNumber(deviceModelUpdated.getSerialNumber()).join();
        final Map<DeviceDto, DeviceTopicDto> deviceToTopicsMap = devicesUpdated.getDevices().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        deviceUpdated -> this.deviceTopicService.getAsyncByManufactureCode(deviceUpdated.getManufactureCode()).join()
                ));

        deviceToTopicsMap.entrySet().stream()
                .map(entry -> this.deviceTopicService.update(entry.getValue().getId(), entry.getKey()))
                .forEach(topic -> this.kafkaProducerService.sendDeviceTopicMessageToKafka("update-device", topic));

        return deviceModelUpdated;
    }

    @Override
    public void delete(final long id) {
        final DevicesDto devicesDto = this.deviceService.
                getAsyncByDeviceModelSerialNumber(this.deviceModelService.getAsyncById(id).join().getSerialNumber()).join();
        final List<DeviceTopicDto> deviceTopicsList = devicesDto.getDevices().stream()
                .map(device -> this.deviceTopicService.getAsyncByManufactureCode(device.getManufactureCode()).join())
                .toList();

        this.deviceModelService.delete(id);

        deviceTopicsList.forEach(topic -> this.kafkaProducerService.sendDeviceTopicMessageToKafka("delete-device", topic));
    }
}
