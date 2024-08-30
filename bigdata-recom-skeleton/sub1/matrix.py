import itertools
from collections import Counter
from parse import load_dataframes
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
from matplotlib.ticker import FuncFormatter
import folium
import os

def make_user_store_matrix(dataframes):
    '''
    유저-음식점 행렬 생성
    유저와 음식점을 축으로 하고 평점을 값으로 갖는 행렬을 만들어 저장.
    '''
    file_path = '유저-음식점 행렬.csv'
    
    if os.path.exists(file_path):
        os.remove(file_path)
        print(f"{file_path} 파일이 삭제되었습니다.")
        
    reviews = pd.DataFrame(dataframes['reviews'])
    
    user_store_matrix = reviews.pivot_table(index='user', columns='store', values='score')
    
    # NaN값 처리
    user_store_matrix = user_store_matrix.fillna(0)
    
    # 행렬 저장
    user_store_matrix.to_csv(file_path, index=False)
    print(f"{file_path} 파일이 생성되었습니다.")
    
    # 행렬 확인
    print(user_store_matrix.head())
    
    
def make_user_category_matrix(dataframes):
    '''
    유저-카테고리 행렬 생성
    유저와 음식점 카테고리를 축으로 하고 평점 평균을 값으로 갖는 행렬을 만들어 저장.
    '''
    reviews = pd.DataFrame(dataframes['reviews'])
    stores = pd.DataFrame(dataframes['stores'])
    
    store_avg_ratings = reviews.groupby('store_id')['rating'].mean().reset_index()
    store_avg_ratings.columns = ['store_id', 'average_rating']
    
    store_data_with_ratings = pd.merge(stores, store_avg_ratings, on='store_id')
    
    category_avg_ratings = store_data_with_ratings.groupby('category')['average_rating'].mean().reset_index()
    
    category_avg_ratings = category_avg_ratings.fillna(0)
    print(category_avg_ratings.head())
    category_avg_ratings.to_csv('유저-카테고리 행렬.csv', index=False)
    
    
def main():
    data = load_dataframes()
    make_user_store_matrix(data)
    make_user_category_matrix(data)


if __name__ == "__main__":
    main()