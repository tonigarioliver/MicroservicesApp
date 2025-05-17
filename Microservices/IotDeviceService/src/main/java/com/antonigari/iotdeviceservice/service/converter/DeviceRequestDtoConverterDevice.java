package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.Device;
import com.antonigari.iotdeviceservice.model.DeviceRequestDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class DeviceRequestDtoConverterDevice implements Converter<DeviceRequestDto, Device> {
    @Override
    @NonNull
    public Device convert(DeviceRequestDto deviceRequestDto) {
        return  Device.builder()
                .manufactureCode(deviceRequestDto.getManufactureCode())
                .price(deviceRequestDto.getPrice())
                .manufactureDate(deviceRequestDto.getManufactureDate())
                .build();
    }
}