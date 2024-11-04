package com.example.userservice.service;

import com.example.userservice.dtos.request.UserLoginRequest;
import com.example.userservice.dtos.response.CheckLoginResponse;
import com.example.userservice.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserEntity> getAllUsers ();

    CheckLoginResponse checkLogin(String username, String password);
}
