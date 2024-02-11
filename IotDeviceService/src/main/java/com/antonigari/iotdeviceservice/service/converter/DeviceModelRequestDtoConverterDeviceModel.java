package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.DeviceModel;
import com.antonigari.iotdeviceservice.model.DeviceModelRequestDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Component
public class DeviceModelRequestDtoConverterDeviceModel implements Converter<DeviceModelRequestDto, DeviceModel> {
    @Override
    @NonNull
    public DeviceModel convert(DeviceModelRequestDto deviceModelRequestDto) {
        return  DeviceModel.builder()
                .serialNumber(deviceModelRequestDto.getSerialNumber())
                .name(deviceModelRequestDto.getName())
                .build();
    }
}