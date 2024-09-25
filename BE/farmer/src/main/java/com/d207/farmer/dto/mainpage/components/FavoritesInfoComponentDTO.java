package com.d207.farmer.dto.mainpage.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
// @NoArgsConstructor
public class FavoritesInfoComponentDTO {
    private Boolean isUsable;
    private List<FavoritePlantDTO> favoritePlants;
    private List<FavoritePlaceDTO> favoritePlaces;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FavoritePlantDTO {
        private Long plantId;
        private String plantName;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FavoritePlaceDTO {
        private Long placeId;
        private String placeName;
    }

    public FavoritesInfoComponentDTO() {
        favoritePlants = new ArrayList<>();
        favoritePlants.add(new FavoritePlantDTO());
        favoritePlants.add(new FavoritePlantDTO());

        favoritePlaces = new ArrayList<>();
        favoritePlaces.add(new FavoritePlaceDTO());
        favoritePlaces.add(new FavoritePlaceDTO());
    }
}
