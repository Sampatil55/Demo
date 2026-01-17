package com.hotel.config;

import com.hotel.service.JWTService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {
private JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean   //
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http

    ) throws Exception {
        http.csrf(csrf->csrf.disable());
        http.cors(cors->cors.disable());
        http.authorizeHttpRequests(auth -> auth

                                .requestMatchers("/api/auth/signup", "/api/tasks/create","/api/auth/login","/api/auth/admin","/api/auth/manager","/api/ai/ask").permitAll()

                                // Manager & Admin can view all tasks
                                .requestMatchers("/api/tasks/all").hasAnyRole("MANAGER", "ADMIN")

                                // Admin can delete any task
                                .requestMatchers("/api/tasks/delete/**","/api/auth/assign-role/**","/api/auth/updateById/{id}",
                                        "/api/auth/delete/**").hasRole("ADMIN")

                                .requestMatchers("/api/tasks/update/**","/api/tasks/create").hasAnyRole("MANAGER", "ADMIN", "USER")

                                .requestMatchers("/api/tasks/update/**","/api/auth/getAllUser","/api/tasks/has-task/**").hasRole("ADMIN")



// <-- Important: match any taskId
                .anyRequest()
                .authenticated()
        )
        .addFilterBefore(jwtFilter, AuthorizationFilter.class);



 return http.build();
    }

}
