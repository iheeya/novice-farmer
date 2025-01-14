# 기상 정보
from sqlalchemy.orm import Session
from setting.mysql import session_local
from weather.models import WeatherArea, WeatherVal, AwsStn, AdmDistrict, SpecialWeather, CurrentSpecialWeather
from weather.schemas import WeatherAreaSchema, WeatherValSchema, AwsStnSchema, AdmDistrictSchema, SpecialWeatherSchema, CurrentSpecialWeatherSchema
from sqlalchemy.exc import OperationalError

from dotenv import load_dotenv
from io import StringIO
from datetime import datetime, timedelta
import requests, os, csv, itertools, time

# 환경 변수 로드
load_dotenv()

# farmers: Session = session_local['farmer']()

# 트랜잭션 중첩 방지
def retry_transaction(db: Session, max_retries=5, delay=1):
    for attempt in range(max_retries):
        try:
            db.commit()  # 트랜잭션 커밋 시도
            break  # 성공 시 루프 탈출
        except OperationalError as e:
            db.rollback()  # 충돌 발생 시 롤백
            time.sleep(delay)  # 재시도 전 대기
    else:
        raise Exception("트랜잭션 불가능")

# 예보구역 데이터 가져오기
def load_areainfo():
    fast_api: Session = session_local['fast_api']()
    url = 'https://apihub.kma.go.kr/api/typ01/url/fct_shrt_reg.php'
    params = {'tmfc' : 0, 'authKey' : os.getenv('WEATHER_AUTH_KEY')}
    response = requests.get(url, params=params)
    csv_data = response.text
    csv_reader = csv.reader(StringIO(csv_data))
    csv_reader = itertools.islice(csv_reader, 11, None)
    
    # 트랜잭션 및 CRUD 기능
    try:
        with fast_api.begin():
            fast_api.query(WeatherArea).delete()
            for row in csv_reader:
                reg = list(row[0].split(' '))
                if len(reg) < 5:
                    continue
                reg_id, reg_sp, reg_name = reg[0], reg[3], reg[9]
                
                if reg_sp == 'C':
                    # if '(' not in reg_name:
                        add_areainfo_to_db(fast_api, str(reg_id), reg_name)
            retry_transaction(fast_api)
    except Exception as e:
        fast_api.rollback()
        print(f'load_areainfo에서 에러가 발생했습니다: {e}')
        
# WeatherArea 테이블에 데이터 추가
def add_areainfo_to_db(db: Session, reg_id: str, reg_name: str):
    
    # 데이터 검증
    try:
        WeatherAreaSchema(reg_id=reg_id, reg_name=reg_name)
    except Exception as e:
        print(f'WeatherArea 데이터 검증에 실패했습니다: {e}')
        return
    
    # 중복 검사
    check_exiting = db.query(WeatherArea).filter(WeatherArea.reg_id == id).first()
    
    if not check_exiting:
        reg_info = WeatherArea(reg_id=reg_id, reg_name=reg_name)
        db.add(reg_info)

# AWS 지점 데이터 가져오기
def load_aswsinfo():
    fast_api: Session = session_local['fast_api']()
    url = 'https://apihub.kma.go.kr/api/typ01/url/stn_inf.php'
    
    updated_date = datetime.now() - timedelta(days=1)
    updated_date = updated_date.strftime('%Y%m%d%H%M')
    
    params = {'inf' : 'AWS', 'tm' : updated_date, 'authKey' :  os.getenv('WEATHER_AUTH_KEY'), 'help' : '0'}
    response = requests.get(url, params=params)
    csv_data = response.text
    csv_reader = csv.reader(StringIO(csv_data))
    csv_reader = itertools.islice(csv_reader, 3, None)
    
    # 트랜잭션
    try:
        with fast_api.begin():
            fast_api.query(AwsStn).delete()
            for row in csv_reader:
                row[0] = row[0].replace('*', ' ')
                row = list(row[0].split())
                if len(row) < 2:
                    continue
                stn_id, stn_lon, stn_lat, reg_id, law_id  = row[0], float(row[1]), float(row[2]), row[10], row[11]
                # 이상 데이터 하나 거르는 용도
                if len(row) != 13:
                    law_id = row[12]
                add_awsinfo_to_db(fast_api, stn_id, stn_lon, stn_lat, reg_id, law_id)
            retry_transaction(fast_api)
    except Exception as e:
        fast_api.rollback()
        print(f'load_aswsinfo에서 에러가 발생했습니다: {e}')

# AwsStn 테이블에 데이터 추가
def add_awsinfo_to_db(db:Session, id: str, lon: float, lat: float, reg_id: str, law_id: str):
    
    try:
        AwsStnSchema(stn_id=id, lon=lon, lat=lat, reg_id=reg_id, law_id=law_id)
    except Exception as e:
        print(f'AwsStn 데이터 검증에 실패했습니다: {e}')
        return
    
    check_exiting = db.query(AwsStn).filter(AwsStn.stn_id == id).first()
    if not check_exiting:
        stn_info = AwsStn(stn_id=id, lon=lon, lat=lat, reg_id=reg_id, law_id=law_id)
        db.add(stn_info)

# 행정구역 데이터 가져오기
def load_adminfo():
    fast_api: Session = session_local['fast_api']()
    base_dir = os.path.dirname(os.path.abspath(__file__))
    file_path = os.path.join(base_dir, 'adm_district.csv')
    
    with open(file_path, "r", encoding='utf-8') as file:
        csv_data = csv.reader(file)

        next(csv_data)
        
        # 트랜잭션
        try:
            with fast_api.begin():
                fast_api.query(AdmDistrict).delete()
                for row in csv_data:
                    if len(row) < 6:
                        continue
                    adm_id, adm_head, adm_middle, adm_tail, x_grid, y_grid, lon, lat = row[0], row[1], row[2], row[3], int(row[4]), int(row[5]), float(row[6]), float(row[7])
                    add_adminfo_to_db(fast_api, adm_id, adm_head, adm_middle, adm_tail, x_grid, y_grid, lon, lat)
                retry_transaction(fast_api)
        except Exception as e:
            fast_api.rollback()
            print(f"load_adminfo에서 에러가 발생했습니다: {e}")

# AdmDistrinct 테이블에 데이터 추가
def add_adminfo_to_db(db: Session, id: str, head: str, middle: str, tail: str, xgrid: int, ygrid: int, lon: float, lat: float):
    
    try:
        AdmDistrictSchema(adm_id=id, adm_head=head, adm_middle=middle, adm_tail=tail, x_grid=xgrid, y_grid=ygrid, lon=lon, lat=lat)
    except Exception as e:
        print(f'AdmDistrict 데이터 검증에 실패했습니다: {e}')
        return
    
    check_existing = db.query(AdmDistrict).filter(AdmDistrict.adm_id==id).first()
    if not check_existing:
        admin_info = AdmDistrict(adm_id=id, adm_head=head, adm_middle=middle, adm_tail=tail, x_grid=xgrid, y_grid=ygrid, lon=lon, lat=lat)
        db.add(admin_info)

# AWS 기반 기상 데이터(강수, 최고온도, 최저온도) 가져오기
def load_valinfo():
    fast_api: Session = session_local['fast_api']()
    target_date = datetime.today() - timedelta(days=1)
    target_date = target_date.strftime('%Y%m%d')
    url = 'https://apihub.kma.go.kr/api/typ01/url/sfc_aws_day.php'
    params_tamax = {'tm1': target_date, 'tm2': target_date, 'obs': 'ta_max', 'authKey': os.getenv('WEATHER_AUTH_KEY')}
    params_tamin = {'tm1': target_date, 'tm2': target_date, 'obs': 'ta_min', 'authKey': os.getenv('WEATHER_AUTH_KEY')}
    params_rnday = {'tm1': target_date, 'tm2': target_date, 'obs': 'rn_day', 'authKey': os.getenv('WEATHER_AUTH_KEY')}
    
    response_tamax = requests.get(url, params=params_tamax).text
    response_tamin = requests.get(url, params=params_tamin).text
    response_rnday = requests.get(url, params=params_rnday).text

    rnday_data = csv.reader(StringIO(response_rnday))
    rnday_data = itertools.islice(rnday_data, 3, None)
    tamax_data = csv.reader(StringIO(response_tamax))
    tamax_data = itertools.islice(tamax_data, 3, None)
    tamin_data = csv.reader(StringIO(response_tamin))
    tamin_data = itertools.islice(tamin_data, 3, None)
    
    # 트랜잭션
    try:
        with fast_api.begin():
            fast_api.query(WeatherVal).delete()
            for row1, row2, row3 in zip(rnday_data, tamax_data, tamin_data):
                if len(row1) < 2:
                    continue
                stn_id, rn_day = row1[1], float(row1[5])
            
                if len(row2) < 2:
                    continue
                ta_max = float(row2[5])
                
                if len(row3) < 2:
                    continue
                ta_min = float(row3[5])
            
                add_valinfo_to_db(fast_api, stn_id, rn_day, ta_max, ta_min)
            retry_transaction(fast_api)
    except Exception as e:
        fast_api.rollback()
        print(f"load_valinfo에서 에러가 발생했습니다: {e}")

# WeatherVal 테이블에 데이터 추가
def add_valinfo_to_db(db: Session, stn_id: str, rn_day: float, ta_max: float, ta_min: float):
    
    try:
        WeatherValSchema(stn_id=stn_id, rn_day=rn_day, ta_max=ta_max, ta_min=ta_min)
    except Exception as e:
        print(f'WeatherVal 데이터 검증에 실패했습니다: {e}')
        return
    
    check_existing = db.query(WeatherVal).filter(WeatherVal.stn_id==stn_id).first()
    if not check_existing:
        val_info = WeatherVal(stn_id=stn_id, rn_day=rn_day, ta_max=ta_max, ta_min=ta_min)
        db.add(val_info)

# 기상특보 지역 데이터 가져오기
def load_special_areainfo():
    fast_api: Session = session_local['fast_api']()
    url = 'https://apihub.kma.go.kr/api/typ01/url/wrn_reg_aws2.php'
    
    updated_date = datetime.now() - timedelta(days=1)
    updated_date = updated_date.strftime('%Y%m%d%H%M%S')
    params = {'tm': updated_date, 'help': '0', 'authKey': os.getenv('WEATHER_AUTH_KEY')}
    response = requests.get(url, params=params)
    csv_data = response.text
    csv_reader = csv.reader(StringIO(csv_data))
    
    # 트랜잭션
    try:
        with fast_api.begin():
            fast_api.query(SpecialWeather).delete()
            for row in csv_reader:
                stn_id, wrn_id, reg_id = row[0], row[7], row[6]
                add_special_weather_to_db(fast_api, stn_id, wrn_id, reg_id)
            retry_transaction(fast_api)
    except Exception as e:
        fast_api.rollback()
        print(f'load_special_areainfo에서 에러가 발생했습니다: {e}')

# 기상특보 지역 데이터 추가
def add_special_weather_to_db(db: Session, stn_id: str, wrn_id: str, reg_id: str):
    
    try:
        SpecialWeatherSchema(stn_id=stn_id, wrn_id=wrn_id, reg_id=reg_id)
    except Exception as e:
        print(f'SpecialWeather 데이터 검증에 실패했습니다: {e}')
        return
    
    check_existing = db.query(SpecialWeather).filter(stn_id==stn_id).first()
    if not check_existing:
        special_info = SpecialWeather(stn_id=stn_id, wrn_id=wrn_id, reg_id=reg_id)
        db.add(special_info)

# 기상특보 데이터 가져오기
# 자정부터 3시간 간격으로 실행되게 설정 필요
def load_curruent_special_weatherinfo():
    fast_api: Session = session_local['fast_api']()
    url = 'https://apihub.kma.go.kr/api/typ01/url/wrn_now_data.php'

    nowtime = datetime.now()
    nowtime = nowtime.strftime('%Y%m%d%H%M')
    
    params = {'fe': 'f', 'tm': '202410070300', 'authKey': os.getenv('WEATHER_AUTH_KEY'), 'help': '1'}
    
    response = requests.get(url, params=params)
    
    csv_data = response.text
    
    csv_reader = csv.reader(StringIO(csv_data))
    csv_reader = itertools.islice(csv_reader, 18, None)
    
    try:
        with fast_api.begin():
            fast_api.query(CurrentSpecialWeather).delete()
            for row in csv_reader:
                wrn_id, wrn_type = row[2], row[6]
                add_current_special_weather_to_db(fast_api, wrn_id, wrn_type)
            retry_transaction(fast_api)
    except Exception as e:
        fast_api.rollback()
        print(f'load_curruent_special_weatherinfo에서 에러가 발생했습니다: {e}')

# 기상특보 데이터 추가
def add_current_special_weather_to_db(db: Session, wrn_id: str, wrn_type: str):
    
    try:
        CurrentSpecialWeatherSchema(wrn_id=wrn_id, wrn_type=wrn_type)
    except Exception as e:
        print(f'CurrentSpecialWeather 데이터 검증에 실패했습니다: {e}')
        return
    
    check_exisisting = db.query(CurrentSpecialWeather).filter(CurrentSpecialWeather.wrn_id==wrn_id).first()
    if not check_exisisting:
        current_special_info = CurrentSpecialWeather(wrn_id=wrn_id, wrn_type=wrn_type)
        db.add(current_special_info)
