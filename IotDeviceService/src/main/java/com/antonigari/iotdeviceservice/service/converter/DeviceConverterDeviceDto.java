package com.antonigari.iotdeviceservice.service.converter;


import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceModelDto;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceConverterDeviceDto implements Converter<Device, DeviceDto> {
    private final ConversionService conversionService;

    DeviceConverterDeviceDto(@Lazy final ConversionService conversionService){
        this.conversionService=conversionService;
    }
    @Override
    @NonNull
    public DeviceDto convert(Device device) {
        return  DeviceDto.builder()
                .deviceId(device.getDeviceId())
                .price(device.getPrice())
                .manufactureDate(device.getManufactureDate())
                .deviceModel(this.conversionService.convert(device.getDeviceModel(), DeviceModelDto.class))
                .manufactureCode(device.getManufactureCode())
                .build();
    }
}
