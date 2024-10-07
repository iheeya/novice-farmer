# 모든 스키마 클래스는 Pydantic의 BaseModel을 상속받아 정의된다. 데이터 검증, 직렬화, 역직렬화 기능 제공.
from pydantic import BaseModel
# Python 타입 힌팅 도구로 필드가 선택적일 때 사용. Ex) Optional[str]은 이 필드가 str or None임을 의미.
from typing import Optional
from datetime import datetime
from enum import Enum

# class Config의 경우 SQLAlchemy의 모델 객체를 Pydantic 스키마로 쉽게 변환할 수 있게 해서 FastAPI가 자동으로 데이터 직렬화를 해준다.    

# WeatherArea 스키마
class WeatherAreaSchema(BaseModel):
    reg_id: str
    reg_name: Optional[str]

    class Config:
        from_attributes = True


# AdmDistrict 스키마
class AdmDistrictSchema(BaseModel):
    adm_id: str
    adm_head: Optional[str]
    adm_middle: Optional[str]
    adm_tail: Optional[str]
    x_grid: Optional[int]
    y_grid: Optional[int]
    lat: Optional[float]
    lon: Optional[float]

    class Config:
        from_attributes = True


# AwsStn 스키마
class AwsStnSchema(BaseModel):
    stn_id: int
    lat: Optional[float]
    lon: Optional[float]
    reg_id: str
    law_id: str

    class Config:
        from_attributes = True


# WeatherVal 스키마
class WeatherValSchema(BaseModel):
    stn_id: int
    rn_day: Optional[float] = 0
    ta_max: Optional[float]
    ta_min: Optional[float]

    class Config:
        from_attributes = True


# CurrentSpecialWeather 스키마
class CurrentSpecialWeatherSchema(BaseModel):
    wrn_id: str
    wrn_type: str

    class Config:
        from_attributes = True


# SpecialWeather 스키마
class SpecialWeatherSchema(BaseModel):
    stn_id: str
    wrn_id: str
    reg_id: str

    class Config:
        from_attributes = True


# UserPlace 스키마
class UserPlaceSchema(BaseModel):
    user_place_id: int
    place_id: Optional[int]
    user_id: Optional[int]
    user_place_bname1: Optional[str]
    user_place_bname2: Optional[str]
    user_place_bunji: Optional[str]
    user_place_jibun: Optional[str]
    user_place_latitude: Optional[str]
    user_place_longitude: Optional[str]
    user_place_name: Optional[str]
    user_place_sido: Optional[str]
    user_place_sigugun: Optional[str]
    zonecode: Optional[str]

    class Config:
        from_attributes = True


# Farm 스키마
class FarmSchema(BaseModel):
    farm_id: int
    farm_degree_day: Optional[int]
    farm_is_completed: Optional[bool]
    farm_is_deleted: Optional[bool]
    farm_is_harvest: Optional[bool]
    farm_complete_date: Optional[datetime]
    farm_create_date: Optional[datetime]
    farm_delete_date: Optional[datetime]
    farm_harvest_date: Optional[datetime]
    farm_seed_date: Optional[datetime]
    farm_memo: Optional[str]
    farm_plant_name: Optional[str]
    user_id: Optional[int]
    user_place_id: Optional[int]
    plant_id: Optional[int]

    class Config:
        from_attributes = True


# FarmTodo 스키마
class FarmTodoType(str, Enum):
    FERTILIZERING = 'FERTILIZERING'
    HARVESTING = 'HARVESTING'
    NATURE = 'NATURE'
    PANDEMIC = 'PANDEMIC'
    WATERING = 'WATERING'

class FarmTodoSchema(BaseModel):
    farm_todo_id: int
    farm_todo_is_completed: Optional[bool]
    farm_todo_complete_date: Optional[datetime]
    farm_todo_date: Optional[datetime]
    farm_todo_type: Optional[FarmTodoType]
    farm_id: Optional[int]

    class Config:
        from_attributes = True