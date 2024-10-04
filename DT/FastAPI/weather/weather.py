# 기상 정보
from sqlalchemy.orm import Session
from setting.mysql import session_local
from .models import WeatherArea, WeatherVal, AwsStn, AdmDistrict

from dotenv import load_dotenv
from io import StringIO
from datetime import datetime, timedelta
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
    csv_reader = itertools.islice(csv_reader, 11, None)

    for row in csv_reader:
        reg = []
        reg = list(row[0].split())
        if len(reg) < 5:
            continue
        reg_id, reg_sp, reg_name = reg[0], reg[3], reg[4]
        
        if reg_sp == 'C':
            if '(' not in reg_name:
                add_areainfo_to_db(db, str(reg_id), reg_name)
    db.commit()
        
def add_areainfo_to_db(db: Session, id: str, name: str):
    # 중복 검사
    check_exiting = db.query(WeatherArea).filter(WeatherArea.reg_id == id).first()
    
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

    csv_reader = itertools.islice(csv_reader, 3, None)
        
    for row in csv_reader:
        stn = list(row[0].split())
        if len(stn) < 2:
            continue
        stn_id, stn_lon, stn_lat, reg_id, law_id  = stn[0], stn[1], stn[2], stn[10], stn[11]
        add_stninfo_to_db(stn_id, stn_lon, stn_lat, reg_id, law_id)
    db.commit()
        
def add_stninfo_to_db(db:Session, id: str, lon: float, lat: float, reg_id: str, law_id: str):
    check_exiting = db.query(AwsStn).filter(AwsStn.stn_id == id).first()
    if not check_exiting:
        stn_info = AwsStn(stn_id=id, lon=lon, lat=lat, reg_id=reg_id, law_id=law_id)
        db.add(stn_info)
        
def load_adminfo(): # 행정구역 데이터 가져오기
    with open("./adm_district.csv", "r", encoding='utf-8') as file:
        csv_data = csv.reader(file)
    
    next(csv_data)
    for row in csv_data:
        adm = list(row[0].split(','))
        if len(adm) < 6:
            continue
        adm_id, adm_head, adm_middle, adm_tail, x_grid, y_grid, lon, lat = adm[0], adm[1], adm[2], adm[3], int(adm[4]), int(adm[5]), float(adm[6]), float(adm[7])
        add_adminfo_to_db(adm_id, adm_head, adm_middle, adm_tail, x_grid, y_grid, lon, lat)
    db.commit()

def add_adminfo_to_db(db: Session, id: str, head: str, middle: str, tail: str, xgrid: int, ygrid: int, lon: float, lat: float):
    check_existing = db.query(AdmDistrict).filter(AdmDistrict.adm_id==id).first()
    if not check_existing:
        admin_info = AdmDistrict(adm_id=id, adm_head=head, adm_middle=middle, adm_tail=tail, x_grid=xgrid, y_grid=ygrid, lon=lon, lat=lat)
        db.add(admin_info)
    
