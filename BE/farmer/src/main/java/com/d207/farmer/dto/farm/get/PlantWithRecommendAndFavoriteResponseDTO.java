package com.d207.farmer.dto.farm.get;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlantWithRecommendAndFavoriteResponseDTO {
    private Long plantId;
    private String plantName;
    private Boolean isFavorite;
    private Boolean isRecommend;
    private Boolean isService;
}
