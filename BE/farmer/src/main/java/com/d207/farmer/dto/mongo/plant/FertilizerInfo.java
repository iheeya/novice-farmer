package com.d207.farmer.dto.mongo.plant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@AllArgsConstructor
public class FertilizerInfo {
    @Field(name = "growth_stages")
    private List<GrowthStage> growthStages;
}
