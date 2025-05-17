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
                .companies(this.companyRepository.findAll().stream()
                        .map(company -> this.conversionService.convert(company, CompanyDto.class))
                        .toList())
                .build());
    }

    @Override
    public CompanyDto create(final NewCompanyRequestDto newCompanyRequestDto) {
        final Company newCompany = this.conversionService.convert(newCompanyRequestDto, Company.class);
        return this.conversionService.convert(this.companyRepository.save(newCompany), CompanyDto.class);
    }

    @Override
    public CompanyDto update(long id, final UpdateCompanyRequestDto updateCompanyRequestDto) {
        final Company existingCompany = this.companyRepository.findByName(updateCompanyRequestDto.getName())
                    .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("Company with ID " + updateCompanyRequestDto.getName() +" not found"));
        existingCompany.setName(updateCompanyRequestDto.getName());
        existingCompany.setAddress(updateCompanyRequestDto.getAddress());
        existingCompany.setPhoneNumber(updateCompanyRequestDto.getPhoneNumber());
        return conversionService.convert(this.companyRepository.save(existingCompany), CompanyDto.class);
    }

    @Override
    public void delete(final long companyId) {
        Company companyToDelete = this.companyRepository.findById(companyId)
                .orElseThrow(() -> ServiceErrorCatalog
                        .NOT_FOUND.exception("Company with ID " + companyId + " not found"));

        this.companyRepository.delete(companyToDelete);
    }

    @Override
    public CompletableFuture<CompanyDto> findByName(String name) {
        return CompletableFuture.supplyAsync(() ->
                this.companyRepository.findByName(name)
                        .map(company -> this.conversionService.convert(company, CompanyDto.class))
                        .orElseThrow(() -> ServiceErrorCatalog
                                .NOT_FOUND.exception("Company with name " + name + " not found"))
        );
    }
}
