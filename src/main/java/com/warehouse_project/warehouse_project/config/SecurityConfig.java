package com.warehouse_project.warehouse_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final FirebaseAuthorizationFilter firebaseAuthorizationFilter;

    public SecurityConfig(FirebaseAuthorizationFilter firebaseAuthorizationFilter) {
        this.firebaseAuthorizationFilter = firebaseAuthorizationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/getUserInfo").hasAnyRole( "USER")  // Solo usuarios con rol ADMIN
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Usuarios con rol USER o ADMIN
                        .anyRequest().authenticated()
                )
                .addFilterBefore(firebaseAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
