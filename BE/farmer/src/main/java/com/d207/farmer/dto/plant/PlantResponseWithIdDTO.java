package com.d207.farmer.dto.plant;

import com.d207.farmer.domain.plant.Plant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PlantResponseWithIdDTO {
    private Long id;
    private String name;
    //private Integer growthDay;
    private Boolean isFavorite;

    public PlantResponseWithIdDTO(Plant plant) {
        this.id = plant.getId();
        this.name = plant.getName();
        //this.growthDay = plant.getGrowthDay();
        this.isFavorite = false;
    }
    public void setIsOn(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    // isOn 값을 가져오는 메서드 추가
    public Boolean getIsFavorite() {
        return this.isFavorite;
    }

}
