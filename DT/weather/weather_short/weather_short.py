import json
import requests
import os
from dotenv import load_dotenv
from io import StringIO
import csv
import itertools

# load_dotenv(dotenv_path='./.env')

os.environ['WEATHER_AUTH_KEY'] = 'iaVHnTfZQlulR5032dJbtQ'
with open('weather_area.csv', mode='r', encoding='utf-8') as file:
    csv_data = csv.reader(file)
    reg_dtaa = [id for id, name in csv_data]
url = 'https://apihub.kma.go.kr/api/typ01/url/fct_afs_dl.php'
params = { 'reg': ['11A00101,11B10102'], 'disp' : 1, 'help': 1, 'authKey' : os.getenv('WEATHER_AUTH_KEY')}
response = requests.get(url, params=params)
print(response.text)