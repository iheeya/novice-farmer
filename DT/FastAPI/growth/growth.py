# 작물 생장도(DDs) 계산
from sqlalchemy.orm import Session
from setting.mysql import session_local
from .models import CropBase, CropThreshold

from dotenv import load_dotenv
from io import StringIO
from datetime import datetime, timedelta
import json, requests, os, csv, itertools, math


load_dotenv()
db: Session = session_local['fast_api']()

def crops_growth(): # 현재는 토마토에 대한 값만 계산 중.
    thi, tlow = 33.33, 7.22
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
    
    # tmax, tmin = 21.56, 14.95
    DD1 = 2*docalcs_S(tmax, tmin)
    tmep_tlow, temp_thi = tlow, tmin
    tlow, thi = thi, 2*thi - tlow
    DD2 = 2*docalcs_S(tmax, tmin)
    tlow, thi = tmep_tlow, temp_thi

    DDs = round((DD1-DD2) * (9/5), 2)
    return { 'degreeDay' : DDs}
    
# 스케쥴러 통해 매 자정 작물별 생장도 계산
def calculate_crop_degree_days():
    return


# 작물별 생장도 계산 후 DB에 넣기.
def add_degree_days(db: Session, dds: int):
    return