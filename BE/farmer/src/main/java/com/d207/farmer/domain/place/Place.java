package com.d207.farmer.domain.place;

import com.d207.farmer.dto.place.PlaceRegisterRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(name = "place_name")
    private String name;

    @Column(name = "place_desc")
    private String desc;

    @Column(name = "place_is_on")
    private Boolean isOn;

    /**
     * 비즈니스 로직
     */
    public Place(PlaceRegisterRequestDTO request) {
        this.name = request.getName();
        this.desc = request.getDesc();
        this.isOn = request.getIsOn();
    }
}
