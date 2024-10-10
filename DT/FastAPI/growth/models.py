# Column: 컬럼 설정, Integer: 정수 타입 지정, TypeDecorator: 커스텀 타입 정의 지원
from sqlalchemy import Column, Dialect, Integer, String, Boolean, Date, DateTime, TypeDecorator, ForeignKey, Float, CheckConstraint, SmallInteger, BigInteger, Enum
from sqlalchemy.ext.declarative import declarative_base
# MySQL의 BIT 타입 사용을 위해 dialect.mysql에서 가져온다.
from sqlalchemy.dialects.mysql import BIT
from sqlalchemy.orm import relationship

Base = declarative_base()


# TINYINT TypeDecorator 정의
class TinyInteger(TypeDecorator):
    impl = SmallInteger

    def process_bind_param(self, value, dialect):
        if value is not None:
            return int(value)

    def process_result_value(self, value, dialect):
        return int(value) if value is not None else 0 

# 작물 기본 정보 모델
class CropBase(Base):
    __tablename__ = 'crop_base'

    crop_id = Column(SmallInteger, primary_key=True, autoincrement=True)
    crop_name = Column(String(255), nullable=False)
    crop_plant_season = Column(String(50))
    is_leaves = Column(TinyInteger)

# 비료 정보 모델
class CropFertilizer(Base):
    __tablename__ = 'crop_fertilizer'

    fertilizer_id = Column(SmallInteger, primary_key=True)
    fertilizer_type = Column(String(255))
    fertilizer_name = Column(String(255))

# 작물별 비료 주기 모델
class CropFertilizerPeriod(Base):
    __tablename__ = 'crop_fertilizer_period'

    crop_id = Column(SmallInteger, ForeignKey('crop_base.crop_id'), primary_key=True)
    fertilizer_step1_cycle = Column(TinyInteger, nullable=True)
    fertilizer_step2_cycle = Column(TinyInteger)
    fertilizer_step3_cycle = Column(TinyInteger)
    fertilizer_step4_cycle = Column(TinyInteger)
    fertilizer_step1_id = Column(SmallInteger, ForeignKey('crop_fertilizer.fertilizer_id'))
    fertilizer_step2_id = Column(SmallInteger, ForeignKey('crop_fertilizer.fertilizer_id'))
    fertilizer_step3_id = Column(SmallInteger, ForeignKey('crop_fertilizer.fertilizer_id'))
    fertilizer_step4_id = Column(SmallInteger, ForeignKey('crop_fertilizer.fertilizer_id'))

# 작물별 관수 주기 모델
class CropWaterPeriod(Base):
    __tablename__ = 'crop_water_period'

    crop_id = Column(SmallInteger, ForeignKey('crop_base.crop_id'), primary_key=True)
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

# 작물별 DD값 Treshold
class CropThreshold(Base):
    __tablename__ = 'crop_threshold'

    crop_id = Column(SmallInteger, ForeignKey('crop_base.crop_id', ondelete='CASCADE'), primary_key=True)
    step2_threshold = Column(Integer, nullable=False)
    step3_threshold = Column(Integer, nullable=False)
    step4_threshold = Column(Integer, nullable=False)

    