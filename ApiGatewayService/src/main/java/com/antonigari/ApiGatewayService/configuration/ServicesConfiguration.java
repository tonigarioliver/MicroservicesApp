package com.antonigari.ApiGatewayService.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ServicesConfiguration {
    @Value("${services.userService.name}")
    private String USER_SERVICE_NAME;
}