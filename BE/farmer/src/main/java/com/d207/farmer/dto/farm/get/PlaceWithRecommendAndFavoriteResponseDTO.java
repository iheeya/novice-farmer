package com.d207.farmer.dto.farm.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PlaceWithRecommendAndFavoriteResponseDTO {
    private Long placeId;
    private String placeName;
    private Boolean isFavorite;
    private Boolean isRecommend;
    private Boolean isService;
}
