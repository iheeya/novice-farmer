package com.d207.farmer.utils;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.plant.PlantThreshold;
import org.springframework.stereotype.Component;

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
