package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import com.antonigari.iotdeviceservice.model.NewDeviceRequestDto;
import com.antonigari.iotdeviceservice.model.UpdateDeviceRequestDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IDeviceService extends ICrudService<DeviceDto,NewDeviceRequestDto, UpdateDeviceRequestDto,DevicesDto> {
    @Async
    CompletableFuture<DeviceDto> getAsyncBySerialNumber(String manufactureCode);
}
