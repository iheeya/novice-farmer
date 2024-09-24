package com.d207.farmer.dto.plant;

import com.d207.farmer.domain.plant.Plant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PlantResponseDTO {
    private Long id;
    private String name;
    private Integer growthDay;
    private Boolean isOn;

    public PlantResponseDTO(Plant plant) {
        this.id = plant.getId();
        this.name = plant.getName();
        this.growthDay = plant.getDegreeDay();
        this.isOn = plant.getIsOn();
    }
}
