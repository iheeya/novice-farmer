package com.d207.farmer.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class SurveyRegisterRequestDTO {
    private List<Plant> plant;
    private List<Place> place;

    // Getters and Setters

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Plant {
        private Long id;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Place {
        private Long id;
    }
}


