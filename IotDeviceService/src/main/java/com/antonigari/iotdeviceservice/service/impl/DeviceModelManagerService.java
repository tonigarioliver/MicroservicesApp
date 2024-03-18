package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.DeviceMeasurement;
import com.antonigari.iotdeviceservice.data.model.DeviceModel;
import com.antonigari.iotdeviceservice.data.model.KafkaProducerTopic;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;
import com.antonigari.iotdeviceservice.model.DeviceModelDto;
import com.antonigari.iotdeviceservice.model.DeviceModelRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceModelsDto;
import com.antonigari.iotdeviceservice.service.ICrudService;
import com.antonigari.iotdeviceservice.service.IDeviceModelService;
import com.antonigari.iotdeviceservice.service.IKafkaProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
@Slf4j
public class DeviceModelManagerService implements ICrudService<DeviceModelDto, DeviceModelRequestDto, DeviceModelRequestDto, DeviceModelsDto> {
    private final IKafkaProducerService kafkaProducerService;
    private final IDeviceModelService deviceModelService;
    private final ConversionService conversionService;

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
        final DeviceModel model = this.deviceModelService.getAsyncById(deviceModelUpdated.getDeviceModelId()).join();
        final List<DeviceMeasurement> measurements = model.getDevices().stream()
                .flatMap(device -> device.getDeviceMeasurements().stream())
                .toList();
        measurements.forEach(measure -> this.kafkaProducerService.sendDeviceMeasurementStatus(
                KafkaProducerTopic.UPDATE_DEVICE_MEASUREMENT,
                this.conversionService.convert(measure, DeviceMeasurementDto.class)
        ));
        return deviceModelUpdated;
    }

    @Override
    public void delete(final long id) {
        final DeviceModel model = this.deviceModelService.getAsyncById(id).join();
        this.deviceModelService.delete(id);
        final List<DeviceMeasurement> measurements = model.getDevices().stream()
                .flatMap(device -> device.getDeviceMeasurements().stream())
                .toList();
        measurements.forEach(measure -> this.kafkaProducerService.sendDeviceMeasurementStatus(
                KafkaProducerTopic.DELETE_DEVICE_MEASUREMENT,
                this.conversionService.convert(measure, DeviceMeasurementDto.class)
        ));
    }
}
