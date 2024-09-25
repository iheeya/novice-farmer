package com.d207.farmer.domain.community;


import com.d207.farmer.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "community_heart")
@NoArgsConstructor
public class CommunityHeart {

    @Id
    @GeneratedValue
    @Column(name = "community_favorite_tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "community_heart_date")
    private LocalDateTime writeDate;


    public CommunityHeart(Community community, User user) {
        this.community = community;
        this.user = user;
        this.writeDate = LocalDateTime.now();
    }

}
