package com.d207.farmer.dto.community;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommunityRegisterDTO {

    String communityTitle;
    String communityContent;
    List<String> imagePath;
    List<String> communityTagList;
}
