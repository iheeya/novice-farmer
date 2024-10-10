from fastapi import APIRouter, HTTPException, Depends
from sqlalchemy.orm import Session
from typing import List, Dict
# from .database import get_db
from setting.mysql import session_local
from .schemas import CropRecommendationRequest, PlaceRecommendationRequest
from .service import CropRecommendationService, IndoorCropRecommendationService, PlaceRecommendationService

router = APIRouter()

fast_api : Session = session_local['fast_api']()
farmer : Session = session_local['farmer']()

# 서비스 객체 생성
def get_crop_recommendation_service(fast_api):
    return CropRecommendationService(fast_api)

def get_indoor_crop_recommendation_service(fast_api):
    return IndoorCropRecommendationService(fast_api)

def get_place_recommendation_service(fast_api):
    return PlaceRecommendationService(fast_api)

# 작물 추천 API
@router.post("/plant/recommend", response_model=List[Dict[str, int]])
async def recommend_crops(
    request: CropRecommendationRequest,
    service: CropRecommendationService = Depends(get_crop_recommendation_service),
    indoor_service: IndoorCropRecommendationService = Depends(get_indoor_crop_recommendation_service)
):
    try:
        place_id = request.place.placeId

        # 실내 텃밭(placeId == 1)인 경우 콜드 스타트 추천 반환
        if place_id == 1:
            return indoor_service.get_cold_start_recommendations()
        
        # 실외 텃밭(2, 3, 4)에 대한 작물 추천
        address = {
            "sido": request.address.sido,
            "sigungu": request.address.sigungu,
            "bname1": request.address.bname1,
            "bname2": request.address.bname2,
            "bunji": request.address.bunji,
        }
        recommendations = service.get_crop_recommendations(address)
        return recommendations
    except HTTPException as e:
        # HTTPException 발생 시 그대로 반환
        raise e
    except Exception as e:
        # 그 외의 예외 발생 시 내부 서버 오류 반환
        raise HTTPException(status_code=500, detail=f"내부 서버 오류: {str(e)}")

@router.post("/place/recommend", response_model=List[Dict[str, int]])
async def recommend_place_for_plant(request: PlaceRecommendationRequest, service: PlaceRecommendationService = Depends(get_place_recommendation_service)):
    try:
        # 선택한 작물에 따른 추천 결과 반환
        recommendations = service.recommend_place_for_plant(request.plantId, request.plantName)
        return recommendations
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"내부 서버 오류: {str(e)}")