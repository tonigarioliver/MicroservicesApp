package com.antonigari.iotdeviceservice.service;

import java.util.concurrent.CompletableFuture;

public interface ICrudService<Dto, N,U,LDto> {
    CompletableFuture<LDto> getAllAsync();
    Dto create(final N Ndto);
    Dto update(final long id, final U Udto);
    void delete(final long id);
}
