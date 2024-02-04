package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.DeviceModel;
import com.antonigari.iotdeviceservice.model.NewDeviceModelRequestDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
@Component
public class NewDeviceModelRequestDtoConverterDeviceModel implements Converter<NewDeviceModelRequestDto, DeviceModel> {
    @Override
    @NonNull
    public DeviceModel convert(NewDeviceModelRequestDto newDeviceModelRequestDto) {
        return  DeviceModel.builder()
                .serialNumber(newDeviceModelRequestDto.getSerialNumber())
                .name(newDeviceModelRequestDto.getName())
                .build();
    }
}