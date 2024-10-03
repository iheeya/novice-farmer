package com.d207.farmer.dto.user.sample;

import com.d207.farmer.domain.user.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class UserSampleRegisterRequestDTO {
    private String email;
    private String password;
    private String nickname;
    private int age;
    private Gender gender;
    private String address;
    private Boolean pushAllow;
    private String imagePath;
}
