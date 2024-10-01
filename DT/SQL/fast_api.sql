# CREATE DATABASE fast_api DEFAULT CHARACTER SET utf8mb4;
#
# USE fast_api

# DROP TABLE weather_area, adm_district, aws_stn, today_weather_base, today_weather_val, crop_base, crop_fertilizer, crop_info, growth_temp


# 리스트로 입력하는 데이터들은 모두 ,(comma) 단위로 구분하여 작성할 것.

CREATE TABLE weather_area (
    `reg_id` VARCHAR(12) PRIMARY KEY COLLATE utf8mb4_unicode_ci, # 예보가 발효되는 예보구역 코드(region_id)
    `reg_name` VARCHAR(12) UNIQUE # 예보가 발효되는 예보구역 이름(region_name)
);

CREATE TABLE adm_district(
    `adm_id` VARCHAR(12) PRIMARY KEY COLLATE utf8mb4_unicode_ci,
    `adm_head` VARCHAR(12) COLLATE utf8mb4_unicode_ci,
    `adm_middle` VARCHAR(12) COLLATE utf8mb4_unicode_ci,
    `adm_tail` VARCHAR(12) COLLATE utf8mb4_unicode_ci,
    `x_grid` TINYINT,
    `y_grid` TINYINT,
    `lat` FLOAT8,
    `lon` FLOAT8
);

CREATE TABLE aws_stn(
    `stn_id` SMALLINT PRIMARY KEY COLLATE utf8mb4_unicode_ci,
    `lat` FLOAT8,
    `lon` FLOAT8,
    `stn_sp` VARCHAR(12),
    `stn_name` VARCHAR(12),
    `reg_id` VARCHAR(12) NOT NULL,
    `law_id` VARCHAR(12) NOT NULL
);

CREATE TABLE today_weather_base(
    `stn_id` SMALLINT PRIMARY KEY COLLATE utf8mb4_unicode_ci,
    `lat` FLOAT8,
    `lon` FLOAT8
);

CREATE TABLE today_weather_val(
    `stn_id` SMALLINT PRIMARY KEY COLLATE utf8mb4_unicode_ci,
    `rn_day` FLOAT4 DEFAULT 0,
    `ta_max` FLOAT4,
    `ta_min` FLOAT4,
    FOREIGN KEY (stn_id) REFERENCES today_weather_base(stn_id) ON DELETE CASCADE
);

CREATE TABLE crop_base(
    `crop_id` TINYINT PRIMARY KEY AUTO_INCREMENT,
    `crop_name` VARCHAR(10) NOT NULL
);

CREATE TABLE crop_fertilizer(
    `fertilizer_id` TINYINT PRIMARY KEY,
    `fertilizer_type` VARCHAR(20),
    `fertilizer_name` VARCHAR(255),
    `fertilizer_period` TINYINT
);

CREATE TABLE crop_info(
    `crop_id` TINYINT PRIMARY KEY,
    `crop_plant_season` VARCHAR(20), # 1, 2, 3으로 입력.
    `fertilizer_season` VARCHAR(20),
    `watering_period` TINYINT,
    `fertilizer_id` TINYINT,
    FOREIGN KEY crop_info(crop_id) REFERENCES crop_base(crop_id) ON DELETE CASCADE,
    FOREIGN KEY crop_info(fertilizer_id) REFERENCES crop_fertilizer(fertilizer_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE growth_temp
(
    `crop_id`          TINYINT PRIMARY KEY,
    `growth_high_temp` FLOAT4, # 생육 최저 기온
    `growth_low_temp`  FLOAT4, # 생육 최고 기온
    FOREIGN KEY growth_temp(crop_id) REFERENCES crop_base(crop_id) ON DELETE CASCADE
);