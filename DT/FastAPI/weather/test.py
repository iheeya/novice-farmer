from dotenv import load_dotenv
from io import StringIO
import json, requests, os, csv, itertools

with open("./adm_district.csv", "r", encoding='utf-8') as file:
    data = csv.reader(file)
    for row in data:
        print(row)