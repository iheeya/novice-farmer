package com.d207.farmer.dto.farm.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class AddressInfoDTO {
    private String roadAddress;
    private String jibunAddress;
    private String englishAddress;
    private List<AddressElementDTO> addressElements;
    private String x;
    private String y;
    private double distance;
}
