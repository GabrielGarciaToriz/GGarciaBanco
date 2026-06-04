package com.digis.ggarciabanco.controller;

import com.digis.ggarciabanco.dto.Result;
import com.digis.ggarciabanco.dto.login.LoginRequest;
import com.digis.ggarciabanco.service.AuthService;
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
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Result<?>> login(@RequestBody LoginRequest request) {
        return responder(authService.login(request));
    }
}