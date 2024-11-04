package com.example.userservice.service.user;

import com.example.userservice.dtos.response.CheckLoginResponse;
import com.example.userservice.model.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCheckLoginSuccess() {
        // Giả lập người dùng trong database
        String username = "lequang24@gmail.com";
        String password = "1999";
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(username);
        userEntity.setPassWord(password);

        // Giả lập hành vi của userRepository
        when(userRepository.findFirstByUserNameAndPassWord(username, password)).thenReturn(userEntity);

        // Gọi phương thức checkLogin
        CheckLoginResponse response = userService.checkLogin(username, password);

        // Kiểm tra kết quả
        assertTrue(response.getStatus());
    }
}
