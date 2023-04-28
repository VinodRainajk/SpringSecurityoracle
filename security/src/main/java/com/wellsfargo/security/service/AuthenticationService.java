package com.wellsfargo.security.service;

import com.wellsfargo.security.Entity.Role;
import com.wellsfargo.security.Entity.user_custom;
import com.wellsfargo.security.Repository.UserRepository;
import com.wellsfargo.security.controller.AuthRequest;
import com.wellsfargo.security.controller.AuthenticationResponse;
import com.wellsfargo.security.controller.RegisterRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEnoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())

        );

        var user =userRepository.findByemail(request.getEmail())
                .orElseThrow();

        var jwttoken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwttoken).build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = user_custom.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEnoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwttoken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwttoken).build();

    }
}
