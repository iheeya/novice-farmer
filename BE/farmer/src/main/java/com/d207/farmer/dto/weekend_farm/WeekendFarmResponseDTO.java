package com.d207.farmer.dto.weekend_farm;

import com.d207.farmer.domain.weekend_farm.WeekendFarm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeekendFarmResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private String imagePath;
    private String desc;

    public WeekendFarmResponseDTO(WeekendFarm weekendFarm) {
        this.id = weekendFarm.getId();
        this.name = weekendFarm.getName();
        this.address = weekendFarm.getAddress();
        this.latitude = weekendFarm.getLatitude();
        this.longitude = weekendFarm.getLongitude();
        this.imagePath = weekendFarm.getImagePath();
        this.desc = weekendFarm.getDesc();
    }
}
