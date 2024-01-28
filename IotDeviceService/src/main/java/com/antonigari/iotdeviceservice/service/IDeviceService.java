package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import com.antonigari.iotdeviceservice.model.NewDeviceRequestDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IDeviceService {
    @Async
    CompletableFuture<DevicesDto> getAllAsync();
    @Async
    CompletableFuture<DeviceDto> getAsyncBySerialNumber(String manufactureCode);

    DeviceDto create(NewDeviceRequestDto newDeviceRequestDto);
}
