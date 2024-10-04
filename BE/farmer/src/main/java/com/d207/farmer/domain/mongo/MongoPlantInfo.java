package com.d207.farmer.domain.mongo;

import com.d207.farmer.dto.mongo.plant.FertilizerInfo;
import com.d207.farmer.dto.mongo.plant.OptimalTemperature;
import com.d207.farmer.dto.mongo.plant.PestOfPlant;
import com.d207.farmer.dto.mongo.plant.Planting;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "crops")
public class MongoPlantInfo {
    @Id
    private String id;

    private String name;

    private String definition;

    @Field(name = "best_season")
    private String bestSeason;

    @Field(name = "optimal_temperature")
    private OptimalTemperature optimalTemperature;

    private Planting planting;

    @Field(name = "fertilizer_info")
    private FertilizerInfo fertilizerInfo;

    private List<PestOfPlant> pests;

    @Field(name = "additional_info")
    private String additionalInfo;

    private List<String> images;
}
