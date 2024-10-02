package com.d207.farmer.domain.community;

import com.d207.farmer.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "community_favorite_tag")
@NoArgsConstructor
@AllArgsConstructor
public class CommunityFavoriteTag {
    @Id
    @GeneratedValue
    @Column(name = "community_favorite_tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_tag_id")
    private CommunityTag communityTag;


    public CommunityFavoriteTag(User user, CommunityTag communitytag) {
        this.user = user;
        this.communityTag = communitytag;
    }
}
