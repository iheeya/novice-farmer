# 작물 생장도(DDs) 계산
from sqlalchemy.orm import Session
from sqlalchemy import or_
from setting.mysql import session_local
from setting.models import UserPlace, Farm
from setting.schemas import FarmUpdateSchema
from weather.models import AwsStn, WeatherArea, WeatherVal
from .models import GrowthTemp
from dotenv import load_dotenv
import  math

load_dotenv()
fast_api: Session = session_local['fast_api']()
farmer: Session = session_local['farmer']()

def crops_growth(tmax, tmin, thi, tlow): # 현재는 토마토에 대한 값만 계산 중.
    def docalcs_S(max_val, min_val):
        if min_val > thi:
            heat = thi - tlow
        else:
            if max_val <= tlow:
                heat = 0
            else:
                fk1 = 2 * tlow
                diff = max_val - min_val
                sum_val = max_val + min_val
                if min_val >= tlow:
                    heat = (sum_val - fk1) / 2
                else:
                    heat = sinec(sum_val, diff, fk1)
                if max_val > thi:
                    fk1 = 2 * thi
                    heat -= sinec(sum_val, diff, fk1)
        
        ddtmp = heat / 2
        return ddtmp

    def sinec(sum_val, diff, fk1):
        twopi = 6.28318530717959
        pihlf = 1.5707963267949
        d2 = fk1 - sum_val
        d3 = diff**2 - d2**2
        # 부동소수점 오차로 인한 sqrt 오류 방지
        if d3 < 0 and d3 > -1e-9:
            d3 = 0
        theta = math.atan2(d2, math.sqrt(d3))
        if d2 < 0 and theta > 0:
            theta -= 3.1416
        heat = (diff * math.cos(theta) - d2 * (pihlf - theta)) / twopi
        return heat
    
    DD1 = 2*docalcs_S(tmax, tmin)
    tmep_tlow, temp_thi = tlow, tmin
    tlow, thi = thi, 2*thi - tlow
    DD2 = 2*docalcs_S(tmax, tmin)
    tlow, thi = tmep_tlow, temp_thi

    return round((DD1-DD2) * (9/5))
    
    
# 스케쥴러 통해 매 자정 작물별 생장도 계산
def update_degree_days(db: Session, data: FarmUpdateSchema, farm_id: int):
    
    farm = farmer.query(Farm).filter(Farm.farm_id == farm_id).first()
    
    if not farm:
        raise Exception(f'농장이 없습니다')
    
    if data.farm_degree_day is not None:
        farm.farm_degree_day = data.farm_degree_day


def update_farm_growth():
    try:
        # 1. 모든 농장(farm.farm_id)을 순회하며 모든 작물(farm.plant_id)에 대해 계산 실시
        farmer_data = farmer.query(Farm).all()

        # # 2. 작물 종류(plant_id )에 따른 생장 온도(growth_temp.crop_id))
        # plant_data = farmer.query(Plant).all()

        # # 3. 농장 위치(farm.user_palce_id)에서 시도, 시군구
        # place_data = farmer.query(Place)
        # user_palce_data = farmer.query(UserPlace).all()

        # # 4. 위치 기반으로 WeatherArea reg_id 구함, AwsStn과 join
        # area_data = fast_api.query(WeatherArea).all()
        # aws_data = fast_api.query(AwsStn).all()

        # # 5. join한 테이블과 WeatherVal과 stn_id로 join해서 최고기온, 최저기온.
        # weather_data = fast_api.query(WeatherVal).all()

        # 6. 농장, 농장위치, 작물 정보 일치하는 곳에 dd값 계산한 것 넣어주기.
        for farm in farmer_data:
            if farm.farm_id is None:
                continue
            id = farm.farm_id
            plant = farm.plant_id
            user_place = farm.user_place_id
            DDs = farm.farm_degree_day
            
            print(f'farm_id는 {id}입니다.')
            # 작물별 생장 한계 온도
            thi, tlow = fast_api.query(GrowthTemp).with_entities(GrowthTemp.growth_high_temp, GrowthTemp.growth_low_temp).filter(GrowthTemp.crop_id == plant).first()
            print(f'thi, hlow: {thi}, {tlow}')
            
            # 유저 농장의 위치
            # 위치 정보 처리
            sido, sigungu = farmer.query(UserPlace).with_entities(UserPlace.user_place_sido, UserPlace.user_place_sigugun).filter(UserPlace.user_place_id == user_place).first()
            sido = sido[:2]
            sigungu = sigungu[:-1]
            
            print(f'sido, sigungu: {sido}, {sigungu}')
            
            # 유저 위치 정보에 맞는 예보구역
            reg_id = fast_api.query(WeatherArea).with_entities(WeatherArea.reg_id).filter(or_(WeatherArea.reg_name.like(f'%{sido}%'), WeatherArea.reg_name.like(f'%{sigungu}%'))).first()
            print(f'reg_id: {reg_id}')
            
            if reg_id:
                # 예보구역에 맞는 관측구역
                stn_id = fast_api.query(AwsStn).with_entities(AwsStn.stn_id).filter(AwsStn.reg_id == reg_id[0]).first()
                print(f'stn_id: {stn_id}')
                
                if stn_id:
                    # 관측구역에서 관측한 데이터
                    tmax, tmin = fast_api.query(WeatherVal).with_entities(WeatherVal.ta_max, WeatherVal.ta_min).filter(WeatherVal.stn_id == stn_id[0]).first()
                    print(f'ta_max, ta_min: {tmax}, {tmin}')
                    
                    
                    # 데이터들을 가지고 CropTime 알고리즘 실행
                    DDs += crops_growth(tmax, tmin, thi, tlow)
                    print(f'DD값은 {DDs} 입니다.')
                    
                    update_degree_days(farmer, FarmUpdateSchema(farm_degree_day=DDs), id)
        farmer.commit()
    except Exception as e:
        farmer.rollback()
        print(f'growth에서 에러가 발생했습니다: {e}')
    finally:
        fast_api.close()
        farmer.close()