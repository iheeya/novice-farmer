package com.d207.farmer.domain.mongo;

import com.d207.farmer.dto.mongo.MongoFileNameDTO;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "fertilizer")
public class MongoFertilizerInfo {
    @Id
    private String id;
    private String type;
    private String name;
    private String method;
    private String cycle;
    private String effect;
    private List<MongoFileNameDTO> images;
}
