from sqlalchemy.orm import Session
from setting.mysql import session_local
from setting.models import UserPlace, Farm
from weather.models import WeatherArea, SpecialWeather, CurrentSpecialWeather, AwsStn
from weather.schemas import WeatherAreaSchema, SpecialWeatherSchema, CurrentSpecialWeatherSchema, AwsStnSchema
from .models import FarmTodo, FarmTodoType
from .schemas import FarmTodoSchema, FarmTodoCreateSchema,FarmTodoTypeSchema
from sqlalchemy.exc import OperationalError
from datetime import datetime