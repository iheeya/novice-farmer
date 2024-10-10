package com.d207.farmer.domain.community;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "community_selected_tag")
@NoArgsConstructor
public class CommunitySelectedTag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "community_selected_tag")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_tag_id")
    private CommunityTag communityTag;

    public CommunitySelectedTag(Community community, CommunityTag communityTag) {
        this.community = community;
        this.communityTag = communityTag;
    }

}
