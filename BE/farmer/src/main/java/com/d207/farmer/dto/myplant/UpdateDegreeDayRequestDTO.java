package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateDegreeDayRequestDTO {
    private Long myPlantId;
    private int degreeDay;
}
