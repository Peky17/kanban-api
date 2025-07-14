package com.kanban.app.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kanban.app.models.dto.LoginDTO;
import com.kanban.app.models.entities.AdministratorEntity;
import com.kanban.app.repositories.AdministratorRepository;

@Service
public class AuthService {
    @Autowired
    private AdministratorRepository administratorRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AdministratorRepository administratorRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdministratorEntity authenticate(LoginDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return administratorRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public AdministratorEntity signup(AdministratorEntity administrator) {
        String plainPassword = administrator.getPassword();
        String encodedPassword = passwordEncoder.encode(plainPassword);
        administrator.setPassword(encodedPassword);
        return administratorRepository.save(administrator);
    }
}
