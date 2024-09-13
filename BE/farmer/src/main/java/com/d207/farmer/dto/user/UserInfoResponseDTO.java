package com.d207.farmer.dto.user;

import com.d207.farmer.domain.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
@Builder
public class UserInfoResponseDTO {
    private String email;
    private String nickname;
    private LocalDateTime regDate;
    private Boolean isFirstLogin;
    private Gender gender;
    private Integer age;
    private String address;
}
