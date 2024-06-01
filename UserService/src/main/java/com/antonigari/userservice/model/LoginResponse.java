package com.antonigari.userservice.model;

import lombok.Builder;

@Builder
public record LoginResponse(String token, Long expiresIn) {
}
