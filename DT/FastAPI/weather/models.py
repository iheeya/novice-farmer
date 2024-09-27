# Column: 컬럼 설정, Integer: 정수 타입 지정, TypeDecorator: 커스텀 타입 정의 지원
from sqlalchemy import Column, Dialect, Integer, String
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()
    
class WeatherArea(Base):
    __tablename__ = 'weather_area'
    id = Column('reg_id', String(10), primary_key=True)
    city = Column('reg_name', String(10))