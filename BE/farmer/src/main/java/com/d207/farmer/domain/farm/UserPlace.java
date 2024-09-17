package com.d207.farmer.domain.farm;

import com.d207.farmer.domain.common.Address;
import com.d207.farmer.domain.common.Direction;
import com.d207.farmer.domain.common.Location;
import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.farm.register.FarmPlaceRegisterDTO;
import com.d207.farmer.dto.farm.register.FarmRegisterRequestDTO;
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

    @Column(name = "user_place_latitude")
    private String latitude;

    @Column(name = "user_place_longitude")
    private String longitude;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "sido", column = @Column(name = "user_place_sido")),
            @AttributeOverride(name = "sigugun", column = @Column(name = "user_place_sigugun")),
            @AttributeOverride(name = "bname1", column = @Column(name = "user_place_bname1")),
            @AttributeOverride(name = "bname2", column = @Column(name = "user_place_bname2")),
            @AttributeOverride(name = "bunji", column = @Column(name = "user_place_bunji")),
            @AttributeOverride(name = "jibun", column = @Column(name = "user_place_jibun")),
            @AttributeOverride(name = "zoneCode", column = @Column(name = "user_place_zonecode"))
    })
    private Address address;

    @Column(name = "user_place_direction")
    private Direction direction;

    public UserPlace (FarmRegisterRequestDTO request) {

    }
}
