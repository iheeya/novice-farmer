package com.d207.farmer.dto.farm.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class GeoAPIResponseDTO {
    private String status;
    private MetaDTO meta;
    private List<AddressInfoDTO> addresses;
    private String errorMessage;
}
