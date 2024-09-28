from pydantic import baseSettings
from sqlalchemy.orm import sessionmaker
from sqlalchemy import create_engine

class MySQLSettings(baseSettings):
    db_host: str
    db_port: str
    db_user: str
    db_password: str

    class Config:
     env_file = '.env'
    
mysql_settings = MySQLSettings()

MYSQL_URL = f'mysql+pymysql://{mysql_settings.db_user}:{mysql_settings.db_password}@{mysql_settings.db_host}:{mysql_settings.db_port}'

MYSQL_ENGINE = create_engine(MYSQL_URL)

session_local = sessionmaker(autocommit=False, autoflush=False, bind=MYSQL_ENGINE)