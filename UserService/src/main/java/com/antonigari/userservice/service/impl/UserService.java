package com.antonigari.userservice.service.impl;

import com.antonigari.userservice.data.model.AppUser;
import com.antonigari.userservice.data.repository.UserRepository;
import com.antonigari.userservice.model.RegisterUserDto;
import com.antonigari.userservice.service.IUserService;
import com.antonigari.userservice.service.exception.ServiceErrorCatalog;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

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
    public String registerUser(final RegisterUserDto registerUserDto) {
        return "HELLO";
    }
}
