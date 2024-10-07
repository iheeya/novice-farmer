from apscheduler.schedulers.background import BackgroundScheduler
import time


scheduler = BackgroundScheduler()

# weather과 growth에서 update 필요한 함수들 호출.
from weather.weather import load_valinfo
from growth.growth import calculate_crop_degree_days


def scheduled_task():
    # 작업해야 할 함수들
    # 매일 기상 데이터 가져오기
    load_valinfo()
    # 매일 작물별 생장도 계산하기
    # calculate_crop_degree_days()
    
def start_scheduler():
    scheduler.add_job(scheduled_task, 'cron', hour=0, minute=0)
    scheduler.start()
    
def stop_scheduler():
    scheduler.shutdown()