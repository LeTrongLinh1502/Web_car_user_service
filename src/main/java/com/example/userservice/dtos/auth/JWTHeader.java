package com.example.userservice.dtos.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Base64;

@Data
public class JWTHeader {

    @JsonProperty("typ")
    private String type;
    @JsonProperty("kid")
    private String keyID;
    @JsonProperty("alg")
    private String algorithm;

    @SneakyThrows
    public static JWTHeader parserHeaderFromJWT(String token) {
        String[] arr = token.split("\\.");
        byte[] payload = Base64.getDecoder().decode(arr[0]);
        return new ObjectMapper().readValue(payload, JWTHeader.class);
    }
}
