package com.d207.farmer.dto.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MongoDescriptionDTO {
    @Field(name = "place_info")
    private String placeInfo;

    @Field(name = "season_info")
    private String seasonInfo;

    @Field(name = "plant_info")
    private String plantInfo;

    @Field(name = "preparation_info")
    private String preparationInfo;

    @Field(name = "fertilizer_info")
    private String fertilizerInfo;

    @Field(name = "watering_info")
    private List<String> wateringInfo;

    @Field(name = "pest_info")
    private String pestInfo;
}
