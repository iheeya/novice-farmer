# 모든 스키마 클래스는 Pydantic의 BaseModel을 상속받아 정의된다. 데이터 검증, 직렬화, 역직렬화 기능 제공.
from pydantic import BaseModel
# Python 타입 힌팅 도구로 필드가 선택적일 때 사용. Ex) Optional[str]은 이 필드가 str or None임을 의미.
from typing import Optional
from datetime import datetime
from enum import Enum

# 사용자 위치 스키마 정의
class UserPlaceSchema(BaseModel):
    user_place_id: Optional[int]
    user_place_sido: Optional[str]
    user_place_sigugun: Optional[str]

    class Config:
        from_attributes = True

# 농장 스키마 정의
class FarmSchema(BaseModel):
    farm_id: Optional[int]
    farm_degree_day: Optional[int]
    plant_id: Optional[int]
    user_place_id: Optional[int]

    class Config:
        from_attributes = True
        
class FarmUpdateSchema(BaseModel):
    farm_degree_day: Optional[int] = None

    class Config:
        from_attributes = True

# 식물 스키마 정의
class PlantSchema(BaseModel):
    plant_id: Optional[int]
    plant_name: Optional[str]

    class Config:
        from_attributes = True

# 사용자 스키마 정의
class UserSchema(BaseModel):
    user_id: Optional[int]

    class Config:
        from_attributes = True

# FarmTodoType Enum 정의
class FarmTodoType(str, Enum):
    FERTILIZERING = 'FERTILIZERING'
    HARVESTING = 'HARVESTING'
    NATURE = 'NATURE'
    PANDEMIC = 'PANDEMIC'
    WATERING = 'WATERING'

# FarmTodo 생성 스키마 정의
class FarmTodoCreate(BaseModel):
    farm_todo_title: Optional[str] = None
    farm_todo_is_completed: Optional[bool] = None
    farm_id: Optional[int] = None
    farm_todo_complete_date: Optional[datetime] = None
    farm_todo_date: Optional[datetime] = None
    farm_todo_type: Optional[FarmTodoType] = None

# FarmTodo 조회 스키마 정의
class FarmTodo(BaseModel):
    farm_todo_id: int
    farm_todo_title: Optional[str] = None
    farm_todo_is_completed: Optional[bool] = None
    farm_id: Optional[int] = None
    farm_todo_complete_date: Optional[datetime] = None
    farm_todo_date: Optional[datetime] = None
    farm_todo_type: Optional[FarmTodoType] = None

    class Config:
        from_attributes = True