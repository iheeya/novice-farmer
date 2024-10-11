package com.d207.farmer.domain.mongo;

import com.d207.farmer.dto.mongo.place.PlaceDetail;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "gardens")
public class MongoPlaceInfo {
    @Id
    private String id;
    private String heading;
    private String content;
    private List<PlaceDetail> contents;
}
