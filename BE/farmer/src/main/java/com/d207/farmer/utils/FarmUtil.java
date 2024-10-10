package com.d207.farmer.utils;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.plant.PlantThreshold;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FarmUtil {

    // 작물 growthStep 계산
    public int getGrowthStep(Farm farm) {
        int growthStep = 1;
        int maxDegreeDay = farm.getPlant().getDegreeDay();
        for(PlantThreshold pt : farm.getPlant().getPlantThresholds()) {
            if(farm.getDegreeDay() <= pt.getDegreeDay()) break;
            growthStep++;
        }
        if(farm.getDegreeDay() == maxDegreeDay) growthStep++;
        return growthStep;
    }
}
