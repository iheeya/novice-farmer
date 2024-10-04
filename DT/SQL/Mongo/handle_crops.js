use farm_info
db.crops.deleteOne({"name":"토마토"})
db.crops.insertMany([
    {
      "name": "토마토",  // 작물 이름
      "images": ["토마토_1.jpg"],
      "definition": "과일형 채소로, 다양한 요리에 활용되며 열매가 많이 달리는 특징이 있음.",  // 작물 설명
      "best_season": ["3", "4", "5"],  // 키우기 좋은 계절
      "optimal_temperature": { "min": "18", "max": "30" },  // 잘 자라는 온도 (최소, 최대)
      "planting": { // 파종 방법
        "method": "모종 심기",
        "description": "모종을 햇빛이 잘 드는 곳에서 심어야 하며, 물 빠짐이 좋게 해야 함."
      },
      "fertilizer_info": { // 적절한 비료. 단계는 임의로 분류한 것이므로 작물마다 다른 값으로 들어갈 것
        "growth_stages": [
          {
            "stage": "1단계",
            "fertilizer": { "type": "칼슘 비료", "brand": "코스트칼슘비료" }
          },
          {
            "stage": "2단계",
            "fertilizer": { "type": "질소 비료", "brand": "삼양화학 질소 비료" }
          },
          {
            "stage": "3단계",
            "fertilizer": { "type": "인산 비료", "brand": "금호 비료 인산칼륨" }
          },
          {
            "stage": "4단계",
            "fertilizer": { "type": "복합 비료", "brand": "한일비료 NK 복합비료" }
          }
        ]
      },
      "pests": [ // 작물이 걸리 수 있는 질병에 대한 정보
        {
          "name": "곰팡이병",
          "description": "잎이 누렇게 변하고 시드는 병해로, 습한 환경에서 잘 발생.",
          "prevention": "습한 환경을 피하고 주기적으로 방제 필요.",
          "treatment": "곰팡이 약제를 사용하고 감염된 식물 제거."
        }
      ],
      "additional_info": "토마토는 칼슘 부족으로 인해 배꼽썩음병에 걸리기 쉬워 주기적으로 비료를 주어야 함." // 작물을 기를 때 간단한 주의사항
    }
])