package com.kanban.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kanban.app.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(
                        AuthenticationProvider authenticationProvider,
                        JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.authenticationProvider = authenticationProvider;
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(auth -> auth
                                                // Public routes
                                                .requestMatchers("/api/v1/auth/**").permitAll()
                                                .requestMatchers("/swagger-ui/**").permitAll()
                                                .requestMatchers("/v3/api-docs/**").permitAll()
                                                // Authenticated routes
                                                .requestMatchers("/api/v1/users/**").authenticated()
                                                .requestMatchers("/api/v1/projects/**").authenticated()
                                                .requestMatchers("/api/v1/boards/**").authenticated()
                                                .requestMatchers("/api/v1/labels/**").authenticated()
                                                .requestMatchers("/api/v1/buckets/**").authenticated()
                                                .requestMatchers("/api/v1/bucket-labels/**").authenticated()
                                                .requestMatchers("/api/v1/tasks/**").authenticated()
                                                .requestMatchers("/api/v1/subtasks/**").authenticated()
                                                .requestMatchers("/api/v1/project-users/**").authenticated()
                                                .requestMatchers("/api/v1/user-tasks/**").authenticated()
                                                // Another requests require auth by default
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }
}