package com.d207.farmer.service.user;

import com.d207.farmer.domain.user.RedisToken;
import com.d207.farmer.dto.user.UserLoginResponseDTO;
import com.d207.farmer.repository.redis.TokenRepository;
import com.d207.farmer.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JWTUtil jwtUtil;

    public UserLoginResponseDTO saveRefreshToken(Long userId) {
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        tokenRepository.save(new RedisToken(userId, refreshToken));
        return new UserLoginResponseDTO(accessToken, refreshToken); // 최초 로그인 여부 추가 ?
    }

    public String getRefreshToken(Long userId) {
        return tokenRepository.findById(userId).orElseThrow().getRefreshToken();
    }

    public void deleteRefreshToken(Long userId) {
        tokenRepository.deleteById(userId);
    }
}
