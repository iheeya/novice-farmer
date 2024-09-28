package com.d207.farmer.dto.community;


import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommunityOneArticleResponseDTO {

    private String nickname;
    private String imagePath;
    private String communityTitle;
    private String communityContent;
    private List<String> communityImagePath;
    private List<String> communityTagList;
    private Long communityHeartcount;
    private Long communityCommentcount;
    boolean checkIPushHeart;
    boolean checkMyarticle;
    private String year;
    private String month;
    private String day;


    public CommunityOneArticleResponseDTO(String nickname, String imagePath, String communityTitle, String communityContent, List<String> communityImagePath,
                                          List<String> communityTagList, Long communityHeartcount, Long communityCommentcount, boolean checkIPushHeart, boolean checkMyarticle, String year, String month, String day) {

        this.nickname = nickname;
        this.imagePath = imagePath;
        this.communityCommentcount=communityCommentcount;
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
        this.communityImagePath = communityImagePath;
        this.communityTagList = communityTagList;
        this.communityHeartcount=communityHeartcount;
        this.checkIPushHeart=checkIPushHeart;
        this.year =year;
        this.month =month;
        this.day =day;
        this.checkMyarticle=checkMyarticle;

    }



}
