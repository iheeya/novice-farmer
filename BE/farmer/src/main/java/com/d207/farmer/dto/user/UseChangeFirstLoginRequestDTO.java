package com.d207.farmer.dto.user;

import com.d207.farmer.domain.user.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UseChangeFirstLoginRequestDTO {

    private Long id;
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime regDate;
    private Boolean isFirstLogin;
    private Gender gender;
    private Integer age;
    private String address;
    private String imagePath;
    private Boolean pushallow;




}
