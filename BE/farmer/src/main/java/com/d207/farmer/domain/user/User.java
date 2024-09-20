package com.d207.farmer.domain.user;

import com.d207.farmer.dto.user.UserRegisterRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_nickname", unique = true)
    private String nickname;

    @Column(name = "user_pwd")
    private String password;

    @Column(name = "user_reg_date")
    private LocalDateTime regDate;

    @Column(name = "user_is_first_login")
    private Boolean isFirstLogin;

    @Column(name = "user_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "user_age")
    private Integer age;

    @Column(name = "user_addr")
    private String address;

    @Column(name = "user_image_path")
    private String imagePath;

    @Column(name = "user_allow_push")
    private Boolean pushAllow;

    /*
     * 비즈니스 메서드
     */
    public User (UserRegisterRequestDTO request) {
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.nickname = request.getNickname();
        this.regDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.isFirstLogin = true;
        this.gender = request.getGender();
        this.age = request.getAge();
        this.address = request.getAddress();
        this.imagePath = ""; // TODO default 값 설정 필요
        this.pushAllow = request.getPsuhAllow();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", regDate=" + regDate +
                ", isFirstLogin=" + isFirstLogin +
                ", gender=" + gender +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
