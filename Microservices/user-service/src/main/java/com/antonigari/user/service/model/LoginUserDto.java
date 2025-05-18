package com.antonigari.user.service.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record LoginUserDto(
        @NotEmpty String userName,
        @NotEmpty String password
) {
}
