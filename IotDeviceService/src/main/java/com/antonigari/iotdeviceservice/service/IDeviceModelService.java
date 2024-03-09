package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.DeviceModelDto;
import com.antonigari.iotdeviceservice.model.DeviceModelRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceModelsDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IDeviceModelService extends ICrudService<DeviceModelDto, DeviceModelRequestDto, DeviceModelRequestDto, DeviceModelsDto> {
    @Async
    CompletableFuture<DeviceModelDto> getAsyncBySerialNumber(String serialNumber);

    @Async
    CompletableFuture<DeviceModelDto> getAsyncById(long id);
}