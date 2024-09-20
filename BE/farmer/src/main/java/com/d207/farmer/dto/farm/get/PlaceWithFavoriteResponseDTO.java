package com.d207.farmer.dto.farm.get;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceWithFavoriteResponseDTO {
    private Long placeId;
    private String placeName;
    private Boolean isFavorite;
    private Boolean isService;
}
