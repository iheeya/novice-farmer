package com.d207.farmer.dto.farm.get;

import com.d207.farmer.domain.common.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendPlantRequestDTO {
    private Long placeId;
    private Address address;
}
