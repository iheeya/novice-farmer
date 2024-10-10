from sqlalchemy import Column, Dialect, Integer, String, Boolean, Date, DateTime, TypeDecorator, ForeignKey, Float, CheckConstraint, SmallInteger, BigInteger, Enum
from sqlalchemy.orm import relationship
from setting.models import Farm
from sqlalchemy.ext.declarative import declarative_base
import enum

Base = declarative_base()

class FarmTodoType(enum.Enum):
    FERTILIZERING = 'FERTILIZERING'
    HARVESTING = 'HARVESTING'
    NATURE = 'NATURE' # 기상 특보
    PANDEMIC = 'PANDEMIC'
    WATERING = 'WATERING'

class FarmTodo(Base):
    __tablename__ = "farm_todo"

    farm_todo_id = Column(BigInteger, primary_key=True, autoincrement=True)
    farm_todo_title = Column(String(255), nullable=True)
    farm_todo_is_completed = Column(Boolean, nullable=True) 
    farm_id = Column(BigInteger, ForeignKey('farm.farm_id'), nullable=True)
    farm_todo_complete_date = Column(DateTime(6), nullable=True)
    farm_todo_date = Column(DateTime(6), nullable=True)
    farm_todo_type = Column(Enum(FarmTodoType), nullable=True)
    