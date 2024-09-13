package com.d207.farmer.domain.community;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "community_image")
public class CommunityImage {

    @Id
    @GeneratedValue
    @Column(name = "community_image_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(name = "community_image_path")
    private String imagePath;
}
