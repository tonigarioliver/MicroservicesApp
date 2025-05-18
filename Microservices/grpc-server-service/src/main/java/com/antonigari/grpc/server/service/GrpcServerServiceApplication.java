package com.antonigari.grpc.server.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcServerServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(GrpcServerServiceApplication.class, args);
    }

}
