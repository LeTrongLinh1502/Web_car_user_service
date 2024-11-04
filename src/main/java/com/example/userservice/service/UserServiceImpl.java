package com.example.userservice.service;

import com.example.userservice.dtos.request.UserLoginRequest;
import com.example.userservice.dtos.response.CheckLoginResponse;
import com.example.userservice.model.UserEntity;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
     private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public CheckLoginResponse checkLogin(String username, String password) {
        CheckLoginResponse res = new CheckLoginResponse();
        res.setUserName(username);
        res.setPassWord(password);
        UserEntity userEntity =userRepository.findFirstByUserNameAndPassWord(username, password);
        if (Objects.isNull(userEntity)){
            res.setStatus(false);
            res.setError("Sai mật khẩu hoặc tài khoản");
        }else res.setStatus(true);
        return res;
    }

}
