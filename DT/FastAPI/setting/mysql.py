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
MYSQL_FARMER_URL = f'mysql+pymysql://{db_user}:{db_pwd}@{db_host}:{db_port}/farmer'

DATABASES = {
    'fast_api': MYSQL_FASTAPI_URL,
    'farmer': MYSQL_FARMER_URL,
}

engines = {schema: create_engine(url) for schema, url in DATABASES.items()}

session_local = {
    schema: sessionmaker(autocommit=False, autoflush=False, bind=engine) for schema, engine in engines.items()
}