from sqlalchemy import Column, Dialect, Integer, String, Boolean, Date, DateTime, TypeDecorator, ForeignKey, Float, CheckConstraint, SmallInteger, BigInteger, Enum
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base
import enum
# from todo.models import FarmTodo

Base = declarative_base()

# 유저 정보 테이블
class UserPlace(Base):
    __tablename__ = 'user_place'
    
    user_place_id = Column(BigInteger, primary_key=True, index=True)
    user_place_sido = Column(String(255), nullable=True)
    user_place_sigugun = Column(String(255), nullable=True)

# 농장 테이블에 대한 모델 정의
class Farm(Base):
    __tablename__ = 'farm'
    
    farm_id = Column(BigInteger, primary_key=True, index=True)
    farm_degree_day = Column(Integer, nullable=True)
    plant_id = Column(BigInteger, ForeignKey("plant.plant_id"), nullable=True)
    user_place_id = Column(BigInteger, ForeignKey("user_place.user_place_id"), nullable=True)
    
    # todos = relationship("FarmTodo", back_populates="farm")

class Plant(Base):
    __tablename__ = 'plant'
    
    plant_id = Column(BigInteger, primary_key=True)
    plant_name = Column(String(255), nullable=True)
    
class User(Base):
    __tablename__ = 'user'

    user_id = Column(BigInteger, primary_key=True)

class Place(Base):
    __tablename__ = 'place'
    
    place_id = Column(BigInteger, primary_key=True)
    place_name = Column(String(255), nullable=True)