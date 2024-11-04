package com.example.userservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.userservice.dtos.response.CheckLoginResponse;
import com.example.userservice.model.UserEntity;
import com.example.userservice.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceImplDiffblueTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Test {@link UserServiceImpl#checkLogin(String, String)}.
     * <p>
     * Method under test: {@link UserServiceImpl#checkLogin(String, String)}
     */
    @Test
    @DisplayName("Test checkLogin(String, String)")
    void testCheckLogin() {
        // Arrange
        UserEntity userEntity = new UserEntity();
        userEntity.setPassWord("Pass Word");
        userEntity.setUserName("janedoe");

        // Act
        CheckLoginResponse actualCheckLoginResult = userServiceImpl.checkLogin("janedoe", "iloveyou");

        // Assert
        assertTrue(actualCheckLoginResult.getStatus());
    }
}
