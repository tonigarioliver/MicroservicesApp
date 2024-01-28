package com.antonigari.iotdeviceservice.data.repository;

import com.antonigari.iotdeviceservice.data.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByName(String name);
}
