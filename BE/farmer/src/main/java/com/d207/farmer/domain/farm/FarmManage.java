package com.d207.farmer.domain.farm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class FarmManage {

    @Id @GeneratedValue
    @Column(name = "farm_manage_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "farm_id")
    private Farm farm;

    @Column(name = "farm_manage_date")
    private LocalDateTime manageDate;

    @Column(name = "farm_manage_image_path")
    private String imagePath;

    @Column(name = "farm_manage_type")
    @Enumerated(EnumType.STRING)
    private ManageType manageType;
}
