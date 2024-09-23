package com.d207.farmer.dto.mainpage.components;

import com.d207.farmer.domain.farm.TodoType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoInfoComponentDTO {
    private Boolean isUsable;
    private TodoType todoType;
    private String title;
    private String farmName;
    private String cropName;
    private String plantImagePath;
    private LocalDateTime todoDate;
    private String address;
    private String temperature;
}
