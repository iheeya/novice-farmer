package com.d207.farmer.dto.plant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PlantRegisterRequestDTO {
    private String name;
    private Integer degreeDay;
    private Boolean isOn;
}
