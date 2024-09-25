package com.d207.farmer.dto.community;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommunityRegisterDTO {

    private String communityTitle;
    private String communityContent;
    private List<String> imagePath;
    private List<String> communityTagList;
}
