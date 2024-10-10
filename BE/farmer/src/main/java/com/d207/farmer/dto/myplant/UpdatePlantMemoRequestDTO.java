package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePlantMemoRequestDTO {
    private Long farmId;
    private String memo;
}
