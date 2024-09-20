package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ManagePlantRequestDTO {
    private Long farmId;

    public ManagePlantRequestDTO(Long farmId) {
        this.farmId = farmId;
    }
}
