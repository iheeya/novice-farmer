import json
import pandas as pd

with open('data.json', 'r', encoding='utf-8') as file:
    data = json.load(file)

df = pd.DataFrame(data)
df = df[['name', 'review_cnt']]

sorted_df = df.sort_values(by='review_cnt', ascending=False)

print(sorted_df)