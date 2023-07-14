package com.example.redisconnect.redistemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {
    private final JwtUtil jwtUtil;
    @GetMapping("refresh/{id}")
    public String getRefresh(@PathVariable("id") Long id) {
        System.out.println("id = " + id+id);
        String refreshToken = jwtUtil.createRefreshToken(id);
        return refreshToken;
    }
    @GetMapping("refresh/validate/{token}")
    public String validateRefresh(@PathVariable("token") String token) {
        boolean isValidate = jwtUtil.validateRefreshToken(token);
        if (isValidate) {
            String refreshToken = jwtUtil.createRefreshToken(1L);
            return refreshToken;
        }
        return "validate failed";
    }
}
