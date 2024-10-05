# Column: 컬럼 설정, Integer: 정수 타입 지정, TypeDecorator: 커스텀 타입 정의 지원
from sqlalchemy import Column, Dialect, Integer, String, Boolean, Date, DateTime, TypeDecorator, ForeignKey, Float, CheckConstraint, SmallInteger
from sqlalchemy.ext.declarative import declarative_base
# MySQL의 BIT 타입 사용을 위해 dialect.mysql에서 가져온다.
from sqlalchemy.dialects.mysql import BIT
from sqlalchemy.orm import relationship

Base = declarative_base()
    
class UserFarm(Base):
    __tablename__ = 'farm'
    id = Column('farm_id', Integer, primary_key=True)
    plant_id = Column(Integer, ForeignKey('palnt.palnt_id'))
    place_id = Column(Integer, ForeignKey('user_place_id'))
    user_id = Column(Integer, ForeignKey('user.user_id'))
    farm_DDs = Column('farm_degree_day', Integer)
    is_completeed = Column('farm_is_completed', Boolean)
    is_deleted = Column('farm_is_deleted', Boolean)
    is_harvest = Column('farm_is_harvest', Boolean)
    complete_date = Column('farm_complete_date', DateTime)
    create_date =  Column('farm_create_date', DateTime)
    delete_date = Column('farm_delete_date', DateTime)
    harvest_date =  Column('farm_harvest_date', DateTime)
    start_date = Column('farm_seed_date', DateTime)
    memo = Column('farm_memo', String(255))
    crop_name = Column('farm_plant_name', String(255))


# TINYINT TypeDecorator 정의
class TinyInteger(TypeDecorator):
    impl = SmallInteger

    def process_bind_param(self, value, dialect):
        if value is not None:
            return int(value)

    def process_result_value(self, value, dialect):
        return int(value)

# 예보구역 데이터 모델
class WeatherArea(Base):
    __tablename__ = 'weather_area'

    reg_id = Column(String(12), primary_key=True, index=True)
    reg_name = Column(String(12), unique=True)

# 행정구역 데이터 모델
class AdmDistrict(Base):
    __tablename__ = 'adm_district'

    adm_id = Column(String(12), primary_key=True, index=True)
    adm_head = Column(String(12))
    adm_middle = Column(String(12))
    adm_tail = Column(String(12))
    x_grid = Column(TinyInteger, CheckConstraint('x_grid BETWEEN 0 AND 255'))  # TINYINT 범위 지정
    y_grid = Column(TinyInteger, CheckConstraint('y_grid BETWEEN 0 AND 255'))  # TINYINT 범위 지정
    lat = Column(Float)
    lon = Column(Float)

# AWS 지점 데이터 모델
class AwsStn(Base):
    __tablename__ = 'aws_stn'

    stn_id = Column(SmallInteger, primary_key=True)
    lat = Column(Float)
    lon = Column(Float)
    reg_id = Column(String(12), ForeignKey('weather_area.reg_id'), nullable=False)
    law_id = Column(String(12), ForeignKey('adm_district.adm_id'), nullable=False)

# 기상 정보 데이터 모델
class WeatherVal(Base):
    __tablename__ = 'weather_val'

    stn_id = Column(SmallInteger, ForeignKey('aws_stn.stn_id'), primary_key=True)
    rn_day = Column(Float, default=0)
    ta_max = Column(Float)
    ta_min = Column(Float)

# 작물 기본 정보 모델
class CropBase(Base):
    __tablename__ = 'crop_base'

    crop_id = Column(SmallInteger, primary_key=True, autoincrement=True)
    crop_name = Column(String(255), nullable=False)
    crop_plant_season = Column(String(50))

# 비료 정보 모델
class CropFertilizer(Base):
    __tablename__ = 'crop_fertilizer'

    fertilizer_id = Column(SmallInteger, primary_key=True)
    fertilizer_type = Column(String(255))
    fertilizer_name = Column(String(255))

# 작물별 비료 주기 모델
class CropFertilizerPeriod(Base):
    __tablename__ = 'crop_fertilizer_period'

    crop_id = Column(TinyInteger, ForeignKey('crop_base.crop_id'), primary_key=True)
    fertilizer_step1 = Column(Boolean)
    fertilizer_step2 = Column(Boolean)
    fertilizer_step3 = Column(Boolean)
    fertilizer_step4 = Column(Boolean)
    fertilizer_step1_id = Column(SmallInteger, ForeignKey('crop_fertilizer.fertilizer_id'))
    fertilizer_step2_id = Column(SmallInteger, ForeignKey('crop_fertilizer.fertilizer_id'))
    fertilizer_step3_id = Column(SmallInteger, ForeignKey('crop_fertilizer.fertilizer_id'))
    fertilizer_step4_id = Column(SmallInteger, ForeignKey('crop_fertilizer.fertilizer_id'))

# 작물별 관수 주기 모델
class CropWaterPeriod(Base):
    __tablename__ = 'crop_water_period'

    crop_id = Column(TinyInteger, ForeignKey('crop_base.crop_id'), primary_key=True)
    watering_step1 = Column(Boolean)
    watering_step2 = Column(Boolean)
    watering_step3 = Column(Boolean)
    watering_step4 = Column(Boolean)

# 작물 생육 온도 모델
class GrowthTemp(Base):
    __tablename__ = 'growth_temp'

    crop_id = Column(SmallInteger, ForeignKey('crop_base.crop_id'), primary_key=True)
    growth_high_temp = Column(Float)
    growth_low_temp = Column(Float)


# 기상특보 예보구역
class SpecialWeather(Base):
    __tablename__ = 'special_weather'

    stn_id = Column(String(12), primary_key=True)
    wrn_id = Column(String(12), ForeignKey('current_special_weather.wrn_id'), nullable=False)
    reg_id = Column(String(12), nullable=False)

# 기상특보 현황 및 타입
class CurrentSpecialWeather(Base):
    __tablename__ = 'current_special_weather'

    wrn_id = Column(String(12), primary_key=True)
    wrn_type = Column(String(12), nullable=False)

    # 관계 설정
    special_weather = relationship("SpecialWeather", backref="current_special_weather")

# 작물별 DD값 Treshold
class CropThreshold(Base):
    __tablename__ = 'crop_threshold'

    crop_id = Column(TinyInteger, primary_key=True) 
    step1_threshold = Column(Integer, nullable=False)
    step2_threshold = Column(Integer, nullable=False)
    step3_threshold = Column(Integer, nullable=False)
    step4_threshold = Column(Integer, nullable=False)

    # 외래 키 설정, crop_base 테이블의 crop_id 참조
    crop_id = Column(SmallInteger, ForeignKey('crop_base.crop_id', ondelete='CASCADE'))