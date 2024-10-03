import os
from motor.motor_asyncio import AsyncIOMotorClient
from dotenv import load_dotenv

load_dotenv()

db_user = os.getenv('MONGO_USER')
db_pwd = os.getenv('MONGO_PASSWORD')
db_host = os.getenv('MONGO_HOST')
db_port = os.getenv('MONGO_PORT')

MONGO_URL = f'mongodb://{db_user}:{db_pwd}@{db_host}:{db_port}/mydatabase'

mongo_client = AsyncIOMotorClient(MONGO_URL)
mongo_db = mongo_client.mydatabase