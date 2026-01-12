package com.sjprograming.restapi.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sjprograming.restapi.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> body) {

        Map<String, String> response = authService.login(
                body.get("username"),
                body.get("password")
        );

        return ResponseEntity.ok(response);
    }
}
