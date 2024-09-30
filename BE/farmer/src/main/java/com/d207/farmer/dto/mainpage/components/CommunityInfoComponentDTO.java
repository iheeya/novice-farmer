package com.d207.farmer.dto.mainpage.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
// @NoArgsConstructor
public class CommunityInfoComponentDTO {
    private Boolean isUsable;
    private String tagName;
    private List<CommunitySortedByPopularity> CommunitySortedByPopularities;
    private List<CommunitySortedByRecent> CommunitySortedByRecents;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommunitySortedByPopularity {
        private Long communityId;
        private String title;
        private String content;
        private String imagePath;
        private Long heartCount;
        private Long commentCount;
        private String writer;
        private String writerImagePath;
        private LocalDate registerDate;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommunitySortedByRecent {
        private Long communityId;
        private String title;
        private String content;
        private String imagePath;
        private Long heartCount;
        private Long commentCount;
        private String writer;
        private String writerImagePath;
        private LocalDate registerDate;
    }

    public CommunityInfoComponentDTO() {
        CommunitySortedByPopularities = new ArrayList<>();
        CommunitySortedByPopularities.add(new CommunitySortedByPopularity());
        CommunitySortedByPopularities.add(new CommunitySortedByPopularity());

        CommunitySortedByRecents = new ArrayList<>();
        CommunitySortedByRecents.add(new CommunitySortedByRecent());
        CommunitySortedByRecents.add(new CommunitySortedByRecent());
    }
}
