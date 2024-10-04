package com.d207.farmer.domain.mongo;

import com.d207.farmer.dto.mongo.MongoFileNameDTO;
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
@Document(collection = "fertilizers")
public class MongoFertilizerInfo {
    @Id
    private String id;

    private String type;

    private String component;

    private String description;

    @Field(name = "usage_crops")
    private List<String> usageCrops;

    private List<String> brands;

    private List<MongoFileNameDTO> images;
}
