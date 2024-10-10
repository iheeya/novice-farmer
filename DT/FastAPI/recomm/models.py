from sqlalchemy import Column, Integer, String, Boolean
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class BJDCode(Base):
    __tablename__ = 'bjd_code'

    id = Column(Integer, primary_key=True, index=True)
    bjd_code = Column(String(10), unique=True, nullable=False)
    bjd_name = Column(String(255), nullable=False)
    abolition = Column(Boolean, default=False)  # abolition 열 추가 (기본값 False)

