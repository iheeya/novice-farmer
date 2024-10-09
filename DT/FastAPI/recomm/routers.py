from fastapi import APIRouter, HTTPException, Depends
from sqlalchemy.orm import Session
from .database import get_db  # DB 세션을 가져오는 함수
from .services import GardenCropRecommender  # 추천 알고리즘 클래스
from .schemas import CropRecommendationRequest, CropRecommendationResponse  # Pydantic 모델 import

# FastAPI의 APIRouter 인스턴스 생성
router = APIRouter()

@router.post("/recommendation/", response_model=CropRecommendationResponse)
async def recommend_crops(request: CropRecommendationRequest, db: Session = Depends(get_db)):
    """
    텃밭 주소와 희망 작물을 받아 추천 작물을 반환하는 API
    :param request: 텃밭 주소와 희망 작물 정보
    :param db: SQLAlchemy DB 세션
    :return: 추천 작물 목록
    """
    try:
        # 추천 알고리즘 클래스 인스턴스 생성 (주소 기반 BJD 코드 찾기 포함)
        recommender = GardenCropRecommender(address=request.address, desired_crop=request.desired_crop, db=db)

        # 내부 로직에서 기본 top_n=6, score_threshold=20 사용
        top_n = 6
        score_threshold = 20

        # 추천 작물 계산
        recommended_crops = recommender.recommend_crops(top_n=top_n, score_threshold=score_threshold)

        # 응답으로 추천 작물 리스트 반환
        return CropRecommendationResponse(crops=recommended_crops)
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"추천 생성 중 오류 발생: {str(e)}")
