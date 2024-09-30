package com.d207.farmer.domain.mongo;

import com.d207.farmer.dto.mongo.MongoFileNameDTO;
import com.d207.farmer.dto.mongo.MongoNameDTO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "plant")
public class MongoPlantInfo {
    @Id
    private String id;
    private String name;
    private String cultivateInfo; // FIXME 이름
    private List<MongoNameDTO> fertilizers;
    private List<MongoNameDTO> pests;
    private String harvest;
    private List<MongoFileNameDTO> images;
    private Map<String, Double> waterAmountByGrowthStep; // FIXME 이름
}
