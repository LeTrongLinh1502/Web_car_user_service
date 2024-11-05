package com.example.userservice.service.auth;

import com.example.userservice.config.redis.RedisComponent;
import com.example.userservice.dtos.auth.JWTPayload;
import com.example.userservice.dtos.handlers.CustomBadRequestException;
import com.example.userservice.dtos.request.UserAuthRequest;
import com.example.userservice.dtos.response.AuthenticationResponse;
import com.example.userservice.dtos.response.CheckLoginResponse;
import com.example.userservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UserService userService;
    @Value("${auth.secret.key}")
    String secretKey;
    private final RedisComponent redisComponent;

    public AuthService(UserService userService, RedisComponent redisComponent) {
        this.userService = userService;
        this.redisComponent = redisComponent;
    }

    public Integer verify(String token) {
        // Decode and use the secret key to create a signing key
//        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
//        SecretKey signingKey = Keys.hmacShaKeyFor(decodedKey);
//
//        // Verify the signature
//        Jwts.parser()
//                .setSigningKey(signingKey)
//                .build()
//                .parseClaimsJws(token);

//        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        JWTPayload payload = JWTPayload.parserPayloadFromJWT(token);
        // check payload null
        if (Objects.isNull(payload)) {
            return HttpStatus.FORBIDDEN.value();
        }

        // check hết hạn token
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        if (payload.getExp() < currentTimeSeconds){
            return HttpStatus.FORBIDDEN.value();
        }

        // check scopes null
        Set<String> scopes = new HashSet<>(payload.getScopes());
        if (CollectionUtils.isEmpty(scopes)) {
            return HttpStatus.FORBIDDEN.value();
        }

        if (!scopes.containsAll(Set.of("quang999", "user-service:token-success"))) {
            return HttpStatus.FORBIDDEN.value();
        }

        return HttpStatus.OK.value();
    }

    public AuthenticationResponse getToken(UserAuthRequest authRequest) {
        CheckLoginResponse verifyUser = userService.checkLogin(authRequest.getUsername(), authRequest.getPassword());
        if(verifyUser.getStatus() == Boolean.FALSE) throw new CustomBadRequestException("Username or password is incorrect");
        //TODO: gen accessToken
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 86400000);
        Map<String, Object> payloadAccessToken = new HashMap<>();
        payloadAccessToken.put("iss", "car_service_auth_token");
        payloadAccessToken.put("username",authRequest.getUsername());
        payloadAccessToken.put("scopes", authRequest.getScopes());
        payloadAccessToken.put("iat", now);
        payloadAccessToken.put("exp", expiration);

        String accessToken = Jwts.builder()
                .setClaims(payloadAccessToken)
                .setId(generateTokenId())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        // Tạo xong đẩy token vào redis
        String keyRedis = authRequest.getUsername() + "_" + authRequest.getPassword() + "user-service";
        redisComponent.addValue(keyRedis, accessToken, 2, TimeUnit.DAYS);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(expiration.getTime())
                .build();
    }
    private static String generateTokenId() {
        return UUID.randomUUID().toString();
    }

}

