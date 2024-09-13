package com.d207.farmer.domain.plant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PlantGrowthIllust {

    @Id @GeneratedValue
    @Column(name = "plant_growth_illust_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Column(name = "plant_growth_illust_step")
    private int step;

    @Column(name = "plant_growth_illust_image_path")
    private String imagePath;
}
