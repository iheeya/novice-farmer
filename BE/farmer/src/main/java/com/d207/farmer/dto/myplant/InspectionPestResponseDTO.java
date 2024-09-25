package com.d207.farmer.dto.myplant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InspectionPestResponseDTO {
    private IsPestDTO isPest;
    private PestInfoDTO pestInfo;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IsPestDTO {
        private Boolean hasPest;
        private String myImagePath;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PestInfoDTO {
        private String pestImagePath;
        private String pestName;
        private String pestDesc;
        private String pestCureDesc;
    }
}
