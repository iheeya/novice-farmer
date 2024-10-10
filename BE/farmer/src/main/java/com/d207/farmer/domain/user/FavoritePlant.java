package com.d207.farmer.domain.user;

import com.d207.farmer.domain.plant.Plant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
@NoArgsConstructor // 기본 생성자 추가
public class FavoritePlant {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;


    // 새로운 생성자 추가
    public FavoritePlant(User user, Plant plant) {
        this.user = user;
        this.plant = plant;
    }
}
