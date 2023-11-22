package com.userproductmanagement.letsplay.auth;

import com.mongodb.DuplicateKeyException;
import com.userproductmanagement.letsplay.config.JwtService;
import com.userproductmanagement.letsplay.exception.DuplicateException;
import com.userproductmanagement.letsplay.model.Role;
import com.userproductmanagement.letsplay.model.User;
import com.userproductmanagement.letsplay.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(@Valid RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        try {
            repository.save(user);
        } catch (DuplicateKeyException ex) {
            if (ex.getMessage().contains("name")) {
                throw new DuplicateException("The name is already in use");
            } else if (ex.getMessage().contains("email")) {
                throw new DuplicateException("The email is already in use");
            }
            // Handle other duplicates or just throw a generic message
            throw new DuplicateException("Data conflict occurred");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
