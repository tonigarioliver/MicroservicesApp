package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.*;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IDeviceModelService extends ICrudService<DeviceModelDto, DeviceModelRequestDto, DeviceModelRequestDto, DeviceModelsDto> {
    @Async
    CompletableFuture<DeviceModelDto> getAsyncBySerialNumber(String serialNumber);
}