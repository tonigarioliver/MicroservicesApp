package com.antonigari.iotdeviceservice.service;

import java.util.concurrent.CompletableFuture;

public interface ICrudService<Dto, N,U,LDto> {
    CompletableFuture<LDto> getAllAsync();
    Dto create(N Ndto);
    Dto update(Long id, U Udto);
    void delete(Long id);
}
