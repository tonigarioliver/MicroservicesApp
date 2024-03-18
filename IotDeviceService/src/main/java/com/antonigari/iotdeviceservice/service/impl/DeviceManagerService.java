package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.data.model.DeviceMeasurement;
import com.antonigari.iotdeviceservice.data.model.KafkaProducerTopic;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;
import com.antonigari.iotdeviceservice.model.DeviceRequestDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import com.antonigari.iotdeviceservice.service.ICrudService;
import com.antonigari.iotdeviceservice.service.IDeviceMeasurementService;
import com.antonigari.iotdeviceservice.service.IDeviceService;
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
public class DeviceManagerService implements ICrudService<DeviceDto, DeviceRequestDto, DeviceRequestDto, DevicesDto> {
    private final IKafkaProducerService kafkaProducerService;
    private final IDeviceMeasurementService deviceMeasurementService;
    private final ConversionService conversionService;
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
        return this.deviceService.create(deviceRequestDto);
    }

    @Override
    public DeviceDto update(final long id, final DeviceRequestDto deviceRequestDto) {
        final DeviceDto deviceUpdated = this.deviceService.update(id, deviceRequestDto);
        final List<DeviceMeasurement> measures = this.deviceService.getAsyncById(deviceUpdated.getDeviceId()).join().getDeviceMeasurements();
        measures.forEach(measure -> this.kafkaProducerService.sendDeviceMeasurementStatus(
                KafkaProducerTopic.UPDATE_DEVICE_MEASUREMENT,
                this.conversionService.convert(measure, DeviceMeasurementDto.class)
        ));

        return deviceUpdated;
    }

    @Override
    public void delete(final long id) {
        final Device deviceToDelete = this.deviceService.getAsyncById(id).join();
        this.deviceService.delete(id);
        final List<DeviceMeasurement> measures = deviceToDelete.getDeviceMeasurements();
        measures.forEach(measure -> this.kafkaProducerService.sendDeviceMeasurementStatus(
                KafkaProducerTopic.DELETE_DEVICE_MEASUREMENT,
                this.conversionService.convert(measure, DeviceMeasurementDto.class)
        ));
    }
}
