package com.sjprograming.restapi.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // ✅ 256-bit secure key (DO NOT make it shorter)
    private static final String SECRET =
            "THIS_IS_A_VERY_LONG_AND_SECURE_256_BIT_SECRET_KEY_123456";

    // 10 hours
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    // ✅ REQUIRED METHOD (THIS FIXES YOUR ERROR)
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ✅ Generate token with ROLE
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Parse token
    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }

    public String extractRole(String token) {
        return parseToken(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        return parseToken(token)
                .getExpiration()
                .after(new Date());
    }
}
