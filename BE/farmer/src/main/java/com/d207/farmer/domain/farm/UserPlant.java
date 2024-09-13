package com.d207.farmer.domain.farm;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class UserPlant {

    @Id @GeneratedValue
    @Column(name = "user_plant_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_place_id")
    private UserPlace userPlace;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @Column(name = "user_plant_name")
    private String name;
}
