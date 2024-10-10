from sqlalchemy.orm import Session
from setting.mysql import session_local
from growth.models import CropFertilizerPeriod, CropThreshold
from setting.models import Farm, FarmTodo, FarmTodoType
from sqlalchemy.exc import OperationalError
from datetime import datetime, timedelta
from sqlalchemy import desc, and_

fast_api : Session = session_local['fast_api']()
farmer : Session = session_local['farmer']()

today = datetime.now()

def update_todoinfo():
    # farm 별 degreedays -> crop threshold로 단게 파악
    farm_data = farmer.query(Farm).all()
    # 만약 마지막으로 있는 farm_id의 fertilizer todo가 완료되지 않았고, 해야될 날이 어제였으면 그냥 하루만 추가할 것.
    # 만약 마지막으로 있는 farm_id의 fertilizer todo가 완료되었다면 자동으로 router가 올 것이니 그 때 계산.
    # 현재 farm_id의 todo가 아예 존재하지 않는다면 1단계로 만들어서 추가해주기
    
    
    crop_threshold = fast_api.query(CropThreshold).all()
    crop_threshold_dict = {threshold.crop_id: threshold for threshold in crop_threshold}
    crop_fertilizer = fast_api.query(CropFertilizerPeriod).all()
    crop_fertilizer_dict = {fertilizer_period.crop_id: fertilizer_period for fertilizer_period in crop_fertilizer}
    todo_type = FarmTodoType

    for farm in farm_data:
        # print(farm.farm_id)
        fert_cycle = 0
        last_todo = farmer.query(FarmTodo).filter(and_(FarmTodo.farm_id == farm.farm_id, FarmTodo.farm_todo_type==FarmTodoType.FERTILIZERING)).order_by(desc(FarmTodo.farm_todo_id)).first()
        
        # todo_id = farmer.query(FarmTodo).order_by(desc(FarmTodo.farm_todo_id)).first().farm_todo_id
        
        if last_todo is None:
            continue
        
        yesterday = datetime.now() - timedelta(days=1)
        
        threshold = crop_threshold_dict.get(farm.plant_id)
        fertilizer = crop_fertilizer_dict.get(farm.plant_id)
        
        
        # 만약 fertilizering이 존재하고
        if last_todo.farm_todo_type:
            # 완료돼야 하는 날짜가 어제인데 완료가 안 됐을 때 내일로 날짜 만들어주기.
            if not last_todo.farm_todo_is_completed:
                if last_todo.farm_todo_date == yesterday.date():
                    fert_cycle = 1
            # 완료됐다면 주기에 따라서 계산해주기.
            else:
                if threshold.step4_threshold is not None:
                    if farm.farm_degree_day >= threshold.step4_threshold:
                        fert_cycle = fertilizer.fertilizer_step4_cycle
                elif threshold.step3_threshold is not None:
                    if farm.farm_degree_day >= threshold.step3_threshold:
                        fert_cycle = fertilizer.fertilizer_step3_cycle
                elif threshold.step2_threshold is not None:
                    if farm.farm_degree_day >= threshold.step2_threshold:
                        fert_cycle = fertilizer.fertilizer_step2_cycle
                else:
                    fert_cycle = fertilizer.fertilizer_step1_cycle
        # 만약 fertilizer가 존재하지 않는다면 step1으로 계산해서 넣어주기.
        elif last_todo is None:
            fert_cycle = fertilizer.fertilizer_step1_cycle
        
        if fert_cycle == 0:
            fert_cycle = 30
            
        todo_date = today + timedelta(days=fert_cycle)
        todo_date = todo_date.strftime("%Y-%m-%d %H:%M:%S.%f")
        # todo_id += 1

        add_new_todo(farmer, farm.farm_id, None, FarmTodoType.FERTILIZERING, todo_date, False)
# 단계별로 비료 더해서 todo 생성

def update_request_todo(farm_id, todo_type):
    farm = farmer.query(Farm).filter(Farm.farm_id == farm_id).first()
    last_todo = farmer.query(FarmTodo).filter(and_(FarmTodo.farm_id == farm.farm_id, FarmTodo.farm_todo_type==FarmTodoType.FERTILIZERING)).order_by(desc(FarmTodo.farm_todo_id)).first()
    crop_threshold = fast_api.query(CropThreshold).all()
    crop_threshold_dict = {threshold.crop_id: threshold for threshold in crop_threshold}
    crop_fertilizer = fast_api.query(CropFertilizerPeriod).all()
    crop_fertilizer_dict = {fertilizer_period.crop_id: fertilizer_period for fertilizer_period in crop_fertilizer}
    yesterday = datetime.now() - timedelta(days=1)
    
    threshold = crop_threshold_dict.get(farm.plant_id)
    fertilizer = crop_fertilizer_dict.get(farm.plant_id)
    
    # todo_id = farmer.query(FarmTodo).order_by(desc(FarmTodo.farm_todo_id)).first().farm_todo_id
    
    # 완료돼야 하는 날짜가 어제인데 완료가 안 됐을 때 내일로 날짜 만들어주기.
    if not last_todo.farm_todo_is_completed:
        if last_todo.farm_todo_date == yesterday.date():
            fert_cycle = 1
    # 완료됐다면 주기에 따라서 계산해주기.
    else:
        if threshold.step4_threshold is not None:
            if farm.farm_degree_day >= threshold.step4_threshold:
                fert_cycle = fertilizer.fertilizer_step4_cycle
        elif threshold.step3_threshold is not None:
            if farm.farm_degree_day >= threshold.step3_threshold:
                fert_cycle = fertilizer.fertilizer_step3_cycle
        elif threshold.step2_threshold is not None:
            if farm.farm_degree_day >= threshold.step2_threshold:
                fert_cycle = fertilizer.fertilizer_step2_cycle
        else:
            fert_cycle = fertilizer.fertilizer_step1_cycle
            
    if last_todo is None:
        fert_cycle = fertilizer.fertilizer_step1_cycle
    if fert_cycle == 0:
        fert_cycle = 30
        
    todo_date = today + timedelta(days=fert_cycle)
    todo_date = todo_date.strftime("%Y-%m-%d %H:%M:%S.%f")
    # todo_id += 1
    add_new_todo(farmer, farm.farm_id, '', todo_type, todo_date, False)
    

def add_new_todo(db: Session, farm_id: int, title: str, type: FarmTodoType, date: datetime=None, is_completed: bool=False):
    new_todo = FarmTodo(farm_id=farm_id, farm_todo_title=title, farm_todo_type=type, farm_todo_date=date, farm_todo_is_completed=is_completed)
    
    try:
        db.add(new_todo)
        db.commit()
    except OperationalError as e:
        db.rollback()
        print(f'fert todo 입력 에러: {e}')