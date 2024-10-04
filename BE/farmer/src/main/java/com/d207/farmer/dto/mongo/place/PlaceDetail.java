package com.d207.farmer.dto.mongo.place;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@AllArgsConstructor
public class PlaceDetail {
    private String name;

    private List<String> images;

    private String description;

    @Field(name = "suitable_crops")
    private List<String> suitableCrops;
}
