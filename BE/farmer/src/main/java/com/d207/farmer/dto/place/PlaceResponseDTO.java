package com.d207.farmer.dto.place;

import com.d207.farmer.domain.place.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PlaceResponseDTO {
    private Long id;
    private String name;
    private String desc;
    private Boolean isOn;

    public PlaceResponseDTO(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.desc = place.getDesc();
        this.isOn = place.getIsOn();
    }

}
