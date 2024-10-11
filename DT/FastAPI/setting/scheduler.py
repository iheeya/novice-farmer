from apscheduler.schedulers.background import BackgroundScheduler
import time


scheduler = BackgroundScheduler()

# weather과 growth에서 update 필요한 함수들 호출.
from weather.weather import load_valinfo


def scheduled_task():
    # 작업해야 할 함수들
    load_valinfo()
    
def start_scheduler():
    scheduler.add_job(scheduled_task, 'cron', hour=0, minute=0)
    scheduler.start()
    
def stop_scheduler():
    scheduler.shutdown()