package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceTopicDto;
import com.antonigari.iotdeviceservice.model.DeviceTopicsDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IDeviceTopicService extends
        ICrudService<DeviceTopicDto,
                DeviceDto,
                DeviceDto,
                DeviceTopicsDto> {

    @Async
    CompletableFuture<DeviceTopicDto> getAsyncByManufactureCode(String manufactureCode);
}