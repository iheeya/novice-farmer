package com.d207.farmer.dto.farm.register;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FarmPlantRegisterDTO {
    private Long plantId;
    private String myPlantName;
    private String memo;
}
