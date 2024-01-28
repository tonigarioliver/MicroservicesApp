package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.model.*;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface ICompanyService extends ICrudService<CompanyDto, NewCompanyRequestDto, UpdateCompanyRequestDto, CompaniesDto> {
    @Async
    CompletableFuture<CompanyDto> findByName(String name);
}