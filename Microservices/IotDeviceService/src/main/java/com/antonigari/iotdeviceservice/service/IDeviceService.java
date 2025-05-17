package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceRequestDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface IDeviceService extends ICrudService<DeviceDto, DeviceRequestDto, DeviceRequestDto, DevicesDto> {
    @Async
    CompletableFuture<DeviceDto> getAsyncByManufactureCode(String manufactureCode);

    @Async
    CompletableFuture<DevicesDto> getAsyncByDeviceModelSerialNumber(String manufactureCode);

    @Async
    CompletableFuture<Device> getAsyncById(long id);
}
