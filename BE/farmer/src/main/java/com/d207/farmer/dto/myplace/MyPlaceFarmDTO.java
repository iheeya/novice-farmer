package com.d207.farmer.dto.myplace;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyPlaceFarmDTO {
    private Long plantId;
    private String plantName;
    private Long myPlantId;
    private String myPlantName;
    private int myPlantGrowthStep;
    private String imagePath;
    private String todoInfo;
    private String seedDate;
}
