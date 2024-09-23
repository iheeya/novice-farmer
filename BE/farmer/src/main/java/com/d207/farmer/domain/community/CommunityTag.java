package com.d207.farmer.domain.community;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@Table(name = "community_tag")
public class CommunityTag {
    @Id
    @GeneratedValue
    @Column(name = "community_tag_id")
    private Long id;


    @Column(name = "community_tag_name")
    private String tagName;

}
