package com.d207.farmer.dto.community;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommunityResponseDTO {
    private Long communityId;
    private Long userId;
    private String communityTitle;
    private String communitycontent;
    private LocalDateTime communityDate;

}
