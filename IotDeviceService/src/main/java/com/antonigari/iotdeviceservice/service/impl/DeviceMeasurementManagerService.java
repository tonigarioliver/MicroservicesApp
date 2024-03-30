package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.KafkaProducerTopic;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDetailsDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementsDto;
import com.antonigari.iotdeviceservice.service.ICrudService;
import com.antonigari.iotdeviceservice.service.IDeviceMeasurementService;
import com.antonigari.iotdeviceservice.service.IKafkaProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Slf4j
@Service
public class DeviceMeasurementManagerService implements ICrudService<
        DeviceMeasurementDto,
        DeviceMeasurementRequestDto,
        DeviceMeasurementRequestDto,
        DeviceMeasurementsDto> {
    private final IKafkaProducerService kafkaProducerService;
    private final IDeviceMeasurementService deviceMeasurementService;

    @Override
    public CompletableFuture<DeviceMeasurementsDto> getAllAsync() {
        return this.deviceMeasurementService.getAllAsync();
    }

    public CompletableFuture<DeviceMeasurementDto> getAsyncByTopic(final String topic) {
        return this.deviceMeasurementService.getAsyncByTopic(topic);
    }

    public CompletableFuture<DeviceMeasurementDetailsDto> getDetailsAsyncById(final long id) {
        return this.deviceMeasurementService.getDetailsAsyncById(id);
    }

    @Override
    public DeviceMeasurementDto create(final DeviceMeasurementRequestDto Ndto) {
        final DeviceMeasurementDto result = this.deviceMeasurementService.create(Ndto);
        this.kafkaProducerService.sendDeviceMeasurementStatus(KafkaProducerTopic.CREATE_DEVICE_MEASUREMENT, result);
        return result;
    }

    @Override
    public DeviceMeasurementDto update(final long id, final DeviceMeasurementRequestDto Udto) {
        final DeviceMeasurementDto result = this.deviceMeasurementService.update(id, Udto);
        this.kafkaProducerService.sendDeviceMeasurementStatus(KafkaProducerTopic.UPDATE_DEVICE_MEASUREMENT, result);
        return result;
    }

    @Override
    public void delete(final long id) {
        final DeviceMeasurementDto deviceMeasurementDto = this.deviceMeasurementService.getAsyncById(id).join();
        this.deviceMeasurementService.delete(id);
        this.kafkaProducerService.sendDeviceMeasurementStatus(
                KafkaProducerTopic.DELETE_DEVICE_MEASUREMENT,
                deviceMeasurementDto
        );
    }
}
