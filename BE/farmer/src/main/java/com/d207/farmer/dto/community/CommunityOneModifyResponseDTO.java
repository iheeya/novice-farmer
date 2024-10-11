package com.d207.farmer.dto.community;

import com.d207.farmer.dto.common.FileDirectory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor

public class CommunityOneModifyResponseDTO {
    private String communityTitle;
    private String communityContent;
    private List<String> communityImagePath;
    private List<String> communityTagList;

    public CommunityOneModifyResponseDTO(String communityTitle, String communityContent, List<String> communityImagePath, List<String> communityTagList) {
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
        // communityImage가 List<String> 타입이므로, 각 이미지를 처리합니다.
        this.communityImagePath = communityImagePath.stream()
                .map(image -> FileDirectory.COMMUNITY.toString().toLowerCase() + "/" + image)
                .collect(Collectors.toList());
        this.communityTagList = communityTagList;
    }
}
