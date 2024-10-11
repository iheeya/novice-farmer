package com.d207.farmer.domain.plant;

import com.d207.farmer.dto.plant.PlantRegisterRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plant {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    @Column(name = "plant_name")
    private String name;

    @Column(name = "plant_degree_day")
    private Integer degreeDay;

    @Column(name = "plant_is_on")
    private Boolean isOn;

    @OneToMany(mappedBy = "plant")
    private List<PlantGrowthIllust> plantGrowthIllusts = new ArrayList<>();

    // 연관관계 편의 메서드
    public void setPlantGrowthIllusts(PlantGrowthIllust plantGrowthIllust) {
        plantGrowthIllusts.add(plantGrowthIllust);
        plantGrowthIllust.setPlant(this);
    }

    @OneToMany(mappedBy = "plant")
    private List<PlantThreshold> plantThresholds = new ArrayList<>();

    /**
     * 비즈니스 로직
     */
    public Plant(PlantRegisterRequestDTO request) {
        this.name = request.getName();
        this.degreeDay = request.getDegreeDay();
        this.isOn = request.getIsOn();
    }
}
