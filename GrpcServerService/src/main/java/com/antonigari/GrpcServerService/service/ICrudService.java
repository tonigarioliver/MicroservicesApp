package com.antonigari.GrpcServerService.service;

import java.util.concurrent.CompletableFuture;

public interface ICrudService<Dto, N, U, LDto> {
    CompletableFuture<LDto> getAllAsync();
}
