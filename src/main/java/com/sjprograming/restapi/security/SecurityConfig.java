package com.sjprograming.restapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .authorizeHttpRequests(auth -> auth

                // ===== PUBLIC HTML =====
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/admin.html",
                    "/admin-layout.html",
                    "/dashboard.html",
                    "/employees.html",
                    "/gallery.html",
                    "/admission.html",
                    "/admin-admissions.html",
                    "/alumni.html",
                    "/admin-alumni.html",
                    "/alumni-showcase.html",
                    "/admin-batches.html",
                    "/favicon.ico",
                    "/alumni-list.html",
                    "/alumni-batches.html",
                    "/admin-gallery.html"
                ).permitAll()

                // ===== APIs =====
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/gallery/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/admissions").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/admissions").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/alumni").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/alumni/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/batches").permitAll()

                // ===== ADMIN =====
                .requestMatchers(HttpMethod.POST, "/api/batches/create").authenticated()

                // ===== ALL OTHER APIs =====
                .requestMatchers("/api/**").authenticated()

                .anyRequest().denyAll()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🔥 THIS IS THE FIX FOR IMAGE 403
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/uploads/**",
                "/css/**",
                "/js/**",
                "/images/**",
                "/favicon.ico"
        );
    }
}
