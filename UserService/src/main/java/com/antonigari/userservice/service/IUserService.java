package com.antonigari.userservice.service;

import com.antonigari.userservice.model.RegisterUserDto;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    String registerUser(@Valid RegisterUserDto registerUserDto);
}
