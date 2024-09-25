package com.d207.farmer.domain.user;

import com.d207.farmer.domain.plant.Plant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class RecommendPlant {

    @Id @GeneratedValue
    @Column(name = "recommend_plant_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    public RecommendPlant(User user, Plant plantDomain) {
        this.user = user;
        this.plant = plantDomain;
    }
}
