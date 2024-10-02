package com.d207.farmer.domain.mongo;

import com.d207.farmer.dto.mongo.MongoFileNameDTO;
import com.d207.farmer.dto.mongo.MongoNameDTO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pest")
public class MongoPestInfo {
    @Id
    private String id;
    private String name;
    private String symptom;
    private List<MongoNameDTO> plants;
    private String treatment;
    private String season;
    private List<MongoFileNameDTO> images;
}
