package com.d207.farmer.dto.mongo.plant;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "plant")
public class MongoPlantInfoDTO {
    @Id
    private String id;

    private String name;

    private PlantInfoDTO info;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlantInfoDTO {
        private String recommendSeedSeason;
        private List<String> farmMethod;
        private List<String> acceptFert;
        private List<String> pestName;
    }
}
