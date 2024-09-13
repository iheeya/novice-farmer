package com.d207.farmer.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
//@AllArgsConstructor
public class UserInfoRequestByEmailDTO {
    @Email @NotNull
    private String email;

    public UserInfoRequestByEmailDTO() {
    }

    public UserInfoRequestByEmailDTO(String email) {
        this.email = email;
    }
}
