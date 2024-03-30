package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.DeviceModel;
import com.antonigari.iotdeviceservice.model.DeviceModelDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceModelConverterDeviceModelDto implements Converter<DeviceModel, DeviceModelDto> {
    @Override
    @NonNull
    public DeviceModelDto convert(final DeviceModel deviceModel) {
        return DeviceModelDto.builder()
                .deviceModelId(deviceModel.getDeviceModelId())
                .name(deviceModel.getName())
                .serialNumber(deviceModel.getSerialNumber())
                .build();
    }
}
