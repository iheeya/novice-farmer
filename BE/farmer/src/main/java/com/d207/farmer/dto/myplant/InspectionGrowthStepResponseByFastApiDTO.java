package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InspectionGrowthStepResponseByFastApiDTO {
    private int step;

    public InspectionGrowthStepResponseByFastApiDTO(int step) {
        this.step = step;
    }
}
