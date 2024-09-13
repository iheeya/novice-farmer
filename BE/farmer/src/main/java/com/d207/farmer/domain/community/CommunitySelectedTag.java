package com.d207.farmer.domain.community;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "community_selected_tag")
public class CommunitySelectedTag {

    @Id
    @GeneratedValue
    @Column(name = "community_selected_tag")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_tag_id")
    private CommunityTag communityTag;

}
