package com.d207.farmer.dto.farm.todo;

import com.d207.farmer.domain.farm.TodoType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FarmTodoResponseDTO {
    private Long placeId;
    private String placeName;
    private Long myPlaceId;
    private String myPlaceName;
    private String weather;
    private List<PlantDTO> plants;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlantDTO {
        private Long plantId;
        private String plantName;
        private Long myPlantId;
        private String myPlantName;
        private Integer growthStep;
        private Integer plantDegreeRatio;
        private List<TodoDTO> todos;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodoDTO {
        private LocalDate todoDate;
        private TodoType todoType;
        private Integer remainDay;
    }
}
