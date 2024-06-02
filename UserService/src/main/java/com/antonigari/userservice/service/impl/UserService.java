package com.antonigari.userservice.service.impl;

import com.antonigari.userservice.data.model.AppUser;
import com.antonigari.userservice.data.repository.UserRepository;
import com.antonigari.userservice.model.LoginResponse;
import com.antonigari.userservice.model.LoginUserDto;
import com.antonigari.userservice.model.RegisterUserDto;
import com.antonigari.userservice.service.IUserService;
import com.antonigari.userservice.service.exception.ServiceErrorCatalog;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        final AppUser user = this.repository.findByUsername(userName)
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("User not exist"));
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    @Override
    public LoginResponse registerUser(final RegisterUserDto registerUserDto) {
        final AppUser newUser = this.repository.save(AppUser.builder()
                .username(registerUserDto.userName())
                .name(registerUserDto.name())
                .email(registerUserDto.email())
                .lastName(registerUserDto.lastName())
                .password(this.passwordEncoder.encode(registerUserDto.password()))
                .createdAt(Date.from(Instant.now()))
                .updatedAt(Date.from(Instant.now()))
                .build());

        return LoginResponse.builder()
                .jwtToken(this.jwtService.generateToken(newUser))
                .expiresIn(this.jwtService.getExpirationTime())
                .build();
    }

    @Override
    public LoginResponse loginUser(final LoginUserDto loginUserDto) {
        final AppUser user = this.repository.findByUsername(loginUserDto.userName())
                .orElseThrow(() -> ServiceErrorCatalog.NOT_FOUND.exception("User not exist"));
        return LoginResponse.builder()
                .jwtToken(this.jwtService.generateToken(user))
                .expiresIn(this.jwtService.getExpirationTime())
                .build();
    }

    @Override
    public boolean isUserTokenValid(final String jwtToken, final String userName) {
        return this.jwtService.isTokenValid(jwtToken, this.loadUserByUsername(userName));
    }

    @Override
    public String getUserNameFromToken(final String jwtToken) {
        return this.jwtService.extractUsername(jwtToken);
    }
}
