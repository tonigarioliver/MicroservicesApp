package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.KafkaMessage;
import com.antonigari.iotdeviceservice.data.repository.DeviceModelRepository;
import com.antonigari.iotdeviceservice.data.repository.DeviceRepository;
import com.antonigari.iotdeviceservice.model.DeviceDto;
import com.antonigari.iotdeviceservice.model.DeviceRequestDto;
import com.antonigari.iotdeviceservice.model.DeviceTopicDto;
import com.antonigari.iotdeviceservice.model.DevicesDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
@Slf4j
public class DeviceManagerService {
    private final DeviceRepository deviceRepository;
    private final DeviceModelRepository deviceModelRepository;
    private final ConversionService conversionService;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;
    private final DeviceTopicService deviceTopicService;
    private final DeviceService deviceService;

    @Async
    public CompletableFuture<DevicesDto> getAllAsync() {
        return this.deviceService.getAllAsync();
    }

    @Async
    public CompletableFuture<DeviceDto> getAsyncByManufactureCode(final String manufactureCode) {
        return this.deviceService.getAsyncByManufactureCode(manufactureCode);
    }

    public DeviceDto create(final DeviceRequestDto deviceRequestDto) {
        final DeviceDto deviceCreated = this.deviceService.create(deviceRequestDto);
        final DeviceTopicDto deviceTopicCreated = this.deviceTopicService.create(deviceCreated);
        this.sendMessageToKafka("new-device", deviceTopicCreated);
        return deviceCreated;
    }

    public DeviceDto update(final long id, final DeviceRequestDto deviceRequestDto) {
        final DeviceDto deviceUpdated = this.deviceService.update(id, deviceRequestDto);
        final DeviceTopicDto existingDeviceTopic = this.deviceTopicService.getAsyncByManufactureCode(deviceUpdated.getManufactureCode()).join();
        final DeviceTopicDto deviceTopicUpdated = this.deviceTopicService.update(existingDeviceTopic.getId(), deviceUpdated);
        this.sendMessageToKafka("updated-device", deviceTopicUpdated);
        return deviceUpdated;
    }

    public void delete(final long id) {
        final DeviceDto deviceToDelete = this.deviceService.getAsyncById(id).join();
        final DeviceTopicDto deviceTopic = this.deviceTopicService.getAsyncByManufactureCode(deviceToDelete.getManufactureCode()).join();
        this.deviceService.delete(id);
        this.sendMessageToKafka("delete-device", deviceTopic);
    }

    private boolean sendMessageToKafka(final String topic, final DeviceTopicDto deviceTopic) {
        try {
            final String payload = this.objectMapper.writeValueAsString(deviceTopic);
            return this.kafkaProducerService.sendMessage(KafkaMessage.builder()
                    .topic(topic)
                    .message(payload)
                    .build());
        } catch (final JsonProcessingException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
