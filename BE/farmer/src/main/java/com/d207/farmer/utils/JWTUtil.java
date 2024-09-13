package com.d207.farmer.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class JWTUtil {

    @Value("${spring.jwt.salt}")
    private String salt;

    @Value("${spring.jwt.access-token.expiretime}")
    private long accessTokenExpireTime;

    @Value("${spring.jwt.refresh-token.expiretime}")
    private long refreshTokenExpireTime;

    // accessToken 발급
    public String createAccessToken(Long userId) {
        return createToken(userId, "access-token", accessTokenExpireTime);
    }

    // refreshToken 발급
    public String createRefreshToken(Long userId) {
        return createToken(userId, "refresh-token", refreshTokenExpireTime);
    }

    private String createToken(Long userId, String subject, long expireTime) {
        Claims claims = Jwts.claims()
                .setSubject(subject) // 토큰 제목 설정
                .setIssuedAt(new Date()) // 생성일 설정
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)); // 만료일 설정(토큰 유효기간)

        // 저장할 data의 key, value
        claims.put("userId", userId);

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT") // header 설정 -> 토큰 타입, 해쉬 알고리즘 세팅
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, this.generateKey()) // Signature 설정 -> secret key를 사용해서 암호화
                .compact(); // 직렬화

        return jwt;
    }

    private byte[] generateKey() {
        // charset 설정 하지 않으면 사용자 플랫폼의 기본 인코딩 설정으로 인코딩
        return salt.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 토큰 유효성 검사
     */
    public boolean validToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
        // TODO Exception 발생 시 무슨 Exception인지 확인하고 try-catch로 묶기
        return true;
    }

    public Long getUserId(String token) {
        // TODO Exception 발생 시 무슨 Exception인지 확인하고 try-catch로 묶기
        Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
        Map<String, Object> value = claims.getBody();
        Number n = (Number) value.get("userId");
        return n.longValue();
    }
}
