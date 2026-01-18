package com.sjprograming.restapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

                // ===== STATIC FILES =====
                .requestMatchers("/uploads/**").permitAll()

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
                    "/admin-gallery.html",
                    "/check-status.html",
                    "/admin-news.html",
                    "/news-view.html",
                    "/admin-news-list.html"
                ).permitAll()

                // ===== AUTH =====
                .requestMatchers("/api/auth/**").permitAll()

                // ===== PUBLIC ADMISSION APIs =====
                .requestMatchers(HttpMethod.POST, "/api/admission/apply").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/admission/status").permitAll()

                // ===== PUBLIC GALLERY =====
                .requestMatchers("/api/gallery/**").permitAll()

                // ===== ADMIN ADMISSION APIs (JWT REQUIRED) =====
                .requestMatchers("/api/admission/admin/**").authenticated()

                // ===== OTHER PUBLIC APIs =====
                .requestMatchers(HttpMethod.POST, "/api/alumni").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/alumni/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/batches").permitAll()
             // PUBLIC ADMISSION APIs
                .requestMatchers(HttpMethod.POST, "/api/admission/apply").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/admission/status").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/news/public").permitAll()
                .requestMatchers("/api/news/public/**").permitAll()
                .requestMatchers("/api/news/admin/**").authenticated()

                // ===== ADMIN =====
                .requestMatchers(HttpMethod.POST, "/api/batches/create").authenticated()
                .requestMatchers("/", "/health").permitAll()
                // ===== ALL OTHER APIs =====
                .requestMatchers("/api/**").authenticated()

                .anyRequest().denyAll()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 🔥 STATIC RESOURCE BYPASS (IMAGES FIX)
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
