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

