package com.d207.farmer.dto.community;

import com.d207.farmer.dto.common.FileDirectory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CommunityResponseDTO {
    private Long communityId;
    //private Long userId;
    private String userNickname;
    private String userImagePath;
    private String communityTitle;
    private List<String> communityImage;
    private String communityContent;

    private LocalDateTime communityDate;
    private String communityDateString;
    private List<String> communityTag;
    private int communityHeartCount;
    private int communityContentCount;

//    FileDirectory.USER.toString().toLowerCase()+"/" +

    //
    public CommunityResponseDTO(Long communityId, Long userId, String userNickname, String userImagePath, String communityTitle, List<String> communityImage, String communityContent,LocalDateTime communityDate, String communityDateString, List<String> communityTag, int communityHeartCount, int communityContentCount) {
        this.communityId = communityId;
        //this.userId = userId;
        this.userNickname = userNickname;
        this.userImagePath = (FileDirectory.USER.toString().toLowerCase()+"/" +userImagePath);
        this.communityTitle = communityTitle;
        // communityImage가 List<String> 타입이므로, 각 이미지를 처리합니다.
        this.communityImage = communityImage.stream()
                .map(image -> FileDirectory.COMMUNITY.toString().toLowerCase() + "/" + image)
                .collect(Collectors.toList());
        this.communityContent = communityContent;
        this.communityDateString = communityDateString;
        this.communityDate = communityDate;
        this.communityTag = communityTag;
        this.communityHeartCount = communityHeartCount;
        this.communityContentCount = communityContentCount;
    }
}
