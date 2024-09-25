package com.d207.farmer.dto.user;

import com.d207.farmer.domain.user.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@ToString
public class UserRegisterRequestDTO {
    @Email @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull @Length(min = 2, max = 20)
    private String nickname;

    @NotNull @Min(1) @Max(150)
    private int age;

    @NotNull
//    @Pattern(regexp = "MALE|FEMALE")
    private Gender gender;

    @NotNull
    private String address;

    @NotNull
    private Boolean pushAllow;
}
