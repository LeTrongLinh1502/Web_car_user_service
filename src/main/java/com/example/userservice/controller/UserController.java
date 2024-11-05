package com.example.userservice.controller;

import com.example.userservice.base.BaseResponse;
import com.example.userservice.base.ResponseBuilder;
import com.example.userservice.dtos.request.UserLoginRequest;
import com.example.userservice.dtos.response.CheckLoginResponse;
import com.example.userservice.model.UserEntity;
import com.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class  UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/user")
    public ResponseEntity<BaseResponse<?>> getUser() {
        List<UserEntity> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(ResponseBuilder.success(allUsers));
    }

}
