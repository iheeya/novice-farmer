package com.d207.farmer.domain.mongo;

import com.d207.farmer.dto.mongo.MongoPlantDescriptionDTO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "crop")
public class MongoPlantInfo {
    @Id
    private String id;
    private List<String> fertilizer;
    private List<String> pest;
    private MongoPlantDescriptionDTO description;
}
