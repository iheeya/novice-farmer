package com.d207.farmer.dto.farm.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class RecommendPlaceRequestDTO {
    private Long plantId;

    public RecommendPlaceRequestDTO(Long plantId) {
        this.plantId = plantId;
    }

}
