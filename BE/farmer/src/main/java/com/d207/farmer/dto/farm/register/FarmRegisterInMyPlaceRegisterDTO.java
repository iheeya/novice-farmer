package com.d207.farmer.dto.farm.register;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FarmRegisterInMyPlaceRegisterDTO {
    private Long myPlaceId;
    private FarmPlantRegisterDTO plant;
}
