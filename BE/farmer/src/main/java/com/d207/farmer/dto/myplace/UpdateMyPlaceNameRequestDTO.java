package com.d207.farmer.dto.myplace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMyPlaceNameRequestDTO {
    private Long userPlaceId;
    private String userPlaceName;
}
