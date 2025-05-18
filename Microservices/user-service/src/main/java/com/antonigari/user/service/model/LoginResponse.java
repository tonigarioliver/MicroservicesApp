package com.antonigari.user.service.model;

import lombok.Builder;

@Builder
public record LoginResponse(String jwtToken, Long expiresIn) {
}
