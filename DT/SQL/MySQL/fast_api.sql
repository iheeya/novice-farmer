# CREATE DATABASE fast_api DEFAULT CHARACTER SET utf8mb4;
#
USE fast_api;

-- 예보구역 데이터 가져오기
CREATE TABLE weather_area (
    `reg_id` VARCHAR(12) PRIMARY KEY COLLATE utf8mb4_unicode_ci, # 예보가 발효되는 예보구역 코드(region_id)
    `reg_name` VARCHAR(12) # 예보가 발효되는 예보구역 이름(region_name)
);

-- 행정구역 데이터 가져오기
CREATE TABLE adm_district(
    `adm_id` VARCHAR(12) PRIMARY KEY COLLATE utf8mb4_unicode_ci,
    `adm_head` VARCHAR(12) COLLATE utf8mb4_unicode_ci,
    `adm_middle` VARCHAR(12) COLLATE utf8mb4_unicode_ci,
    `adm_tail` VARCHAR(12) COLLATE utf8mb4_unicode_ci,
    `x_grid` SMALLINT,
    `y_grid` SMALLINT,
    `lat` FLOAT8,
    `lon` FLOAT8
);

-- AWS 지점 데이터 가져오기
CREATE TABLE aws_stn(
    `stn_id` SMALLINT PRIMARY KEY COLLATE utf8mb4_unicode_ci,
    `lat` FLOAT8,
    `lon` FLOAT8,
    `reg_id` VARCHAR(12) NOT NULL,
    `law_id` VARCHAR(12) NOT NULL
);

-- 기상 정보 가져오기(자정에 이전 날 것 가져옴)
CREATE TABLE weather_val(
    `stn_id` SMALLINT PRIMARY KEY COLLATE utf8mb4_unicode_ci,
    `rn_day` FLOAT4 DEFAULT 0,
    `ta_max` FLOAT4,
    `ta_min` FLOAT4,
    FOREIGN KEY (stn_id) REFERENCES aws_stn(stn_id) ON DELETE CASCADE
);

CREATE TABLE crop_base(
    `crop_id` SMALLINT PRIMARY KEY AUTO_INCREMENT,
    `crop_name` VARCHAR(255) NOT NULL,
    `crop_plant_season` VARCHAR(50), # 1, 2, 3으로 입력.
    `is_leaves` TINYINT
);

CREATE TABLE crop_fertilizer(
    `fertilizer_id` SMALLINT PRIMARY KEY,
    `fertilizer_type` VARCHAR(255),
    `fertilizer_name` VARCHAR(255)
);

CREATE TABLE crop_fertilizer_period(
    `crop_id` SMALLINT PRIMARY KEY,
    `fertilizer_step1_cycle` TINYINT NULL,
    `fertilizer_step2_cycle` TINYINT NULL,
    `fertilizer_step3_cycle` TINYINT NULL,
    `fertilizer_step4_cycle` TINYINT NULL,
    fertilizer_step1_id SMALLINT NULL,
    fertilizer_step2_id SMALLINT NULL,
    fertilizer_step3_id SMALLINT NULL,
    fertilizer_step4_id SMALLINT NULL,
    CONSTRAINT fk_crop_id FOREIGN KEY (crop_id) REFERENCES crop_base(crop_id) ON DELETE CASCADE,
    CONSTRAINT fk_fertilizer_step1_id FOREIGN KEY (fertilizer_step1_id) REFERENCES crop_fertilizer(fertilizer_id),
    CONSTRAINT fk_fertilizer_step2_id FOREIGN KEY (fertilizer_step2_id) REFERENCES crop_fertilizer(fertilizer_id),
    CONSTRAINT fk_fertilizer_step3_id FOREIGN KEY (fertilizer_step3_id) REFERENCES crop_fertilizer(fertilizer_id),
    CONSTRAINT fk_fertilizer_step4_id FOREIGN KEY (fertilizer_step4_id) REFERENCES crop_fertilizer(fertilizer_id)
);


CREATE TABLE crop_water_period(
    `crop_id` SMALLINT PRIMARY KEY,
    `watering_step1` TINYINT, # growth 1단계에서 관수 주기.
    `watering_step2` TINYINT, # growth 1단계에서 관수 주기.
    `watering_step3` TINYINT, # growth 1단계에서 관수 주기.
    `watering_step4` TINYINT, # growth 1단계에서 관수 주기.
    FOREIGN KEY crop_water_period(crop_id) REFERENCES crop_base(crop_id) ON DELETE CASCADE
);

CREATE TABLE growth_temp
(
    `crop_id`          SMALLINT PRIMARY KEY,
    `growth_high_temp` FLOAT4, # 생육 최저 기온
    `growth_low_temp`  FLOAT4, # 생육 최고 기온
    FOREIGN KEY growth_temp(crop_id) REFERENCES crop_base(crop_id) ON DELETE CASCADE
);

CREATE TABLE special_weather(
    `stn_id`    VARCHAR(12) PRIMARY KEY,
    `wrn_id`    VARCHAR(12) NOT NULL, # 특보구역 코드 - 어느지역 특보인지
    `reg_id`    VARCHAR(12) NOT NULL # 예보구역 코드
);

CREATE TABLE current_special_weather(
    `wrn_id` VARCHAR(12) PRIMARY KEY,
    `wrn_type` VARCHAR(12) NOT NULL
); # 특보현황

CREATE TABLE crop_threshold(
    `crop_id` SMALLINT PRIMARY KEY,
    `step2_threshold` INT,
    `step3_threshold` INT,
    `step4_threshold` INT NOT NULL,
    FOREIGN KEY crop_threshold(crop_id) REFERENCES crop_base(crop_id) ON DELETE CASCADE
);
