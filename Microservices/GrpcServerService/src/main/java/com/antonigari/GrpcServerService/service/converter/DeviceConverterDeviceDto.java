package com.antonigari.GrpcServerService.service.converter;


import com.antonigari.GrpcServerService.data.model.Device;
import com.antonigari.GrpcServerService.model.DeviceDto;
import com.antonigari.GrpcServerService.model.DeviceModelDto;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceConverterDeviceDto implements Converter<Device, DeviceDto> {
    private final ConversionService conversionService;

    public DeviceConverterDeviceDto(@Lazy final ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    @NonNull
    public DeviceDto convert(final Device device) {
        return DeviceDto.builder()
                .deviceId(device.getDeviceId())
                .price(device.getPrice())
                .manufactureDate(device.getManufactureDate())
                .deviceModel(this.conversionService.convert(device.getDeviceModel(), DeviceModelDto.class))
                .manufactureCode(device.getManufactureCode())
                .build();
    }
}
