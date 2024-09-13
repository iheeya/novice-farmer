package com.d207.farmer.domain.plant;

import com.d207.farmer.dto.plant.PlantRegisterRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plant {

    @Id @GeneratedValue
    @Column(name = "plant_id")
    private Long id;

    @Column(name = "plant_name")
    private String name;

    @Column(name = "plant_growth_day")
    private Integer growthDay;

    @Column(name = "plant_is_on")
    private Boolean isOn;

    /**
     * 비즈니스 로직
     */
    public Plant(PlantRegisterRequestDTO request) {
        this.name = request.getName();
        this.growthDay = request.getGrowthDay();
        this.isOn = request.getIsOn();
    }
}
