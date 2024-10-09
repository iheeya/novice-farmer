from pydantic import BaseModel, Field
from typing import List, Optional

# 요청 모델
class CropRecommendationRequest(BaseModel):
    address: str  # 주소
    desired_crop: Optional[str] = None  # 희망 작물

# 응답 모델
class CropRecommendationResponse(BaseModel):
    crops: List[str]  # 추천 작물 목록

# 주소 정보를 검증하기 위한 Pydantic 모델 정의
class Address(BaseModel):
    sido: Optional[str] = Field(None, example="경북", description="시/도 정보")
    sigungu: Optional[str] = Field(None, example="구미시", description="시/군/구 정보")
    bname1: Optional[str] = Field(None, example="", description="시 안에 구/군이 있을 경우")
    bname2: Optional[str] = Field(None, example="임수동", description="읍/면/동/리 정보")
    bunji: Optional[str] = Field(None, example="94-1", description="번지 정보")
    jibun: Optional[str] = Field(None, example="경북 구미시 임수동 94-1", description="지번 정보")
    zonecode: Optional[str] = Field(None, example="39388", description="우편번호")
    latitude: Optional[float] = Field(None, description="위도")
    longitude: Optional[float] = Field(None, description="경도")

class Place(BaseModel):
    placeId: Optional[int] = Field(None, example=3, description="장소 ID (예: 3 - 개인텃밭)")
    placeName: Optional[str] = Field(None, example="개인텃밭", description="장소 이름")

# 전체 요청 데이터를 검증하기 위한 Pydantic 모델 정의
class CropRecommendationRequest(BaseModel):
    place: Place
    address: Address
