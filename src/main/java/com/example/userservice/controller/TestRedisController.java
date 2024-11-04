package com.example.userservice.controller;

import com.example.userservice.config.redis.RedisComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class TestRedisController {
    private final RedisComponent redisComponent;

    public TestRedisController(RedisComponent redisComponent) {
        this.redisComponent = redisComponent;
    }

    @GetMapping("/api/test-redis")
    public String ping() {
        try {
            redisComponent.addValue("test_2024", "test ok", 2, TimeUnit.DAYS);
            String response = (String) redisComponent.getValue("test_2024");
            return response;
        }catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }
    }
}
