package com.antonigari.userservice.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record LoginUserDto(
        @NotEmpty String userName,
        @NotEmpty String email,
        @NotEmpty String password,
        @NotEmpty String name,
        @NotEmpty String lastName
) {
}
