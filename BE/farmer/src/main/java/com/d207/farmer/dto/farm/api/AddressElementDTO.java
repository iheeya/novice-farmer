package com.d207.farmer.dto.farm.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class AddressElementDTO {
    private List<String> types;
    private String longName;
    private String shortName;
    private String code;
}
