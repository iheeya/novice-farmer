# 모든 스키마 클래스는 Pydantic의 BaseModel을 상속받아 정의된다. 데이터 검증, 직렬화, 역직렬화 기능 제공.
from pydantic import BaseModel
# Python 타입 힌팅 도구로 필드가 선택적일 때 사용. Ex) Optional[str]은 이 필드가 str or None임을 의미.
from typing import Optional
from datetime import datetime

# 작물 기본 정보 스키마
class CropBaseSchema(BaseModel):
    crop_id: Optional[int]
    crop_name: str
    crop_plant_season: Optional[str]
    is_leaves: Optional[int]

    class Config:
        from_attributes = True

# 비료 정보 스키마
class CropFertilizerSchema(BaseModel):
    fertilizer_id: Optional[int]
    fertilizer_type: Optional[str]
    fertilizer_name: Optional[str]

    class Config:
        from_attributes = True

# 작물별 비료 주기 스키마
class CropFertilizerPeriodSchema(BaseModel):
    crop_id: Optional[int]
    fertilizer_step1: Optional[bool]
    fertilizer_step2: Optional[bool]
    fertilizer_step3: Optional[bool]
    fertilizer_step4: Optional[bool]
    fertilizer_step1_id: Optional[int]
    fertilizer_step2_id: Optional[int]
    fertilizer_step3_id: Optional[int]
    fertilizer_step4_id: Optional[int]

    class Config:
        from_attributes = True

# 작물별 관수 주기 스키마
class CropWaterPeriodSchema(BaseModel):
    crop_id: Optional[int]
    watering_step1: Optional[bool]
    watering_step2: Optional[bool]
    watering_step3: Optional[bool]
    watering_step4: Optional[bool]

    class Config:
        from_attributes = True

# 작물 생육 온도 스키마
class GrowthTempSchema(BaseModel):
    crop_id: Optional[int]
    growth_high_temp: Optional[float]
    growth_low_temp: Optional[float]

    class Config:
        from_attributes = True

# 작물별 DD값 Threshold 스키마
class CropThresholdSchema(BaseModel):
    crop_id: Optional[int]
    step2_threshold: int
    step3_threshold: int
    step4_threshold: int

    class Config:
        from_attributes = True