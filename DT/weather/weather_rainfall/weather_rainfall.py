import json
import requests
import os
from dotenv import load_dotenv
from io import StringIO
import csv
import itertools
from datetime import datetime
import pandas as pd

now = datetime.now()
today = '24' + now.strftime("%y%m%d")
tmfc  = '24' + now.strftime("%y%m%d%H")

# load_dotenv(dotenv_path='./.env')

os.environ['WEATHER_AUTH_KEY'] = 'iaVHnTfZQlulR5032dJbtQ'

timelst = ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', #+
            '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23']#+

for tm in timelst:
    tmef = today + tm
    url = 'https://apihub.kma.go.kr/api/typ01/cgi-bin/url/nph-dfs_shrt_grd'
    params = {'tmfc' : tmfc, 'tmef': tmef, 'vars':'PCP', 'authKey' : os.getenv('WEATHER_AUTH_KEY')}
    response = requests.get(url, params=params)
    csv_file = StringIO(response.text)
    reader = csv.reader(csv_file)
    csv_data = []
    for row in reader:
        # 각 필드의 공백을 제거한 후 저장
            csv_data.append([row[:-1]])
    
    with open(f'./weather_rainfall/rainfall_{tm}.csv', mode='w', newline='', encoding='utf-8') as file:
            writer = csv.writer(file)
            writer.writerows(csv_data)