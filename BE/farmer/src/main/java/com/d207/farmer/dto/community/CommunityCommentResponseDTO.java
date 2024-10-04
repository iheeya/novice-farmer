package com.d207.farmer.dto.community;

import com.d207.farmer.dto.common.FileDirectory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityCommentResponseDTO {


    private String nickname;
    private String imagePath;
    private String commentContent;
    private String writeDatestring;


    public CommunityCommentResponseDTO(String nickname, String imagePath, String commentContent, String writeDatestring) {
        this.nickname = nickname;
        this.imagePath = (FileDirectory.USER.toString().toLowerCase()+"/" +imagePath);
        this.commentContent = commentContent;
        this.writeDatestring = writeDatestring;
    }
}
