package com.jack.BloggingApp.security;

import com.jack.BloggingApp.users.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final JWTService jwtService;
    private final UsersService usersService;

    public AppSecurityConfig(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JWTAuthenticationFilter jwtAuthenticationFilter =
                new JWTAuthenticationFilter(new JWTAuthenticationManager(jwtService, usersService));

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // uses default CorsConfigurationSource bean, if any
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/users", "/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/articles", "/articles/*").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);

        return http.build();
    }


    // Optional: define this if needed elsewhere
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
