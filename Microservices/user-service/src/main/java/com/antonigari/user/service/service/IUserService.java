package com.antonigari.user.service.service;

import com.antonigari.user.service.model.LoginResponse;
import com.antonigari.user.service.model.LoginUserDto;
import com.antonigari.user.service.model.RegisterUserDto;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    LoginResponse registerUser(final @Valid RegisterUserDto registerUserDto);

    LoginResponse loginUser(final @Valid LoginUserDto loginUserDto);

    boolean isUserTokenValid(final String jwtToken, final String userName);

    String getUserNameFromToken(final String jwtToken);
}
