package com.d207.farmer.dto.survey;

import com.d207.farmer.dto.utils.OnlyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class SurveyRegisterRequestDTO {
    private List<OnlyId> plant;
    private List<OnlyId> place;


}


