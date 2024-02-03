package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.*;
import com.antonigari.iotdeviceservice.model.*;
import lombok.*;
import org.springframework.core.convert.converter.*;
import org.springframework.stereotype.*;


@Component
public class NewDeviceRequestDtoConverterDevice implements Converter<NewDeviceRequestDto, Device> {
    @Override
    @NonNull
    public Device convert(NewDeviceRequestDto newDeviceRequestDto) {
        return  Device.builder()
                .manufactureCode(newDeviceRequestDto.getManufactureCode())
                .build();
    }
}