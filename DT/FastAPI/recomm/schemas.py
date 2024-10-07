from pydantic import BaseModel
from typing import List, Optional

# 요청 모델
class CropRecommendationRequest(BaseModel):
    address: str  # 주소
    desired_crop: Optional[str] = None  # 희망 작물

# 응답 모델
class CropRecommendationResponse(BaseModel):
    crops: List[str]  # 추천 작물 목록
