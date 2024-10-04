package com.d207.farmer.dto.community;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityOneModifyRequestDTO {
    private String communityTitle;
    private String communityContent;
    private List<String> communityImageSubtractPaths;

    private List<String> communityTagSubtractList;
    private List<String> communityTagAddList;
}
