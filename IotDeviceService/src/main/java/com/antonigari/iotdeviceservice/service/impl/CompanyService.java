package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.data.model.Company;
import com.antonigari.iotdeviceservice.data.repository.CompanyRepository;
import com.antonigari.iotdeviceservice.model.CompaniesDto;
import com.antonigari.iotdeviceservice.model.CompanyDto;
import com.antonigari.iotdeviceservice.model.NewCompanyRequestDto;
import com.antonigari.iotdeviceservice.model.UpdateCompanyRequestDto;
import com.antonigari.iotdeviceservice.service.ICompanyService;
import com.antonigari.iotdeviceservice.service.exception.ServiceErrorCatalog;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@AllArgsConstructor
public class CompanyService implements ICompanyService {
    private final CompanyRepository companyRepository;
    private final ConversionService conversionService;

    @Async
    @Override
    public CompletableFuture<CompaniesDto> getAllAsync() {
        return CompletableFuture.completedFuture(CompaniesDto.builder()
                .companies(companyRepository.findAll().stream()
                        .map(company -> conversionService.convert(company, CompanyDto.class))
                        .toList())
                .build());
    }

    @Override
    public CompanyDto create(NewCompanyRequestDto newCompanyRequestDto) {
        Company newCompany = conversionService.convert(newCompanyRequestDto, Company.class);
        companyRepository.save(newCompany);
        return conversionService.convert(newCompany, CompanyDto.class);
    }

    @Override
    public CompanyDto update(Long id, UpdateCompanyRequestDto updateCompanyRequestDto) {
        Company existingCompany = companyRepository.findByName(updateCompanyRequestDto.getName())
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("Company with ID " + updateCompanyRequestDto.getName() +" not found"));

        // Update fields as needed
        existingCompany.setName(updateCompanyRequestDto.getName());
        existingCompany.setAddress(updateCompanyRequestDto.getAddress());
        existingCompany.setPhoneNumber(updateCompanyRequestDto.getPhoneNumber());

        companyRepository.save(existingCompany);
        return conversionService.convert(existingCompany, CompanyDto.class);
    }

    @Override
    public void delete(Long companyId) {
        Company companyToDelete = companyRepository.findById(companyId)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("Company with ID " + companyId + " not found"));

        companyRepository.delete(companyToDelete);
    }

    @Override
    public CompletableFuture<CompanyDto> findByName(String name) {
        return CompletableFuture.supplyAsync(() ->
                companyRepository.findByName(name)
                        .map(company -> conversionService.convert(company, CompanyDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("Company with name " + name + " not found"))
        );
    }
}
