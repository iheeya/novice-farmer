# Column: 컬럼 설정, Integer: 정수 타입 지정, TypeDecorator: 커스텀 타입 정의 지원
from sqlalchemy import Column, Integer, String, Float, SmallInteger, ForeignKey, TypeDecorator, CheckConstraint, Boolean, BigInteger, DateTime, Enum
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

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


# 유저 정보 테이블
class UserPlace(Base):
    __tablename__ = 'user_place'
    
    user_place_id = Column(BigInteger, primary_key=True, index=True)
    place_id = Column(BigInteger, ForeignKey("place.place_id"), nullable=True)
    user_id = Column(BigInteger, ForeignKey("user.user_id"), nullable=True)
    user_place_bname1 = Column(String(255), nullable=True)
    user_place_bname2 = Column(String(255), nullable=True)
    user_place_bunji = Column(String(255), nullable=True)
    user_place_jibun = Column(String(255), nullable=True)
    user_place_latitude = Column(String(255), nullable=True)
    user_place_longitude = Column(String(255), nullable=True)
    user_place_name = Column(String(255), nullable=True)
    user_place_sido = Column(String(255), nullable=True)
    user_place_sigugun = Column(String(255), nullable=True)
    zonecode = Column(String(255), nullable=True)

    # 관계 설정
    user = relationship("User", back_populates="user_places")
    place = relationship("Place", back_populates="user_places")


# 농장 테이블에 대한 모델 정의
class Farm(Base):
    __tablename__ = 'farm'
    
    farm_id = Column(BigInteger, primary_key=True, index=True)
    farm_degree_day = Column(Integer, nullable=True)
    farm_is_completed = Column(Boolean, nullable=True)
    farm_is_deleted = Column(Boolean, nullable=True)
    farm_is_harvest = Column(Boolean, nullable=True)
    farm_complete_date = Column(DateTime(6), nullable=True)
    farm_create_date = Column(DateTime(6), nullable=True)
    farm_delete_date = Column(DateTime(6), nullable=True)
    farm_harvest_date = Column(DateTime(6), nullable=True)
    farm_seed_date = Column(DateTime(6), nullable=True)
    plant_id = Column(BigInteger, ForeignKey("plant.plant_id"), nullable=True)
    user_id = Column(BigInteger, ForeignKey("user.user_id"), nullable=True)
    user_place_id = Column(BigInteger, ForeignKey("user_place.user_place_id"), nullable=True)
    farm_memo = Column(String(255), nullable=True)
    farm_plant_name = Column(String(255), nullable=True)

    # 관계 설정
    user_place = relationship("UserPlace", back_populates="farms")
    plant = relationship("Plant", back_populates="farms")
    user = relationship("User", back_populates="farms")


# 농장별 할 일 테이블
class FarmTodo(Base):
    __tablename__ = 'farm_todo'
    
    farm_todo_id = Column(BigInteger, primary_key=True, index=True)
    farm_todo_is_completed = Column(Boolean, nullable=True)
    farm_id = Column(BigInteger, ForeignKey("farm.farm_id"), nullable=True)
    farm_todo_complete_date = Column(DateTime(6), nullable=True)
    farm_todo_date = Column(DateTime(6), nullable=True)
    farm_tddo_type = Column(String(255), nullable=True)  # 오타 수정 필요시 여기 확인
    farm_todo_type = Column(Enum('FERTILIZERING', 'HARVESTING', 'NATURE', 'PANDEMIC', 'WATERING'), nullable=True)

    # 관계 설정
    farm = relationship("Farm", back_populates="farm_todos")