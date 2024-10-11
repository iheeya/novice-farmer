import requests
import os
from dotenv import load_dotenv
import xml.etree.ElementTree as ET  # XML 파싱을 위한 라이브러리
from sqlalchemy.orm import Session
from .models import BJDCode  # BJD 코드 테이블 모델

load_dotenv()

import logging
logging.basicConfig()
logging.getLogger('sqlalchemy.engine').setLevel(logging.INFO)

class GardenCropRecommender:
    def __init__(self, address: str, desired_crop: list = None, db: Session = None):
        """
        초기화 메서드
        :param address: 텃밭의 주소 정보 (주소를 받아서 BJD 코드로 변환)
        :param desired_crop: 사용자가 원하는 작물 목록 (리스트 형식), None일 경우 빈 리스트로 처리
        :param db: SQLAlchemy DB 세션
        """
        self.address = address
        # 빈 문자열을 포함하지 않는 리스트로 처리
        self.desired_crop = [crop for crop in desired_crop if crop] if desired_crop is not None else []
        self.api_key = os.getenv('API_KEY')  # 환경변수에 저장된 API Key 불러오기
        self.api_base_url = 'http://apis.data.go.kr/1390802/SoilEnviron/SoilFitStat/getSoilCropFitInfo'
        self.db = db  # DB 세션 저장

        # 주소를 받아 BJD 코드로 변환
        self.bjd_code = self.get_bjd_code_from_address()

        # 순회할 작물 목록
        self.crop_codes = {
            'CR001': '옥수수',
            'CR022': '고추',
            'CR017': '오이',
            'CR018': '토마토',
            'CR030': '콩',
            'CR021': '가지',
            'CR028': '무',
            'CR044': '상추',
            'CR038': '배추',
            'CR032': '감자',
            'CR033': '고구마',
            'CR048': '대파',
        }

    def split_address(self):
        """
        주소를 시도, 시군구, 읍면동, 리로 분리하는 함수
        :return: 시도, 시군구, 읍면동, 리
        """
        address_parts = self.address.split()  # 주소를 공백으로 분리
        
        # 기본 값은 None으로 설정
        시도, 시군구, 읍면동, 리 = None, None, None, None

        # 시도
        if len(address_parts) > 0:
            시도 = address_parts[0]
        
        # 시군구
        if len(address_parts) > 1:
            시군구 = address_parts[1]

        # 읍면동
        if len(address_parts) > 2:
            읍면동 = address_parts[2]
        
        # 리 (선택적)
        if len(address_parts) > 3:
            리 = ' '.join(address_parts[3:])  # '리'는 종종 여러 단어로 이루어질 수 있음

        return {
            '시도': 시도,
            '시군구': 시군구,
            '읍면동': 읍면동,
            '리': 리
        }

    def get_bjd_code_from_address(self):
        """
        주소를 받아서 DB에서 BJD 코드를 반환하는 메서드
        :return: BJD 코드 (법정동 코드)
        """
        if self.db is None:
            raise ValueError("DB 세션이 없습니다.")
        
        # 주소를 분리
        address_info = self.split_address()
        시도, 시군구, 읍면동, 리 = address_info['시도'], address_info['시군구'], address_info['읍면동'], address_info['리']
        
        # 1. 시도+시군구+읍면동+리로 조회
        if 리 and 읍면동 and 시군구 and 시도:
            full_address = f"{시도} {시군구} {읍면동} {리}"
            print(f"Attempting to find BJD code for full address: {full_address}")
            bjd_entry = self.db.query(BJDCode).filter(BJDCode.bjd_name == full_address).first()
            if bjd_entry:
                if bjd_entry.abolition:
                    raise ValueError(f"폐지된 코드입니다: {bjd_entry.bjd_name}")
                return bjd_entry.bjd_code

        # 2. 시도+시군구+읍면동으로 조회
        if 읍면동 and 시군구 and 시도:
            partial_address = f"{시도} {시군구} {읍면동}"
            print(f"Attempting to find BJD code for partial address (읍면동): {partial_address}")
            bjd_entry = self.db.query(BJDCode).filter(BJDCode.bjd_name == partial_address).first()
            if bjd_entry:
                if bjd_entry.abolition:
                    raise ValueError(f"폐지된 코드입니다: {bjd_entry.bjd_name}")
                return bjd_entry.bjd_code

        # 3. 시도+시군구로 조회
        if 시군구 and 시도:
            partial_address = f"{시도} {시군구}"
            print(f"Attempting to find BJD code for partial address (시군구): {partial_address}")
            bjd_entry = self.db.query(BJDCode).filter(BJDCode.bjd_name == partial_address).first()
            if bjd_entry:
                if bjd_entry.abolition:
                    raise ValueError(f"폐지된 코드입니다: {bjd_entry.bjd_name}")
                return bjd_entry.bjd_code

        # 4. 시도만으로 조회
        if 시도:
            print(f"Attempting to find BJD code for 시도: {시도}")
            bjd_entry = self.db.query(BJDCode).filter(BJDCode.bjd_name == 시도).first()
            if bjd_entry:
                if bjd_entry.abolition:
                    raise ValueError(f"폐지된 코드입니다: {bjd_entry.bjd_name}")
                return bjd_entry.bjd_code

        # 적합한 법정동 코드가 없으면 예외 발생
        raise ValueError(f"주소 '{self.address}'에 해당하는 법정동 코드가 없습니다.")

    def fetch_soil_data(self, crop_code: str):
        # abolition 여부를 확인
        bjd_entry = self.db.query(BJDCode).filter(BJDCode.bjd_code == self.bjd_code).first()
        if bjd_entry and bjd_entry.abolition:
            return "폐지된 코드입니다."
        
        params = {
            'serviceKey': self.api_key,
            'BJD_Code': self.bjd_code,  # 변환된 법정동 코드 사용
            'soil_Crop_Code': crop_code,
        }
        print(params)
        response = requests.get(self.api_base_url, params=params)
        
        if response.status_code == 200:
            # XML 데이터를 파싱
            tree = ET.ElementTree(ET.fromstring(response.content))
            root = tree.getroot()
            
            # 파싱된 데이터를 딕셔너리 형태로 변환
            soil_data = {}
            for item in root.findall(".//item"):
                high_suit_area = item.find('high_Suit_Area').text
                suit_area = item.find('suit_Area').text
                poss_area = item.find('poss_Area').text
                low_suit_area = item.find('low_Suit_Area').text
                soil_data = {
                    'high_Suit_Area': high_suit_area,
                    'suit_Area': suit_area,
                    'poss_Area': poss_area,
                    'low_Suit_Area': low_suit_area
                }

            # 모든 값이 0인 경우 폐지된 코드 메시지 반환
            if not soil_data:  # 응답 데이터가 없을 경우 처리
                print(f"{crop_code}에 대한 데이터가 없습니다.")
                return None

            return soil_data
        else:
            print(f"API 요청 실패: {response.status_code}")
            return None


    def evaluate_crop_suitability(self, soil_data: dict):
        """
        API에서 가져온 데이터를 기반으로 작물 적합성을 평가
        :param soil_data: 파싱된 XML 데이터
        :return: 적합성 점수
        """
        if not soil_data or isinstance(soil_data, str):
            return 0  # 데이터가 없거나 폐지된 코드이면 0점 반환

        # 적합지, 가능지, 저위생산지 등 점수를 평가
        high_suit_area = int(soil_data.get('high_Suit_Area', 0))
        suit_area = int(soil_data.get('suit_Area', 0))
        poss_area = int(soil_data.get('poss_Area', 0))
        low_suit_area = int(soil_data.get('low_Suit_Area', 0))

        # 적합성 평가 점수 계산 (단순 예시로 총 면적 기준)
        score = (high_suit_area * 1.5) + (suit_area * 1.2) + poss_area - (low_suit_area * 0.5)
        
        # 점수 출력
        print(f"점수 계산 결과: high_suit_area={high_suit_area}, suit_area={suit_area}, poss_area={poss_area}, low_suit_area={low_suit_area}, score={score}")
        
        return score

    def recommend_crops(self, top_n=6, score_threshold=20):
        """
        12가지 주요 작물에 대한 적합성 평가 후, 상위 top_n 개 작물 이름 리스트 반환
        점수 차이가 특정 임계값을 넘는 경우 제외
        :param top_n: 반환할 상위 작물 개수 (기본 6개)
        :param score_threshold: 상위 작물들과의 점수 차이가 너무 크면 제외할 임계값 (기본 20점)
        :return: 적합한 작물 이름 리스트 (내림차순)
        """
        crop_scores = []

        for crop_code, crop_name in self.crop_codes.items():
            soil_data = self.fetch_soil_data(crop_code)
            suitability_score = self.evaluate_crop_suitability(soil_data)

            # 작물과 그에 대한 점수를 함께 저장
            crop_scores.append((crop_name, suitability_score))

        # 희망 작물이 있다면 가산점 부여
        if self.desired_crop:
            crop_scores = [(crop, score + 10) if crop in self.desired_crop else (crop, score) for crop, score in crop_scores]

        # 점수 순으로 내림차순 정렬
        crop_scores.sort(key=lambda x: x[1], reverse=True)

        # 상위 3개의 점수 가져오기
        top_3_score = crop_scores[2][1] if len(crop_scores) >= 3 else crop_scores[-1][1]

        # 점수 차이가 너무 큰 작물 제외 (임계값으로 필터링)
        filtered_crops = [crop for crop, score in crop_scores if score >= top_3_score - score_threshold]

        # 상위 top_n 개 반환
        return filtered_crops[:top_n]
