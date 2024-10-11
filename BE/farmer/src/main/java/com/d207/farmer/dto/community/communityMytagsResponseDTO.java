package com.d207.farmer.dto.community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor

public class communityMytagsResponseDTO {

    List<communityAllTagsResponseDTO> mytag;

    List<communityAllTagsResponseDTO> popularTag;

    public communityMytagsResponseDTO (List<communityAllTagsResponseDTO> mytag, List<communityAllTagsResponseDTO> popularTag){
        this.mytag = mytag;
        this.popularTag = popularTag;
    }
}
