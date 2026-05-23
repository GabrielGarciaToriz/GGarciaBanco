package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.auth.LoginRequest;
import com.digis.GGarciaBanco.dto.auth.LoginResponse;
import com.digis.GGarciaBanco.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Result<?>> login(@RequestBody LoginRequest request) {
        Result<LoginResponse> result = authService.login(request);
        return responder(result);
    }

}
