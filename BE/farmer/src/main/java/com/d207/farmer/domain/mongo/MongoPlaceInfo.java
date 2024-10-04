package com.d207.farmer.domain.mongo;

import com.d207.farmer.dto.mongo.MongoPlaceDescriptionDTO;
import com.d207.farmer.dto.mongo.MongoFileNameDTO;
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
@Document(collection = "gardens")
public class MongoPlaceInfo {
    @Id
    private String id;
    private String name;
    private List<Map<String, List<String>>> season;
    private MongoPlaceDescriptionDTO description;
    private MongoFileNameDTO images;
}
