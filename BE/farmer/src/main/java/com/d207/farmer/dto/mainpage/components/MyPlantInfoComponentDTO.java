package com.d207.farmer.dto.mainpage.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPlantInfoComponentDTO {
    private Boolean isUsable;
    private Long plantId;
    private String plantName;
}
