package com.d207.farmer.dto.plant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterPlantIllustRequestDTO {
    private Long plantId;
    private int growthStep;
    private String imagePath;
}
