package com.d207.farmer.dto.place;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PlaceRegisterRequestDTO {
    private String name;
    private String desc;
    private Boolean isOn;
}
