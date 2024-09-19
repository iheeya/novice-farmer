package com.d207.farmer.dto.place;

import com.d207.farmer.domain.place.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PlaceResponseWithIdDTO {
    private Long id;
    private String name;
    private String desc;
    private Boolean isFavorite;

    public PlaceResponseWithIdDTO(Place place) {
        this.id = place.getId();
        this.name = place.getName();
        this.desc = place.getDesc();
        this.isFavorite = false;
    }

    // setIsOn 메서드 추가
    public void setIsfavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean getIsFavorite(){
        return this.isFavorite;
    }


}
