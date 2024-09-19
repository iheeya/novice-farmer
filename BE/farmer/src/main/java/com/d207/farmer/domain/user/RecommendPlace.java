package com.d207.farmer.domain.user;

import com.d207.farmer.domain.place.Place;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    /**
     * 비즈니스 로직
     */
    public RecommendPlace(User user, Place place) {
        this.user = user;
        this.place = place;
    }
}
