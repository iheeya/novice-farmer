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
@Document(collection = "crop")
public class MongoPlantInfo {
    @Id
    private String id;
    private String name;
    private String info;
    private List<MongoNameDTO> fertilizers;
    private List<MongoNameDTO> pests;
    private String harvest;
    private List<MongoFileNameDTO> images;
    private Integer watering; // 물 주는 주기 FIXME 리스트로 수정될수도
}
