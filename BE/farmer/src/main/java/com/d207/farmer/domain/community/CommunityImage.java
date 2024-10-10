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
@Table(name = "community_image")
@NoArgsConstructor
public class CommunityImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "community_image_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @Column(name = "community_image_path")
    private String imagePath;


    public CommunityImage(Community community, String imagePath) {
        this.community = community;
        this.imagePath = imagePath;

    }


}
