package com.kanban.app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanban.app.models.dto.LoginDTO;
import com.kanban.app.models.dto.TokenDTO;
import com.kanban.app.models.entities.AdministratorEntity;
import com.kanban.app.services.auth.AuthService;
import com.kanban.app.services.auth.JwtService;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private final JwtService jwtService;

    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginDTO loginDTO) {
        AdministratorEntity authenticatedUser = authService.authenticate(loginDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        TokenDTO loginResponse = new TokenDTO();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AdministratorEntity> register(@RequestBody AdministratorEntity administratorEntity) {
        AdministratorEntity registeredUser = authService.signup(administratorEntity);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/me")
    public ResponseEntity<AdministratorEntity> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AdministratorEntity currentUser = (AdministratorEntity) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }
}