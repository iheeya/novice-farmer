from sqlalchemy import create_engine, Column, Integer, String, Boolean
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from dotenv import load_dotenv
import os
import pandas as pd

# .env 파일에서 환경 변수 불러오기
load_dotenv()

# 환경 변수에서 데이터베이스 정보 가져오기
EC2_DB_ID = os.getenv('EC2_DB_ID')
EC2_DB_PW = os.getenv('EC2_DB_PW')
EC2_DB_HOST = os.getenv('EC2_DB_HOST')
EC2_DB_PORT = os.getenv('EC2_DB_PORT')
EC2_DB_NAME = os.getenv('MYSQL_FASTAPI_URL')
EC2_DB_NAME2 = os.getenv('MYSQL_FARMER_URL')

# 데이터베이스 URL 생성
MYSQL_FASTAPI_URL = f"mysql+pymysql://{EC2_DB_ID}:{EC2_DB_PW}@{EC2_DB_HOST}:{EC2_DB_PORT}/{EC2_DB_NAME}"
MYSQL_FARMER_URL = f"mysql+pymysql://{EC2_DB_ID}:{EC2_DB_PW}@{EC2_DB_HOST}:{EC2_DB_PORT}/{EC2_DB_NAME2}"

# SQLAlchemy 엔진 및 세션 설정
engine = create_engine(MYSQL_FASTAPI_URL)
engine2 = create_engine(MYSQL_FARMER_URL)

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()

# 의존성 주입을 위한 DB 세션 함수
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()