package com.d207.farmer.dto.community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityOneModifyResponseDTO {
    private String communityTitle;
    private String communityContent;
    private List<String> communityImagePath;
    private List<String> communityTagList;
}
