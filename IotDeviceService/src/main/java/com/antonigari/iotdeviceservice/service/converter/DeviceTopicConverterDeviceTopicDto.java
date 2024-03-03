package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.DeviceTopic;
import com.antonigari.iotdeviceservice.model.DeviceTopicDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceTopicConverterDeviceTopicDto implements Converter<DeviceTopic, DeviceTopicDto> {
    @Override
    @NonNull
    public DeviceTopicDto convert(final DeviceTopic deviceTopic) {
        return DeviceTopicDto.builder()
                .id(deviceTopic.getId())
                .manufactureCode(deviceTopic.getDeviceManufactureCode())
                .topic(deviceTopic.getTopic())
                .build();
    }
}