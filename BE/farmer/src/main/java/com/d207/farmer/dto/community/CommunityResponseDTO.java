package com.d207.farmer.dto.community;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommunityResponseDTO {
    private Long communityId;
    private Long userId;
    private String userNickname;
    private String userImagePath;
    private String communityTitle;
    private List<String> communityImage;
    private String communityContent;
    private LocalDateTime communityDate;
    private List<String> communityTag;
    private int communityHeartCount;
    private int communityContentCount;

}
