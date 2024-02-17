package com.antonigari.iotdeviceservice.controller;

import com.antonigari.iotdeviceservice.model.CompaniesDto;
import com.antonigari.iotdeviceservice.model.CompanyDto;
import com.antonigari.iotdeviceservice.model.NewCompanyRequestDto;
import com.antonigari.iotdeviceservice.model.UpdateCompanyRequestDto;
import com.antonigari.iotdeviceservice.service.impl.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/company")
@Tag(name = "Company Controller", description = "Operations related to companies")
@AllArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    @PostMapping
    @Operation(summary = "Create Company", description = "Create a new Device.")
    public ResponseEntity<CompanyDto> createCompany(
            @RequestBody @NotNull final NewCompanyRequestDto newCompanyRequestDto
    ) {
        return new ResponseEntity<>(this.companyService.create(newCompanyRequestDto), HttpStatus.OK);
    }
    @GetMapping
    @Operation(summary = "Read companies", description = "Read list of companies.")
    public ResponseEntity<CompaniesDto> getCompanies() {
        return new ResponseEntity<>(this.companyService.getAllAsync().join(), HttpStatus.OK);
    }
    @GetMapping(value = "/{companyName}")
    @Operation(summary = "Get Company", description = "Get company by Serial Number.")
    public ResponseEntity<CompanyDto> getCompanyByName(@PathVariable @NotEmpty final String companyName) {
        return new ResponseEntity<>(this.companyService.findByName(companyName).join(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Company", description = "Update existing Company.")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable @Positive final long id,
                                                    @RequestBody @NotNull final UpdateCompanyRequestDto updateCompanyRequestDto
    ) {
        return new ResponseEntity<>(this.companyService.update(id,updateCompanyRequestDto),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Company", description = "Delete Company by id.")
    public ResponseEntity<Void> deleteComapny(@PathVariable @Positive final long id) {
        this.companyService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
