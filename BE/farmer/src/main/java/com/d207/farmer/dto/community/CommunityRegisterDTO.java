package com.d207.farmer.dto.community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
public class CommunityRegisterDTO {

    private String communityTitle;
    private String communityContent;
    private List<String> communityTagList;
}
