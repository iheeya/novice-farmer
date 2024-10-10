package com.d207.farmer.domain.weekend_farm;

import com.d207.farmer.dto.weekend_farm.WeekendFarmRegisterRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeekendFarm {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "weekend_farm_id")
    private Long id;

    @Column(name = "weekend_farm_name")
    private String name;

    @Column(name = "weekend_farm_addr")
    private String address;

    @Column(name = "weekend_farm_tel")
    private String tel;

    @Column(name = "weekend_farm_latitude")
    private String latitude;

    @Column(name = "weekend_farm_longitude")
    private String longitude;

    @Column(name = "weekend_farm_image_path")
    private String imagePath;

    @Column(name = "weekend_farm_desc")
    private String desc;

    /**
     * 비즈니스 로직
     */
    public WeekendFarm (WeekendFarmRegisterRequestDTO request) {
        this.name = request.getName();
        this.address = request.getAddress();
        this.tel = request.getTel();
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
        this.imagePath = request.getImagePath();
        this.desc = request.getDesc();
    }
}
