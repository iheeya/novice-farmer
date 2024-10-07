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
    x_grid = Column(SmallInteger, CheckConstraint('x_grid BETWEEN 0 AND 255'))  # TINYINT 범위 지정
    y_grid = Column(SmallInteger, CheckConstraint('y_grid BETWEEN 0 AND 255'))  # TINYINT 범위 지정
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

    stn_id = Column(SmallInteger, ForeignKey('aws_stn.stn_id', ondelete='CASCADE'), primary_key=True)
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
