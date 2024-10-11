package com.d207.farmer.dto.mongo.place;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceInfoResponseDTO {
    private InfoDetailDTO justice;
    private InfoDetailDTO purpose;
    private InfoDetailDTO effect;
    private InfoDetailDTO placeType;

    @Getter
    @AllArgsConstructor
    public static class InfoDetailDTO {
        private String title;
        private String comment;
        private String content;
    }
}
