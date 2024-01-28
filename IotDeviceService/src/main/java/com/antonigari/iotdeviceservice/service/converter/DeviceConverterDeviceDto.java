package com.antonigari.iotdeviceservice.service.converter;


import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceConverterDeviceDto implements Converter<Device, DeviceDto> {
    @Override
    @NonNull
    public DeviceDto convert(Device device) {
        return  DeviceDto.builder()
                        .manufactureCode(device.getManufactureCode())
                        .build();
    }
}
