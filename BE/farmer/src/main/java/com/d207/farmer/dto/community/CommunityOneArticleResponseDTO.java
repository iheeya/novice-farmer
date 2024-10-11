package com.d207.farmer.dto.community;


import com.d207.farmer.dto.common.FileDirectory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

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
        this.imagePath = (FileDirectory.USER.toString().toLowerCase()+"/" +imagePath);
        this.communityCommentcount=communityCommentcount;
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
        // communityImage가 List<String> 타입이므로, 각 이미지를 처리합니다.
        this.communityImagePath = communityImagePath.stream()
                .map(image -> FileDirectory.COMMUNITY.toString().toLowerCase() + "/" + image)
                .collect(Collectors.toList());
        this.communityTagList = communityTagList;
        this.communityHeartcount=communityHeartcount;
        this.checkIPushHeart=checkIPushHeart;
        this.year =year;
        this.month =month;
        this.day =day;
        this.checkMyarticle=checkMyarticle;

    }



}
