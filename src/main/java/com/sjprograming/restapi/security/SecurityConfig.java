package com.sjprograming.restapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
            // 🔐 Disable CSRF for REST APIs
            .csrf(csrf -> csrf.disable())

            // 🔐 Allow iframe (Railway / H2 / Render)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            .authorizeHttpRequests(auth -> auth

                /* =======================
                   STATIC RESOURCES
                ======================= */
                .requestMatchers(
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/uploads/**",
                        "/favicon.ico"
                ).permitAll()

                /* =======================
                   PUBLIC HTML PAGES
                ======================= */
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
                        "/alumni-list.html",
                        "/alumni-batches.html",
                        "/admin-gallery.html",
                        "/check-status.html",
                        "/admin-news.html",
                        "/admin-news-list.html",
                        "/news-view.html"
                ).permitAll()

                /* =======================
                   AUTH APIs
                ======================= */
                .requestMatchers("/api/auth/**").permitAll()

                /* =======================
                   🔥 PUBLIC NEWS APIs (FIXED)
                ======================= */
                .requestMatchers(HttpMethod.GET, "/api/news/public").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/news/public/**").permitAll()

                /* =======================
                   PUBLIC APIs
                ======================= */
                .requestMatchers(HttpMethod.POST, "/api/admission/apply").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/admission/status").permitAll()
                .requestMatchers("/api/gallery/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/alumni").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/alumni/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/batches").permitAll()

                /* =======================
                   ADMIN APIs (JWT REQUIRED)
                ======================= */
                .requestMatchers("/api/news/admin/**").authenticated()
                .requestMatchers("/api/admission/admin/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/batches/create").authenticated()

                /* =======================
                   ALL OTHER APIs
                ======================= */
                .requestMatchers("/api/**").authenticated()

                /* =======================
                   FALLBACK
                ======================= */
                .anyRequest().authenticated()
            )

            // 🔑 JWT Filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /* =======================
       IGNORE STATIC FILES COMPLETELY
    ======================= */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/css/**",
                "/js/**",
                "/images/**",
                "/uploads/**",
                "/favicon.ico"
        );
    }

    /* =======================
       PASSWORD ENCODER
    ======================= */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
