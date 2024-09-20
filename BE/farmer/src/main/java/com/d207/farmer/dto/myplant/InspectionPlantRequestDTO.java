package com.d207.farmer.dto.myplant;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InspectionPlantRequestDTO {
    private String imagePath;

    public InspectionPlantRequestDTO(String imagePath) {
        this.imagePath = imagePath;
    }
}
