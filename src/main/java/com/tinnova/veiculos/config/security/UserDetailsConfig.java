package com.tinnova.veiculos.config.security;

import org.springframework.context.annotation.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = new AuthUser(
                "admin",
                passwordEncoder().encode("123456"),
                List.of(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name()))
        );

        UserDetails user = new AuthUser(
                "user",
                passwordEncoder().encode("user123"),
                List.of(new SimpleGrantedAuthority(UserRole.ROLE_USER.name()))
        );

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
