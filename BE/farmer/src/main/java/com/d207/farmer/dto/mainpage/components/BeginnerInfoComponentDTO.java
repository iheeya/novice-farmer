package com.d207.farmer.dto.mainpage.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
// @NoArgsConstructor
public class BeginnerInfoComponentDTO {
    private Boolean isUsable;
    private List<plantInfoDTO> plants;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class plantInfoDTO {
        private Long plantId;
        private String plantName;
        private String plantDesc;
    }

    public BeginnerInfoComponentDTO() {
        plants = new ArrayList<>();
        plants.add(new plantInfoDTO());
        plants.add(new plantInfoDTO());
    }
}
