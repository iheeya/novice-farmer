package com.d207.farmer.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class SurveyRegisterRequestDTO {
    private List<Plant> plant;
    private List<Place> place;

    // Getters and Setters

    public static class Plant {
        private Long id;
        // Getter 추가
        public Long getId() {
            return id;
        }



        // Getters and Setters
    }

    public static class Place {
        private Long id;

        // Getter 추가
        public Long getId() {
            return id;
        }

        // Getters and Setters
    }
}


