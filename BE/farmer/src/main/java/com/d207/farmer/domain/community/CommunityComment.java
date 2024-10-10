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
@Table(name = "community_comment")
@NoArgsConstructor
public class CommunityComment {

    @Id
    @GeneratedValue
    @Column(name = "community_comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "community_comment_content")
    private String content;

    @Column(name = "community_comment_date")
    private LocalDateTime writeDate;



    public CommunityComment(Community community, User user, String content) {
        this.community = community;
        this.user = user;
        this.content = content;
        this.writeDate = LocalDateTime.now();
    }
}
