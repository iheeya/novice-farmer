package com.d207.farmer.dto.mongo.place;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImagesAndContentsResponseDTO {
    private List<String> images;
    private List<ContentDTO> contents;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContentDTO {
        private String title;
        private String content;
    }
}
