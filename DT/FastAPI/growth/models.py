# Column: 컬럼 설정, Integer: 정수 타입 지정, TypeDecorator: 커스텀 타입 정의 지원
from typing import Any
from sqlalchemy import Column, Dialect, Integer, String, Boolean, Date, DateTime, TypeDecorator, ForeignKey
from sqlalchemy.ext.declarative import declarative_base
# MySQL의 BIT 타입 사용을 위해 dialect.mysql에서 가져온다.
from sqlalchemy.dialects.mysql import BIT

Base = declarative_base()

# 커스텀 BIT 타입을 정의한다.
class BooleanBit(TypeDecorator):
    # impl 속성은 이 커스텀 타입이 내부적으로 어떤 데이터베이스 타입으로 변환될 지 정의한다.
    impl = BIT
    
    # SQLAlchemy가 데이터를 DB에 저장하기 전에 호출된다. value는 Python에서 전달된 값, dialect는 DB의 종류.
    def process_bind_param(self, value, dialect):
        # Python에서 준 value 값이 True면 1 반환, False면 0 반환.
        return 1 if value else 0
    
    # # SQLAlchemy가 DB에서 값을 가져올 때 호출된다.
    # def process_result_value(self, value, dialect):
    #     # value를 boolean type으로 변환하여 True, False로 반환한다(사실 없어도 상관은 없다.)
    #     return bool(value)
    
class UserFarm(Base):
    __tablename__ = 'farm'
    id = Column('farm_id', Integer, primary_key=True)
    plant_id = Column(Integer, ForeignKey('palnt.palnt_id'))
    place_id = Column(Integer, ForeignKey('user_place_id'))
    user_id = Column(Integer, ForeignKey('user.user_id'))
    farm_DDs = Column('farm_degree_day', Integer)
    is_completeed = Column('farm_is_completed', BooleanBit)
    is_deleted = Column('farm_is_deleted', BooleanBit)
    is_harvest = Column('farm_is_harvest', BooleanBit)
    complete_date = Column('farm_complete_date', DateTime)
    create_date =  Column('farm_create_date', DateTime)
    delete_date = Column('farm_delete_date', DateTime)
    harvest_date =  Column('farm_harvest_date', DateTime)
    start_date = Column('farm_seed_date', DateTime)
    memo = Column('farm_memo', String(255))
    crop_name = Column('farm_plant_name', String(255))