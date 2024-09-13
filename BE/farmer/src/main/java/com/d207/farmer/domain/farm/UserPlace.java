package com.d207.farmer.domain.farm;

import com.d207.farmer.domain.common.Location;
import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPlace {

    @Id @GeneratedValue
    @Column(name = "user_place_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_place_name")
    private String name;

    @Column(name = "user_place_addr")
    private String address;

    @Column(name = "user_place_latitude")
    private String latitude;

    @Column(name = "user_place_longitude")
    private String longitude;

    public UserPlace(User user, Place place, Location location, String latitude, String longitude) {
        this.place = place;
        this.user = user;
        this.address = location.toString();
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
