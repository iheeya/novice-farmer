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
    private String communityTitle;
    private List<String> communityImage;
    private String communitycontent;
    private LocalDateTime communityDate;
    private List<String> communityTag;
    private int communityHeart;
    private int communityContent;

}
