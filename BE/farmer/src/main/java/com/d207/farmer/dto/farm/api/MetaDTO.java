package com.d207.farmer.dto.farm.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class MetaDTO {
    private int totalCount;
    private int page;
    private int count;
}
