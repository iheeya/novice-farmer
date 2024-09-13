package com.d207.farmer.dto.farm.register;

import com.d207.farmer.domain.common.Direction;
import com.d207.farmer.domain.common.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FarmPlaceRegisterDTO {
    private Long placeId;
    private Location location;
    private Direction direction;
}
