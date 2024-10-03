use farm_info
db.fertilizers.insertMany([
    {
      "type": "칼슘 비료",  // 비료 종류
      "component": "제1인산칼슘", // 조성 성분
      "description": "과일과 채소의 열과 현상을 방지하고 과육을 단단하게 함.",  // 비료 설명
      "usage_crops": ["토마토", "가지"],  // 사용되는 작물
      "brands": ["코스트칼슘비료", "농협 칼슘액비"]// 상표명 및 가격 정보
    }
])