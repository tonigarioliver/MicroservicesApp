package com.antonigari.api.gateway.service.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ServicesConfiguration {
    @Value("${services.userService.name}")
    private String userServiceName;
}
