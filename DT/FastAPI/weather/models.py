# Column: 컬럼 설정, Integer: 정수 타입 지정, TypeDecorator: 커스텀 타입 정의 지원
from sqlalchemy import Column, Dialect, Integer, String, Float, SmallInteger, ForeignKey
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()
    
class WeatherArea(Base):
    __tablename__ = 'weather_area'
    reg_id = Column(String(12), primary_key=True)
    reg_name = Column(String(12), nullable=False)
    
class AdministrativeDistrict(Base):
    __tablename__ = 'adm_district'
    adm_id = Column(String(12), primary_key=True)
    adm_head = Column(String(12))
    adm_middle = Column(String(12))
    adm_tail =  Column(String(12))
    x_grid = Column(SmallInteger)
    y_grid = Column(SmallInteger)
    lat = Column(Float)
    lon = Column(Float)
    
class AwsStn(Base):
    __tablename__ = 'aws_stn'
    stn_id = Column(SmallInteger, primary_key=True)
    lat = Column(Float)
    lon = Column(Float)
    stn_sp = Column(String(12))
    stn_name = Column(String(12))
    reg_id = Column(String(12), nullable=False)
    law_id = Column(String(12), nullable=False)
    
class TodayWeatherBase(Base):
    __tablename__ = 'today_weather_base'
    stn_id = Column(SmallInteger, ForeignKey('aws_stn.stn_id'), primary_key=True)
    rn_day = Column(Float, default=0)
    ta_max = Column(Float)
    ta_min = Column(Float)
    aws_stn = relationship('AwsStn', back_populates='today_weather_base')
    
class TodayWeatherVal(Base):
    __tablename__ = 'today_weather_val'
    stn_id = Column(SmallInteger, ForeignKey('today_weather_base.stn_id', ondelete="CASCADE"), primary_key=True)
    rn_day = Column(Float, default=0)
    ta_max = Column(Float)
    ta_min = Column(Float)
    today_weather_base = relationship('TodayWeatherBase', back_populates='today_weather_val')