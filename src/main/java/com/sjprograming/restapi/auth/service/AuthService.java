package com.sjprograming.restapi.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sjprograming.restapi.auth.model.Admin;
import com.sjprograming.restapi.auth.repository.AdminRepository;
import com.sjprograming.restapi.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ ADD THIS

    public Map<String, String> login(String username, String password) {

        Admin admin = adminRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        // ✅ CORRECT PASSWORD CHECK
        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // ✅ JWT with ROLE
        String token = jwtUtil.generateToken(
                admin.getUsername(),
                admin.getRole()
        );

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", admin.getRole());

        return response;
    }
}
