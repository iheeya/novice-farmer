# 모든 스키마 클래스는 Pydantic의 BaseModel을 상속받아 정의된다. 데이터 검증, 직렬화, 역직렬화 기능 제공.
from pydantic import BaseModel
# Python 타입 힌팅 도구로 필드가 선택적일 때 사용. Ex) Optional[str]은 이 필드가 str or None임을 의미.
from typing import Optional

# class Config의 경우 SQLAlchemy의 모델 객체를 Pydantic 스키마로 쉽게 변환할 수 있게 해서 FastAPI가 자동으로 데이터 직렬화를 해준다.    
# 예보구역 데이터 스키마
class WeatherAreaBase(BaseModel):
    reg_id: str
    reg_name: str

class WeatherArea(WeatherAreaBase):
    class Config:
        orm_mode = True

# 행정구역 데이터 스키마
class AdmDistrictBase(BaseModel):
    adm_id: str
    adm_head: str
    adm_middle: str
    adm_tail: str
    x_grid: Optional[int]
    y_grid: Optional[int]
    lat: Optional[float]
    lon: Optional[float]

class AdmDistrict(AdmDistrictBase):
    class Config:
        orm_mode = True

# AWS 지점 데이터 스키마
class AwsStnBase(BaseModel):
    stn_id: int
    lat: float
    lon: float
    stn_sp: str
    stn_name: str
    reg_id: str
    law_id: str

class AwsStn(AwsStnBase):
    class Config:
        orm_mode = True

# 기상 정보 데이터 스키마
class WeatherValBase(BaseModel):
    stn_id: int
    rn_day: Optional[float]
    ta_max: Optional[float]
    ta_min: Optional[float]

class WeatherVal(WeatherValBase):
    class Config:
        orm_mode = True

# 작물 기본 정보 스키마
class CropBaseBase(BaseModel):
    crop_id: int
    crop_name: str
    crop_plant_season: Optional[str]

class CropBase(CropBaseBase):
    class Config:
        orm_mode = True

# 비료 정보 스키마
class CropFertilizerBase(BaseModel):
    fertilizer_id: int
    fertilizer_type: Optional[str]
    fertilizer_name: Optional[str]

class CropFertilizer(CropFertilizerBase):
    class Config:
        orm_mode = True

# 작물별 비료 주기 스키마
class CropFertilizerPeriodBase(BaseModel):
    crop_id: int
    fertilizer_step1: Optional[str]
    fertilizer_step2: Optional[str]
    fertilizer_step3: Optional[str]
    fertilizer_step4: Optional[str]
    fertilizer_step1_id: Optional[int]
    fertilizer_step2_id: Optional[int]
    fertilizer_step3_id: Optional[int]
    fertilizer_step4_id: Optional[int]

class CropFertilizerPeriod(CropFertilizerPeriodBase):
    class Config:
        orm_mode = True

# 작물별 관수 주기 스키마
class CropWaterPeriodBase(BaseModel):
    crop_id: int
    watering_step1: Optional[int]
    watering_step2: Optional[int]
    watering_step3: Optional[int]
    watering_step4: Optional[int]

class CropWaterPeriod(CropWaterPeriodBase):
    class Config:
        orm_mode = True

# 생육 온도 스키마
class GrowthTempBase(BaseModel):
    crop_id: int
    growth_high_temp: Optional[float]
    growth_low_temp: Optional[float]

class GrowthTemp(GrowthTempBase):
    class Config:
        orm_mode = True        