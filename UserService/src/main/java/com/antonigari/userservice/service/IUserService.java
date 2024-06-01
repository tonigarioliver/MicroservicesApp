package com.antonigari.userservice.service;

import com.antonigari.userservice.model.LoginResponse;
import com.antonigari.userservice.model.LoginUserDto;
import com.antonigari.userservice.model.RegisterUserDto;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    LoginResponse registerUser(final @Valid RegisterUserDto registerUserDto);

    LoginResponse loginUser(final @Valid LoginUserDto loginUserDto);

    boolean isUserTokenValid(final String jwtToken, final String userName);

    String getUserNameFromToken(final String jwtToken);
}
