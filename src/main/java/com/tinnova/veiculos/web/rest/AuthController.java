package com.tinnova.veiculos.web.rest;

import com.tinnova.veiculos.config.security.JwtTokenProvider;
import com.tinnova.veiculos.web.dto.LoginRequest;
import com.tinnova.veiculos.web.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authManager,
                          JwtTokenProvider tokenProvider) {
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()
                )
        );

        String token = tokenProvider.generateToken(auth);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
