package com.d207.farmer.dto.community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class communityAllTagsResponseDTO {

    private Long id;

    private String tagName;

    private boolean isSelected;


    public communityAllTagsResponseDTO(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
        this.isSelected = false;
    }

    public void setSelected(boolean booleanvalue) {
        this.isSelected = booleanvalue;
    }
}
