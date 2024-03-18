package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementsDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IDeviceMeasurementService extends ICrudService<
        DeviceMeasurementDto,
        DeviceMeasurementRequestDto,
        DeviceMeasurementRequestDto,
        DeviceMeasurementsDto> {
    @Async
    CompletableFuture<DeviceMeasurementDto> getAsyncById(long id);

    @Async
    CompletableFuture<DeviceMeasurementDto> getAsyncByTopic(String topic);

}