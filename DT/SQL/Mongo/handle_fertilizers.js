use farm_info
db.fertilizers.deleteMany({"type":"칼슘 비료"})
db.fertilizers.insertMany([
    {
      "type": "칼슘 비료",  // 비료 종류
      "component": "제1인산칼슘", // 조성 성분
      "images": ["제1인산칼슘_1.jpg"],
      "description": "인산 22%, 칼슘 12%로 조성된 칼슘 비료. 과일과 채소의 열과 현상을 방지하고 과육을 단단하게 함.",  // 비료 설명
      "usage_crops": ["토마토", "가지"],  // 사용되는 작물
    }
])