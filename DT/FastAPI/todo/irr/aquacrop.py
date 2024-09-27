from aquacrop.core import AquaCropModel, Soil, Crop, Weather
from aquacrop.utils.data import get_filepath

import sys
import os

os.environ['DEVELOPMENT'] = 'True'
# AquaCrop 경로 설정
sys.path.append(os.path.abspath('c:/Users/SSAFY/Desktop/BigData/Aquacrop2/aquacrop'))
project_root = os.path.abspath(os.path.dirname(__file__))
sys.path.append(project_root)

path = get_filepath('hyderabad_climate.txt')
# Soil, Crop, Weather 데이터 설정
soil = Soil('SandyLoam')
crop = Crop('Maize', planting_date='2024/05/01')
weather = Weather(path)  # 날씨 데이터 경로

# 모델 생성
model = AquaCropModel(soil=soil, crop=crop, weather=weather)

# 시뮬레이션 실행
model.run_model(till_termination=True)

# 결과 출력
results = model.get_results()
print(results)
