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
    reviews = pd.DataFrame(dataframes['reviews'])
    
    user_store_matrix = reviews.pivot_table(index='user', columns='store', values='score')
    
    # NaN값 처리
    user_store_matrix = user_store_matrix.fillna(0)
    
    # 행렬 확인
    print(user_store_matrix.head())
    
    
def make_user_category_matrix(dataframes):
    '''
    유저-카테고리 행렬 생성
    유저와 음식점 카테고리를 축으로 하고 평점 평균을 값으로 갖는 행렬을 만들어 저장.
    '''
    
    reviews = pd.DataFrame(dataframes['reviews'])
    stores = pd.DataFrame(dataframes['stores'])
    
    store_avg_ratings = reviews.groupby('store')['score'].mean().reset_index()
    store_avg_ratings.columns = ['store', 'average_rating']
    
    store_data_with_ratings = pd.merge(stores, store_avg_ratings, left_on='id', right_on='store')
    
    user_category_matrix = reviews.merge(store_data_with_ratings[['store', 'category']], on='store')
    
    user_category_matrix = user_category_matrix.groupby(['user', 'category'])['score'].mean().unstack().fillna(0)
    
    print(user_category_matrix.head())
    
    
def main():
    data = load_dataframes()
    make_user_store_matrix(data)
    make_user_category_matrix(data)


if __name__ == "__main__":
    main()