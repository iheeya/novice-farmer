package com.d207.farmer.dto.myplace;

import com.d207.farmer.domain.common.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceInfoDTO {
    private Long placeId;
    private String placeName;
    private String myPlaceName;
    private int farmCount;
    private String weather;
    private Address address;
}
