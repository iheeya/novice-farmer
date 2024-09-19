package com.d207.farmer.domain.user;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor // 기본 생성자 추가
public class FavoritePlace {

    @Id  @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;


    public FavoritePlace(User user, Place place) {
        this.user = user;
        this.place = place;
    }


}
