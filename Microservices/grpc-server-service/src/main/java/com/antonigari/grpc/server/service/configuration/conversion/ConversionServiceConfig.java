package com.antonigari.grpc.server.service.configuration.conversion;

import com.antonigari.grpc.server.service.service.converter.DeviceConverterDeviceDto;
import com.antonigari.grpc.server.service.service.converter.DeviceMeasurementConverterDeviceMeasurementDto;
import com.antonigari.grpc.server.service.service.converter.DeviceMeasurementDtoConverterDeviceMeasurementGrpc;
import com.antonigari.grpc.server.service.service.converter.DeviceModelConverterDeviceModelDto;
import com.antonigari.grpc.server.service.service.converter.MeasurementTypeConverterMeasurementTypeDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;

@Configuration
public class ConversionServiceConfig {

    @Bean
    public DefaultFormattingConversionService conversionService() {
        final DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addConverter(new DeviceModelConverterDeviceModelDto());
        conversionService.addConverter(new DeviceMeasurementDtoConverterDeviceMeasurementGrpc());
        conversionService.addConverter(new DeviceMeasurementConverterDeviceMeasurementDto(conversionService));
        conversionService.addConverter(new MeasurementTypeConverterMeasurementTypeDto());
        conversionService.addConverter(new DeviceConverterDeviceDto(conversionService));
        return conversionService;
    }
}
