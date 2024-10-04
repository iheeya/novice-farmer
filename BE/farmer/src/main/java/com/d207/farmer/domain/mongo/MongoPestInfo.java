package com.d207.farmer.domain.mongo;

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
@Document(collection = "pest")
public class MongoPestInfo {
    @Id
    private String id;

    private String name;

    private String description;

    @Field(name = "affected_crops")
    private List<String> affectedCrops;

    private String symptoms;

    private String prevention;

    private String treatment;

    private List<String> images;
}
