package com.antonigari.iotdeviceservice.service.converter;


import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceConverter implements Converter<Device, DeviceDto> {
    @Override
    @NonNull
    public DeviceDto convert(Device source) {
        return null;
    }
}
