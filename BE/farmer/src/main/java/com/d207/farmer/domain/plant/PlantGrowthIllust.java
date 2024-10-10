package com.d207.farmer.domain.plant;

import com.d207.farmer.dto.plant.RegisterPlantIllustRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlantGrowthIllust {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "plant_growth_illust_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Column(name = "plant_growth_illust_step")
    private int step;

    @Column(name = "plant_growth_illust_image_path")
    private String imagePath;

    /**
     * 비즈니스 메서드
     */
    public PlantGrowthIllust(RegisterPlantIllustRequestDTO request) {
        this.step = request.getGrowthStep();
        this.imagePath = request.getImagePath();
    }

    public PlantGrowthIllust(Plant plant, int step, String imagePath) {
        this.step = step;
        this.imagePath = imagePath;
        this.plant = plant;
    }
}
