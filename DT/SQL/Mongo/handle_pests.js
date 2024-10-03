use farm_info
db.pests.insertMany([
    {
      "name": "잎말이곰팡이병",  // 병해충 이름
      "description": "습한 환경에서 퍼지는 병으로 작물에 큰 피해를 줌.",  // 병해충 설명
      "affected_crops": ["토마토", "고추"],  // 영향을 받는 작물
      "symptoms": "잎이 누렇게 변하고 시들어버림.",  // 증상
      "prevention": "습한 환경을 피하고 주기적인 방제가 필요함.",  // 예방 방법
      "treatment": "곰팡이 약제를 사용하고 감염된 식물 제거.",  // 대처법
    }
])