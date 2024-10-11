package com.d207.farmer.dto.mongo.plant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GrowthStage {
    private String stage;
    private FertilizerOfPlant fertilizer;
}
