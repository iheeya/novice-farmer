from sqlalchemy.orm import Session
from setting.mysql import session_local
from setting.models import UserPlace, Farm
from weather.models import WeatherArea, SpecialWeather, CurrentSpecialWeather, AwsStn
from weather.schemas import WeatherAreaSchema, SpecialWeatherSchema, CurrentSpecialWeatherSchema, AwsStnSchema
from .models import FarmTodo, FarmTodoType
from .schemas import FarmTodoSchema, FarmTodoCreateSchema,FarmTodoTypeSchema
from sqlalchemy.exc import OperationalError
from datetime import datetime
from sqlalchemy import or_

def update_special_weatherinfo():
    fast_api : Session = session_local['fast_api']()
    farmer : Session = session_local['farmer']()
    
    farm_data = farmer.query(Farm).all()
    
    for farm in farm_data:
        id = farm.farm_id
        plant = farm.plant_id
        user_place = farm.user_place_id
        wrn_type = ''
        sido, sigungu = farmer.query(UserPlace).with_entities(UserPlace.user_place_sido, UserPlace.user_place_sigugun).filter(UserPlace.user_place_id == user_place).first()
        sido = sido[:2]
        sigungu = sigungu[:-1]
        
        # 유저 위치 정보에 맞는 예보구역
        reg_id = fast_api.query(WeatherArea).with_entities(WeatherArea.reg_id).filter(or_(WeatherArea.reg_name.like(f'%{sido}%'), WeatherArea.reg_name.like(f'%{sigungu}%'))).first()
        print(f'reg_id: {reg_id}')
        
        if reg_id:
            # 예보구역에 맞는 관측구역
            wrn_id = fast_api.query(SpecialWeather).with_entities(SpecialWeather.stn_id).filter(SpecialWeather.reg_id == reg_id[0]).first()
            print(f'stn_id: {wrn_id}')
            
            if wrn_id:
                wrn_type = fast_api.query(SpecialWeather).with_entities(SpecialWeather.wrn_id).filter(SpecialWeather.wrn_id)
                todo_date = datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')
                add_special_weather(farmer, id, wrn_type, FarmTodoType.NATURE, todo_date, False)

def add_special_weather(db: Session, farm_id: int, title: str, type: FarmTodoType, date: datetime=None, is_completed: bool=False):
    new_todo = FarmTodo(farm_id=farm_id, title=title, farm_todo_type=type, farm_todo_date=date, farm_todo_is_completed=is_completed)
    
    try:
        db.add(new_todo)
        db.commit()
    except OperationalError as e:
        db.rollback()
        print(f'fert todo 입력 에러: {e}')
