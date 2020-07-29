package com.springboot.security.login.config;

import com.springboot.security.login.exception.ApiBadRequestException;
import com.springboot.security.login.model.Users;
import com.springboot.security.login.repository.UsersRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

public class UsersAuthenticationProvider implements AuthenticationProvider {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    public UsersAuthenticationProvider(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Users> userOpt = usersRepository.findByUsername(username);
        if(!userOpt.isPresent()) {
            throw new ApiBadRequestException("Username or password incorrect");
        }
        Users user = userOpt.get();

        String dbPassword = user.getPassword();
        if (!passwordEncoder.matches(password, dbPassword)) {
            throw new ApiBadRequestException("Username or password incorrect");
        }

        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
