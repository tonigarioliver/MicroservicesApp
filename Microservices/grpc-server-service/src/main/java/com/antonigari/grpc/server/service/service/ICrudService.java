package com.antonigari.grpc.server.service.service;

import java.util.concurrent.CompletableFuture;

public interface ICrudService<Dto, N, U, LDto> {
    CompletableFuture<LDto> getAllAsync();
}
