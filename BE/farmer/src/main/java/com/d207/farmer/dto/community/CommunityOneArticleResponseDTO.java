package com.d207.farmer.dto.community;

import com.d207.farmer.domain.community.CommunityComment;
import com.d207.farmer.domain.community.CommunityHeart;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommunityOneArticleResponseDTO {

    private Long userId;
    private String communityTitle;
    private String communitycontent;
    private List<String> imagePath;
    private List<String> communityTagList;
    private List<CommunityHeart> communityHeartList;
    private List<CommunityComment> commnunityCommentList;
    boolean checkIPushHeart;
    private LocalDateTime communityDate;
}
