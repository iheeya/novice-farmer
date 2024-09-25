package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionGrowthStepResponseDTO {
    private int beforeStep;
    private int afterStep;
}
