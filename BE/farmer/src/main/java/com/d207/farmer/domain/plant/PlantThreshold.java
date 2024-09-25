package com.d207.farmer.domain.plant;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PlantThreshold {

    @Id @GeneratedValue
    @Column(name = "plant_threshold_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Column(name = "plant_threshold_step")
    private Integer thresholdStep;

    @Column(name = "plant_threshold_degree_day")
    private Integer degreeDay;

    /**
     * 비즈니스 메서드
     */
    public PlantThreshold(Plant plant, Integer thresholdStep, Integer degreeDay) {
        this.plant = plant;
        this.thresholdStep = thresholdStep;
        this.degreeDay = degreeDay;
    }
}
