import requests
from datetime import datetime, timedelta
import os
from dotenv import load_dotenv
import csv
from io import StringIO
from itertools import islice

load_dotenv()

url = 'https://apihub.kma.go.kr/api/typ01/url/fct_shrt_reg.php'
params = {'tmfc' : 0, 'disp' : 1, 'authKey' : os.getenv('WEATHER_AUTH_KEY')}
response = requests.get(url, params=params)
csv_data = response.text
csv_reader = csv.reader(StringIO(csv_data))
csv_reader = islice(csv_reader, 11, None)

for row in csv_reader:
    row = list(row[0].split(' '))
    print(row)