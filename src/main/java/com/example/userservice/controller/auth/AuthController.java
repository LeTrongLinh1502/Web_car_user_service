package com.example.userservice.controller.auth;

import com.example.userservice.base.BaseResponse;
import com.example.userservice.base.ResponseBuilder;
import com.example.userservice.dtos.request.UserAuthRequest;
import com.example.userservice.dtos.response.AuthenticationResponse;
import com.example.userservice.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/v1/auth/token")
    public ResponseEntity<BaseResponse<?>> authToken(
            @RequestBody UserAuthRequest authRequest
            ) {
        AuthenticationResponse authenticationResponse = authService.getToken(authRequest);
        return ResponseEntity.ok(ResponseBuilder.success(authenticationResponse));
    }
}
