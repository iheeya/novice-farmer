package com.d207.farmer.domain.user;

import com.d207.farmer.domain.place.Place;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class RecommendPlace {

    @Id @GeneratedValue
    @Column(name = "recommend_place_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;
}
