package com.antonigari.iotdeviceservice.service.converter;

import com.antonigari.iotdeviceservice.data.model.Company;
import com.antonigari.iotdeviceservice.model.CompanyDto;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyConverterCompanyDto implements Converter<Company, CompanyDto> {
    @Override
    @NonNull
    public CompanyDto convert(Company company) {
        return CompanyDto.builder()
                .address(company.getAddress())
                .name(company.getName())
                .phoneNumber(company.getPhoneNumber())
                .build();
    }
}
