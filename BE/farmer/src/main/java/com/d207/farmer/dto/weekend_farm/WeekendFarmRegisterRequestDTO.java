package com.d207.farmer.dto.weekend_farm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeekendFarmRegisterRequestDTO {
    private String name;
    private String address;
    private String tel;
    private String latitude;
    private String longitude;
    private String imagePath;
    private String desc;
}
