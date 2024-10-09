from fastapi import APIRouter, HTTPException, Depends
from sqlalchemy.orm import Session
from typing import List, Dict
from .database import get_db
from .schemas import CropRecommendationRequest
from .service import CropRecommendationService

router = APIRouter()

# 서비스 객체 생성
def get_crop_recommendation_service(db: Session = Depends(get_db)):
    return CropRecommendationService(db)

# 작물 추천 API
@router.post("/plant/recommend", response_model=List[Dict[str, int]])
async def recommend_crops(request: CropRecommendationRequest, service: CropRecommendationService = Depends(get_crop_recommendation_service)):
    try:
        # 객체의 속성으로 접근하도록 수정
        address = {
            "sido": request.address.sido,
            "sigungu": request.address.sigungu,
            "bname1": request.address.bname1,
            "bname2": request.address.bname2,
            "bunji": request.address.bunji,
        }
        # 작물 추천 결과 반환
        recommendations = service.get_crop_recommendations(address)
        return recommendations
    except HTTPException as e:
        # HTTPException 발생 시 그대로 반환
        raise e
    except Exception as e:
        # 그 외의 예외 발생 시 내부 서버 오류 반환
        raise HTTPException(status_code=500, detail=f"내부 서버 오류: {str(e)}")