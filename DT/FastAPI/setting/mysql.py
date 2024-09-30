import os
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy import create_engine
from dotenv import load_dotenv

load_dotenv()

db_user = os.getenv('DB_USER')
db_pwd = os.getenv('DB_PASSWORD')
db_host = os.getenv('DB_HOST')
db_port = os.getenv('DB_PORT')

MYSQL_FASTAPI_URL = f'mysql+pymysql://{db_user}:{db_pwd}@{db_host}:{db_port}/fast_api'

fastapi_engine = create_engine(MYSQL_FASTAPI_URL)
session_local = sessionmaker(autocommit=False, autoflush=False, bind=fastapi_engine)
Base = declarative_base()