package com.example.userservice.dtos.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.Base64;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JWTPayload {
    private Long exp;
    private List<String> scopes;
    private String username;

    public static JWTPayload parserPayloadFromJWT(String token) {
        try {
            String[] arr = token.split("\\.");
            byte[] payload = Base64.getUrlDecoder().decode(arr[1]);
            return new ObjectMapper().readValue(payload, JWTPayload.class);
        } catch (Exception ex) {
            return null;
        }
    }
}
 