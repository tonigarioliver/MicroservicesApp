package com.antonigari.userservice.controller;

import com.antonigari.userservice.model.RegisterUserDto;
import com.antonigari.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
@Tag(name = "User Controller", description = "Operations related to users")
@AllArgsConstructor
public class UserController {
    private final IUserService service;

    @PostMapping
    @Operation(summary = "Register User", description = "Register a new User.")
    public ResponseEntity<String> registerUser(
            @RequestBody @NotNull final RegisterUserDto registerUserDto
    ) {
        return new ResponseEntity<>(this.service.registerUser(registerUserDto), HttpStatus.CREATED);
    }
}
