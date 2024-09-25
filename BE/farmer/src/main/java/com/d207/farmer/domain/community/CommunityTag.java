package com.d207.farmer.domain.community;


import com.d207.farmer.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@Table(name = "community_tag")
@NoArgsConstructor
public class CommunityTag {
    @Id
    @GeneratedValue
    @Column(name = "community_tag_id")
    private Long id;


    @Column(name = "community_tag_name")
    private String tagName;


    public CommunityTag(String communityTag) {
        this.tagName = communityTag;
    }


}
