package com.d207.farmer.dto.survey;

import com.d207.farmer.dto.utils.OnlyId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class SurveyRegisterReRequestDTO {
    private List<OnlyId> plustags;
    private List<OnlyId> deltags;


}


