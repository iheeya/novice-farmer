package com.d207.farmer.domain.common;

import jakarta.persistence.Embeddable;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Address {

    private String sido;

    private String sigugun;

    private String bname1;

    private String bname2;

    private String bunji;

    private String jibun;

    private String zonecode;
}
