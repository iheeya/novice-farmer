package com.d207.farmer.dto.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MongoPlantDescriptionDTO {
    @Field(name = "season_info")
    private String seasonInfo;

    @Field(name = "temperature_info")
    private String temperatureInfo;

    @Field(name = "soil_info")
    private String soilInfo;

    @Field(name = "acid_info")
    private String acidInfo;

    @Field(name = "crop_info")
    private String plantInfo;

    @Field(name = "watering_info")
    private String wateringInfo;

    @Field(name = "fertilizer_info")
    private List<String> fertilizerInfo;

    @Field(name = "harvest_info")
    private String harvestInfo;

}
