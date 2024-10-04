package com.d207.farmer.dto.mongo.plant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PestOfPlant {
    private String name;
    private String description;
    private String prevention;
    private String treatment;
}
