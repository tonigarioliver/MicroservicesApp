package com.antonigari.userservice.service;

import com.antonigari.userservice.model.LoginResponse;
import com.antonigari.userservice.model.LoginUserDto;
import com.antonigari.userservice.model.RegisterUserDto;
import com.antonigari.userservice.service.impl.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    JwtService getJwtService();

    LoginResponse registerUser(@Valid RegisterUserDto registerUserDto);

    LoginResponse loginUser(@Valid LoginUserDto loginUserDto);
}
