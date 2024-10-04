# 기상 정보
from sqlalchemy.orm import Session
from setting.mysql import session_local
from .models import WeatherArea, WeatherVal, AwsStn, AdmDistrict

from dotenv import load_dotenv
from io import StringIO
import json, requests, os, csv, itertools


load_dotenv()
db: Session = session_local()

def load_areainfo(): # 예보구역 데이터 가져오기
    url = 'https://apihub.kma.go.kr/api/typ01/url/fct_shrt_reg.php'
    params = {'tmfc' : 0, 'disp' : 1, 'authKey' : os.getenv('WEAHTER_AUTH_KEY')}
    response = requests.get(url, params=params)

    csv_data = response.text

    # print(csv_data)

    csv_reader = csv.reader(StringIO(csv_data))

    filtered_data = []

    csv_reader = itertools.islice(csv_reader, 11, None)

    for row in csv_reader:
        reg = []
        reg = list(row[0].split())
        if len(reg) < 5:
            continue
        reg_id = reg[0]
        reg_sp = reg[3]
        reg_name = reg[4]

        
        if reg_sp == 'C':
            if '(' not in reg_name:
                add_areainfo_to_db(db, str(reg_id), reg_name)
    db.commit()
        
def add_areainfo_to_db(db: Session, id: str, name: str):
    # 중복 검사
    check_exiting = db.query(WeatherArea).filter(WeatherArea.reg_id == id).frist()
    
    if not check_exiting:
        reg_info = WeatherArea(reg_id=id, reg_name=name)
        db.add(reg_info)
    

def load_aswsinfo(): # aws 지점 데이터 가져오기
    url = 'https://apihub.kma.go.kr/api/typ01/url/stn_inf.php?'
    params = {'inf' : 'AWS', 'tm' : '202410010000', 'authKey' :  os.getenv('WEATHER_AUTH_KEY'), 'help' : '0'}
    response = requests.get(url, params=params)
    
    csv_data = response.text
    
    # print(csv_data)

    csv_reader = csv.reader(StringIO(csv_data))

    filtered_data = []

    csv_reader = itertools.islice(csv_reader, 3, None)
        
    for row in csv_reader:
        stn = []
        if len(stn) < 13:
            continue
        stn_id = stn[0]
        stn_lon = stn[1]
        stn_lat = stn[2]
        reg_id = stn[10]
        law_id = stn[11]

        
def load_adminfo(): # 행정구역 데이터 가져오기
    return
    
def load_valinfo(): # aws 기반 기상 데이터 가져오기
    return
    
if __name__ == "__main__":
    load_areainfo()