package com.d207.farmer.dto.farm.register;

import com.d207.farmer.domain.common.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FarmPlaceRegisterDTO {
    private Long placeId;
    private Address address;
}
