package com.d207.farmer.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class UserLoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private boolean isFirstLogin;


    public UserLoginResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isFirstLogin = false; // 기본값 설정
    }

    public UserLoginResponseDTO(String accessToken, String refreshToken, Boolean isFirstLogin) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isFirstLogin = isFirstLogin; // 기본값 설정
    }

}
