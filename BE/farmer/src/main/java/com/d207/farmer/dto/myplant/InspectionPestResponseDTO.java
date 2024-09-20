package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionPestResponseDTO {
    private IsPestDTO isPest;
    private PestInfoDTO pestInfo;

    @Getter
    @AllArgsConstructor
    private static class IsPestDTO {
        private Boolean hasPest;
        private String myImagePath;
    }

    @Getter
    @AllArgsConstructor
    private static class PestInfoDTO {
        private String pestImagePath;
        private String pestName;
        private String pestDesc;
        private String pestCureDesc;
    }
}
