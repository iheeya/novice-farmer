package com.d207.farmer.domain.farm;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.farm.register.FarmPlantRegisterDTO;
import com.d207.farmer.dto.farm.register.FarmRegisterRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Farm {

    @Id @GeneratedValue
    @Column(name = "farm_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "plant_id")
    private Plant plant;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_place_id")
    private UserPlace userPlace;

    @Column(name = "farm_plant_name")
    private String myPlantName;

    @Column(name = "farm_seed_date")
    private LocalDateTime seedDate;

    @Column(name = "farm_pred_date")
    private LocalDateTime predDate;

    @Column(name = "farm_growth_step")
    private int growthStep;

    @Column(name = "farm_is_completed")
    private Boolean isCompleted;

    @Column(name = "farm_complete_date")
    private LocalDateTime completeDate;

    @Column(name = "farm_create_date")
    private LocalDateTime createDate;

    @Column(name = "farm_is_deleted")
    private Boolean isDeleted;

    @Column(name = "farm_delete_date")
    private LocalDateTime deletedDate;

    @Column(name = "farm_is_harvest")
    private Boolean isFirstHarvest;

    @Column(name = "farm_harvest_date")
    private LocalDateTime firstHarvestDate;

    @Column(name = "farm_memo")
    private String memo;

    /**
     * 비즈니스 메서드
     */
    public Farm(User user, UserPlace userPlace, Plant plant, FarmPlantRegisterDTO request) {
        this.user = user;
        this.userPlace = userPlace;
        this.plant = plant;
        this.myPlantName = request.getMyPlantName();
        this.memo = request.getMemo();
        this.predDate = LocalDateTime.now().plusDays(plant.getGrowthDay());
        this.growthStep = 1;
        this.createDate = LocalDateTime.now();
    }

    // 작물 키우기 시작하기
    public void startGrow(int step) {
        this.seedDate = LocalDateTime.now();
        this.growthStep = step;
    }

    // 작물 삭제
    public void delete() {
        this.isDeleted = true;
        this.deletedDate = LocalDateTime.now();
    }

    // 첫 수확
    public void harvest() {
        this.isFirstHarvest = true;
        this.firstHarvestDate = LocalDateTime.now();
    }

    // 완료(첫 수확 버튼 클릭 시 렌더링)
    public void end() {
        this.isCompleted = true;
        this.completeDate = LocalDateTime.now();
    }

    public void updateName(String plantName) {
        this.myPlantName = plantName;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
}
