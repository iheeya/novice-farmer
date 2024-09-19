package com.d207.farmer.dto.farm.get;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceWithRecommendAndFavoriteResponseDTO {
    private Long placeId;
    private String placeName;
    private Boolean isFavorite;
    private Boolean isRecommend;
    private Boolean isService;
}
