package com.example.redisrepository;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("token")
@AllArgsConstructor
@ToString
public class Token {
    @Id
    private Long id;
    private String refreshToken;
    @TimeToLive
    private Long expiration;
}
