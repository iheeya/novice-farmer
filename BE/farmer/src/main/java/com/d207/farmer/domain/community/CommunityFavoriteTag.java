package com.d207.farmer.domain.community;

import com.d207.farmer.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "community_favorite_tag")
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
    private CommunityTag communitytag;
}
