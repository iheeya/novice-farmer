package com.d207.farmer.dto.farm.get;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendPlaceResponseDTO {
    private List<placeDTO> places;

    @Getter
    @AllArgsConstructor
    public static class placeDTO {
        private Long placeId;
    }
}
