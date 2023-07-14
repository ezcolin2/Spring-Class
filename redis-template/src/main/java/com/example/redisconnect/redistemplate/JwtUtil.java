package com.example.redisconnect.redistemplate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
//    private final RedisTemplate<String, String> redisTemplate;
    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOperations;
    @Value("${jwt.secretKey}") //application.properties에 저장되어 있는 값을 가져온다.
    private String secretKey;
    @Value("${jwt.expiredMs}") //application.properties에 저장되어 있는 값을 가져온다.
    private Long expiredMs;

    public String getMemberId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("memberId", String.class);
    }
    public Long getId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("id", Long.class);
    }

    public boolean isExpired(String token) {
        return !Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public String createAccessToken(String memberId, Long id) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);
        claims.put("id", id);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }
    public String createRefreshToken(Long id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        System.out.println("token = " + token);
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(id.toString(), token);
//        ListOperations<String, String> valueOperations = redisTemplate.opsForList();
//        valueOperations.leftPush(id.toString(), token);
        return token;
    }

    public boolean validateRefreshToken(String refreshToken) {
        boolean expired = isExpired(refreshToken);
        System.out.println("expired = " + expired);
        Long pk = getId(refreshToken);
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String storedRefreshToken = valueOperations.get(pk.toString());
        System.out.println("storedRefreshToken = " + storedRefreshToken);
        return expired && refreshToken.equals(storedRefreshToken);

    }
}