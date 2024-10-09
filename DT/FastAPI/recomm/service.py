# PNU 기반 작물, 토양 평가 기준
crop_criteria = {
    "오이": {
        "심토토성": {"식양질": 15, "미사식양질": 15, "사양질": 11, "미사사양질": 11, "식질": 4, "사질": 4},
        "유효토심": {">50": 7, "25-50": 6, "0-25": 3},
        "배수등급": {"양호": 22, "약간양호": 18, "매우양호": 22, "약간불량": 15, "불량": 8, "매우불량": 8},
        "경사": {"0-2": 26, "2-7": 25, "7-15": 13, ">15": 4},
        "자갈함량": {"15-35": 10, "0-15": 10, ">35": 9},
        "지형": {"하성평탄지": 20, "곡간지": 18, "선상지": 18, "산록경사지": 13, "홍적대지": 13, "용암류대지": 13, "구릉지": 5, "산악지": 5},
        "최적지": 90,
        "적지": 85,
        "가능지": 79
    },
    "토마토": {
        "심토토성": {"미사식양질": 17, "식양질": 17, "사양질": 14, "미사사양질": 14, "식질": 13, "사질": 13},
        "유효토심": {">50": 6, "25-50": 4, "0-25": 2},
        "배수등급": {"양호": 30, "약간양호": 24, "매우양호": 30, "약간불량": 19, "불량": 9, "매우불량": 9},
        "경사": {"0-2": 16, "2-7": 13, "7-15": 11, ">15": 4},
        "자갈함량": {"0-15": 4, "15-35": 3, ">35": 1},
        "지형": {"하성평탄지": 27, "곡간지": 24, "선상지": 24, "산록경사지": 22, "홍적대지": 22, "용암류대지": 22, "구릉지": 19, "산악지": 19},
        "최적지": 90,
        "적지": 85,
        "가능지": 79
    },
    "가지": {
        "심토토성": {"사양질": 8, "미사사양질": 8, "식양질": 7, "미사식양질": 7, "식질": 4, "사질": 4},
        "유효토심": {">100": 14, "50-100": 13, "25-50": 12, "0-25": 11},
        "배수등급": {"양호": 23, "약간양호": 21, "매우양호": 19, "약간불량": 11, "불량": 11, "매우불량": 11},
        "경사": {"0-2": 31, "2-7": 29, "7-15": 26, ">15": 15},
        "자갈함량": {"0-15": 19, "15-35": 17, ">35": 16},
        "지형": {"하성평탄지": 5, "곡간지": 4, "선상지": 4, "산록경사지": 2, "홍적대지": 2, "용암류대지": 2, "구릉지": 2, "산악지": 2},
        "최적지": 90,
        "적지": 85,
        "가능지": 79
    },
    "고추": {
        "심토토성": {"미사사양질": 20, "사양질": 20, "식양질": 15, "미사식양질": 15, "식질": 10, "사질": 5},
        "유효토심": {">100": 7, "50-100": 6, "25-50": 5, "0-25": 3},
        "배수등급": {"양호": 22, "약간양호": 18, "매우양호": 22, "약간불량": 15, "불량": 8, "매우불량": 8},
        "경사": {"<7": 13, "7-15": 11, "15-30": 5, ">30": 3},
        "자갈함량": {"0-15": 5, "15-35": 3, ">35": 1},
        "최적지": 85,
        "적지": 80,
        "가능지": 70
    },
    "배추": {
        "심토토성": {"사양질": 20, "미사사양질": 20, "식양질": 15, "미사식양질": 15, "식질": 10, "사질": 5},
        "유효토심": {">100": 7, "50-100": 6, "25-50": 5, "0-25": 3},
        "배수등급": {"약간양호": 22, "양호": 20, "약간불량": 15, "매우양호": 15, "불량": 8, "매우불량": 8},
        "경사": {"<7": 13, "7-15": 11, "15-30": 5, ">30": 3},
        "자갈함량": {"0-15": 5, "15-35": 3, ">35": 1},
        "최적지": 85,
        "적지": 80,
        "가능지": 70
    },
    "상추": {
        "표토토성": {"양토": 16, "미사질양토": 16, "사양토": 13, "세사양토": 13, "식양토": 12, "미사질식양토": 12, "양질사토": 10, "양질조사토": 10, "양질세사토": 10},
        "심토토성": {"사양질": 18, "미사사양질": 18, "미사식양질": 17, "식양질": 16, "식질": 12, "사질": 12},
        "배수등급": {"양호": 10, "약간양호": 9, "약간불량": 7, "불량": 4, "매우양호": 4, "매우불량": 4},
        "유효토심": {">100": 12, "50-100": 10, "25-50": 9, "0-25": 5},
        "경사": {"0-2": 13, "2-7": 11, "7-15": 5, ">15": 3},
        "지형": {"하성평탄지": 21, "곡간지": 18, "선상지": 18, "산록경사지": 16, "홍적대지": 16, "용암류대지": 16, "하해혼성평탄지": 10, "구릉지": 10, "산악지": 10, "분석구": 10},
        "자갈함량": {"0-15": 10, "15-35": 9, ">35": 4},
        "최적지": 90,
        "적지": 85,
        "가능지": 79
    },
    "무": {
        "심토토성": {"사양질": 20, "미사사양질": 20, "식양질": 15, "미사식양질": 15, "식질": 10, "사질": 5},
        "유효토심": {">100": 7, "50-100": 6, "25-50": 5, "0-25": 3},
        "배수등급": {"양호": 22, "약간양호": 18, "매우양호": 22, "약간불량": 15, "불량": 8, "매우불량": 8},
        "경사": {"2-7": 13, "0-2": 11, "7-15": 5, ">15": 3},
        "자갈함량": {"0-15": 5, "15-35": 3, ">35": 1},
        "최적지": 85,
        "적지": 80,
        "가능지": 70
    },
    "옥수수": {
        "심토토성": {"사양질": 20, "미사사양질": 20, "식양질": 15, "미사식양질": 15, "식질": 10, "사질": 5},
        "유효토심": {">100": 7, "50-100": 6, "25-50": 5, "0-25": 3},
        "배수등급": {"양호": 22, "약간양호": 18, "매우양호": 22, "약간불량": 15, "불량": 8, "매우불량": 8},
        "경사": {"0-2": 13, "2-7": 11, "7-15": 5, ">15": 3},
        "자갈함량": {"0-15": 5, "15-35": 3, ">35": 1},
        "최적지": 85,
        "적지": 80,
        "가능지": 70
    },
    "콩": {
        "심토토성": {"사양질": 20, "미사사양질": 20, "식양질": 15, "미사식양질": 15, "식질": 10, "사질": 5},
        "유효토심": {">100": 7, "50-100": 6, "25-50": 5, "0-25": 3},
        "배수등급": {"양호": 22, "약간양호": 18, "매우양호": 22, "약간불량": 15, "불량": 8, "매우불량": 8},
        "경사": {"2-7": 13, "0-2": 11, "7-15": 5, ">15": 3},
        "자갈함량": {"0-15": 5, "15-35": 3, ">35": 1},
        "최적지": 85,
        "적지": 80,
        "가능지": 70
    },
    "감자": {
        "심토토성": {"사양질": 20, "미사사양질": 20, "식양질": 15, "미사식양질": 15, "식질": 10, "사질": 5},
        "유효토심": {">100": 7, "50-100": 6, "25-50": 5, "0-25": 3},
        "배수등급": {"양호": 22, "약간양호": 18, "매우양호": 22, "약간불량": 15, "불량": 8, "매우불량": 8},
        "경사": {"0-2": 13, "2-7": 11, "7-15": 5, ">15": 3},
        "자갈함량": {"15-35": 5, "0-15": 3, ">35": 1},
        "최적지": 85,
        "적지": 80,
        "가능지": 70
    },
    "고구마": {
        "심토토성": {"미사사양질": 20, "사양질": 20, "식양질": 15, "식질": 10, "미사식양질": 10, "사질": 5},
        "유효토심": {">100": 7, "50-100": 6, "25-50": 5, "0-25": 3},
        "배수등급": {"양호": 22, "약간양호": 18, "매우양호": 22, "약간불량": 15, "불량": 8, "매우불량": 8},
        "경사": {"0-2": 13, "2-7": 11, "7-15": 5, ">15": 3},
        "자갈함량": {"0-15": 5, "15-35": 3, ">35": 1},
        "최적지": 85,
        "적지": 80,
        "가능지": 70
    },
    "대파": {
        "심토토성": {"식양질": 12, "미사사양질": 12, "식질": 10, "사질": 8, "미사식양질": 8, "사양질": 5},
        "유효토심": {"25-50": 12, ">100": 10, "50-100": 8, "0-25": 5},
        "배수등급": {"약간불량": 12, "약간양호": 10, "매우양호": 8, "양호": 8, "불량": 5, "매우불량": 5},
        "경사": {"0-2": 12, "7-15": 10, "2-7": 8, ">15": 5},
        "자갈함량": {"0-15": 12, "15-35": 10, ">35": 5},
        "최적지": 90,
        "적지": 85,
        "가능지": 79
    }
}
# 작물 코드와 이름 매핑
crop_ids = {
    1: "토마토",
    2: "고추",
    3: "옥수수",
    4: "오이",
    5: "콩",
    6: "가지",
    7: "무",
    8: "상추",
    9: "배추",
    10: "감자",
    11: "고구마",
    12: "대파"
}
# 작물별 재배 시즌
crop_plant_season = {
    "토마토": [3, 4, 5],
    "고추": [4, 5],
    "옥수수": [5, 6],
    "오이": [3, 4, 5],
    "콩": [5, 6],
    "가지": [4, 5],
    "무": [8, 9],
    "상추": [3, 4],
    "배추": [9, 10],
    "감자": [3, 4],
    "고구마": [5, 6],
    "대파": [3, 4, 9]
}
# 시도 명칭 매핑 테이블
SIDO_MAPPING = {
    "서울": "서울특별시",
    "부산": "부산광역시",
    "대구": "대구광역시",
    "인천": "인천광역시",
    "광주": "광주광역시",
    "대전": "대전광역시",
    "울산": "울산광역시",
    "세종": "세종특별자치시",
    "경기": "경기도",
    "강원": "강원특별자치도",
    "충북": "충청북도",
    "충남": "충청남도",
    "전북": "전북특별자치도",
    "전남": "전라남도",
    "경북": "경상북도",
    "경남": "경상남도",
    "제주": "제주특별자치도",
    "제주도": "제주특별자치도"
}
# 심토토성 (Deepsoil_Qlt_Code)
deepsoil_qlt_mapping = {
    "01": "사질",
    "02": "사양질",
    "03": "미사사양질",
    "04": "식양질",
    "05": "미사식양질",
    "06": "식질",
    "99": "기타"
}
# 심토자갈함량 (Deepsoil_Ston_Code)
deepsoil_ston_mapping = {
    "01": "없음_0-15%",
    "02": "있음_15-35%",
    "03": "심함_35%이상",
    "99": "기타"
}
# 경사도 (Soilslope_Code)
soilslope_mapping = {
    "01": "경사_0-2%",
    "02": "경사_2-7%",
    "03": "경사_7-15%",
    "04": "경사_15-30%",
    "05": "경사_30-60%",
    "06": "경사_60-100%",
    "99": "기타"
}
# 배수등급 (Soildra_Code)
soildra_mapping = {
    "01": "매우양호",
    "02": "양호",
    "03": "약간양호",
    "04": "약간불량",
    "05": "불량",
    "06": "매우불량",
    "99": "기타"
}
# 유효토심 (Vldsoildep_Code)
vldsoildep_mapping = {
    "01": "매우얕음_0-25cm",
    "02": "얕음_25-50cm",
    "03": "보통_50-100cm",
    "04": "깊음_100cm이상",
    "99": "기타"
}
# 표토토성 (Surtture_Code)
surtture_mapping = {
    "01": "양질조사토",
    "02": "양질세사토",
    "03": "양질사토",
    "04": "세사양토",
    "05": "사양토",
    "06": "양토",
    "07": "미사질양토",
    "08": "미사질식양토",
    "09": "식양토",
    "99": "기타"
}
# 분포지형 (Soil_Type_Geo_Code)
soil_type_geo_mapping = {
    "01": "산악지",
    "02": "구릉지",
    "03": "산록경사지",
    "04": "곡간지/선상지",
    "05": "해성평탄지",
    "06": "하성평탄지",
    "07": "고원지",
    "08": "홍적대지",
    "09": "용암류대지",
    "10": "용암류평탄",
    "99": "기타"
}

import os
from typing import Dict, List, Optional
from sqlalchemy.orm import Session
from dotenv import load_dotenv
from fastapi import HTTPException
from .models import BJDCode
import xml.etree.ElementTree as ET
from urllib import parse
import requests

load_dotenv()

API_KEY=os.getenv("API_KEY")

class CropRecommendationService:
    def __init__(self, db: Session):
        load_dotenv()
        self.db = db
        self.api_key = API_KEY
        # 두 개의 API URL 설정
        self.soil_api_base_url = f"http://apis.data.go.kr/1390802/SoilEnviron/SoilCharac/V2/getSoilCharacter?serviceKey={API_KEY}"
        self.soil_sctnn_api_base_url = f"http://apis.data.go.kr/1390802/SoilEnviron/SoilCharacSctnn/getSoilCharacterSctnn?serviceKey={API_KEY}"

    def get_bjd_code(self, sido: str, sigungu: str, bname1: Optional[str], bname2: str) -> str:
        """
        주소 정보를 기반으로 BJD 코드를 조회하는 함수.
        """
        sido_mapped = SIDO_MAPPING.get(sido, sido)
        query_candidates = [
            f"{sido_mapped} {sigungu} {bname1} {bname2}".strip() if bname1 else f"{sido_mapped} {sigungu} {bname2}".strip(),
            f"{sido_mapped} {sigungu} {bname1}".strip() if bname1 else None,
        ]

        for query_name in query_candidates:
            bjd_entry = self.db.query(BJDCode).filter(BJDCode.bjd_name == query_name).first()
            if bjd_entry and not bjd_entry.abolition:
                return bjd_entry.bjd_code

        raise HTTPException(status_code=404, detail=f"BJD 코드가 없습니다: {query_candidates[0]}")

    def create_pnu_code(self, bjd_code: str, bunji: str) -> str:
        """
        BJD 코드와 번지 정보를 기반으로 PNU 코드를 생성하는 함수.
        """
        bunji_parts = bunji.split('-')
        main_bunji = bunji_parts[0].zfill(4)
        sub_bunji = bunji_parts[1].zfill(4) if len(bunji_parts) > 1 else '0000'
        return f"{bjd_code}1{main_bunji}{sub_bunji}"

    def fetch_and_map_soil_data(self, pnu_code: str) -> Dict[str, str]:
        # 첫 번째 API 호출
        url1 = f"{self.soil_sctnn_api_base_url}&PNU_Code={pnu_code}"
        response1 = requests.get(url1)
        root1 = ET.fromstring(response1.content)
        
        # 두 번째 API 호출
        url2 = f"{self.soil_api_base_url}&PNU_Code={pnu_code}"
        response2 = requests.get(url2)
        root2 = ET.fromstring(response2.content)

        # 모든 데이터를 한 번에 매핑하여 딕셔너리에 저장
        soil_data = {
            "심토토성": deepsoil_qlt_mapping.get(root1.findtext(".//Deepsoil_Qlt_Code"), "기타"),
            "자갈함량": clean_value(deepsoil_ston_mapping.get(root1.findtext(".//Deepsoil_Ston_Code"), "기타")),
            "경사": clean_value(soilslope_mapping.get(root1.findtext(".//Soilslope_Code"), "기타")),
            "배수등급": soildra_mapping.get(root2.findtext(".//Soildra_Code"), "기타"),
            "유효토심": clean_value(vldsoildep_mapping.get(root2.findtext(".//Vldsoildep_Code"), "기타")),
            "표토토성": surtture_mapping.get(root2.findtext(".//Surtture_Code"), "기타"),
            "지형": soil_type_geo_mapping.get(root2.findtext(".//Soil_Type_Geo_Code"), "기타")
        }

        return soil_data

    def calculate_crop_score(self, soil_data: Dict[str, str]) -> Dict[str, int]:
        crop_scores = {}
        for crop_name, criteria in crop_criteria.items():
            score = 0
            # 심토토성 평가
            deepsoil = soil_data.get("심토토성", "기타")
            score += criteria["심토토성"].get(deepsoil, 0)

            # 자갈함량 평가
            gravel_content = soil_data.get("자갈함량", "기타")
            score += criteria["자갈함량"].get(gravel_content, 0)

            # 경사 평가
            slope = soil_data.get("경사", "기타")
            score += criteria["경사"].get(slope, 0)

            # 배수등급 평가
            drainage = soil_data.get("배수등급", "기타")
            score += criteria["배수등급"].get(drainage, 0)

            # 유효토심 평가
            soil_depth = soil_data.get("유효토심", "기타")
            score += criteria["유효토심"].get(soil_depth, 0)

            # 지형 평가
            terrain = soil_data.get("지형", "기타")
            if "지형" in criteria:
                score += criteria["지형"].get(terrain, 0)

            # 점수와 각 항목 출력
            print(f"Crop: {crop_name}, Score: {score}, Deepsoil: {deepsoil}, Gravel: {gravel_content}, Slope: {slope}, Drainage: {drainage}, Soil Depth: {soil_depth}, Terrain: {terrain}")

            crop_scores[crop_name] = score

        return crop_scores

    def recommend_top_crops(self, crop_scores: Dict[str, int], top_n: int = 6, score_threshold: int = 20) -> List[Dict[str, int]]:
        sorted_crops = sorted(crop_scores.items(), key=lambda x: x[1], reverse=True)
        print(f"Sorted crops: {sorted_crops}")  # 점수로 정렬된 작물 출력
        
        recommended_crops = []

        for i, (crop, score) in enumerate(sorted_crops[:top_n]):
            if i < len(sorted_crops) - 1 and score - sorted_crops[i + 1][1] >= score_threshold:
                break
            recommended_crops.append({"plantId": list(crop_ids.keys())[list(crop_ids.values()).index(crop)]})

        return recommended_crops

    def get_crop_recommendations(self, address: dict) -> List[Dict[str, int]]:
        """
        주어진 주소로 작물 추천 결과를 반환.
        """
        bjd_code = self.get_bjd_code(
            address["sido"],
            address["sigungu"],
            address.get("bname1"),
            address["bname2"]
        )
        pnu_code = self.create_pnu_code(bjd_code, address["bunji"])
        soil_data = self.fetch_and_map_soil_data(pnu_code)
        crop_scores = self.calculate_crop_score(soil_data)
        return self.recommend_top_crops(crop_scores)

def clean_value(value: str) -> str:
    """ API에서 가져온 값을 정리하는 함수 """
    if '_' in value:
        value = value.split('_')[1]
    if 'cm' in value:
        value = value.replace('cm', '').strip()
    elif '%' in value:
        value = value.replace('%', '').strip()
    return value


