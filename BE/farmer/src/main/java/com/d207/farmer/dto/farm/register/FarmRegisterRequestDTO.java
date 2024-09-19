package com.d207.farmer.dto.farm.register;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FarmRegisterRequestDTO {
    private FarmPlaceRegisterDTO place;
    private FarmPlantRegisterDTO plant;
}
