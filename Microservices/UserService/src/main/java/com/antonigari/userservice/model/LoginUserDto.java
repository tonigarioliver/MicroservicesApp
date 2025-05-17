package com.antonigari.userservice.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record LoginUserDto(
        @NotEmpty String userName,
        @NotEmpty String password
) {
}
