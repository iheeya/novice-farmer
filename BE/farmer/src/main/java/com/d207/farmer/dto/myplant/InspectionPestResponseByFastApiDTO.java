package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionPestResponseByFastApiDTO {
    private Boolean hasPest;
    private String pestName;
}
