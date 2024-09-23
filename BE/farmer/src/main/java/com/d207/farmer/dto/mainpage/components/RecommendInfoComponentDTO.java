package com.d207.farmer.dto.mainpage.components;

import com.d207.farmer.domain.user.RecommendPlant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendInfoComponentDTO {
    private Boolean isUsable;
    private RecommendByDTO recommendByPlace;
    private RecommendByDTO recommendByUser;

    @Getter
    @AllArgsConstructor
    //@NoArgsConstructor
    public static class RecommendByDTO {
        private String comment;
        private List<RecommendPlantDTO> recommendPlants;

        public RecommendByDTO() {
            recommendPlants = new ArrayList<>();
            recommendPlants.add(new RecommendPlantDTO());
            recommendPlants.add(new RecommendPlantDTO());
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecommendPlantDTO {
        private Long plantId;
        private String plantName;
    }
}
