package com.wellsfargo.security.config;

import com.wellsfargo.security.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private  final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService()
    {

        return username ->userRepository.findByemail_id(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not Found"));

    }
}
