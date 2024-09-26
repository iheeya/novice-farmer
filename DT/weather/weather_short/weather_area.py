import json
import requests
import os
from dotenv import load_dotenv
from io import StringIO
import csv
import itertools

# load_dotenv(dotenv_path='./.env')

os.environ['WEATHER_AUTH_KEY'] = 'iaVHnTfZQlulR5032dJbtQ'

url = 'https://apihub.kma.go.kr/api/typ01/url/fct_shrt_reg.php'
params = {'tmfc' : 0, 'authKey' : os.getenv('WEATHER_AUTH_KEY')}
response = requests.get(url, params=params)

csv_data = response.text

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

    
    if reg_sp in ['A', 'B', 'C']:
        if '-' not in reg_name and '해안' not in reg_name and '내륙' not in reg_name:
            filtered_data.append([
                reg_id, reg_name
            ])
        

    with open('weather_area.csv', mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(['id', 'name'])
        writer.writerows(filtered_data)