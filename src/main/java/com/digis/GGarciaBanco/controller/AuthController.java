package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.auth.AuthResponse;
import com.digis.GGarciaBanco.dto.login.LoginRequest;
import com.digis.GGarciaBanco.dto.login.LoginResponse;
import com.digis.GGarciaBanco.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
