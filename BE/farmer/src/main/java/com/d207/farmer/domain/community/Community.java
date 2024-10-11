package com.d207.farmer.domain.community;


import com.d207.farmer.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@Table(name = "community")
@NoArgsConstructor // 기본 생성자 추가
public class Community {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "community_title")
    private String title;

    @Column(name = "community_content")
    private String content;

    @Column(name = "community_date")
    private LocalDateTime writeDate;

    @Column(name = "community_check_delete")
    private boolean checkDelete;


    public Community(User user, String communityTitle, String communityContent) {

        this.user = user;
        this.title = communityTitle;
        this.content = communityContent;
        this.writeDate = LocalDateTime.now();
        this.checkDelete = false;
    }

    public Community(User user, String communityTitle, String communityContent, LocalDateTime writeDate) {

        this.user = user;
        this.title = communityTitle;
        this.content = communityContent;
        this.writeDate = writeDate;
        this.checkDelete = false;
    }
}
