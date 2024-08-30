import itertools
from collections import Counter
import os
import webbrowser

import numpy as np
from parse import load_dataframes
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
# import geopandas as gpd
import folium

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DATA_DIR = os.path.join(BASE_DIR, "../data")
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")

def load_dataframes():
    return pd.read_pickle(DUMP_FILE)


def set_config():
    # 폰트, 그래프 색상 설정
    font_list = fm.findSystemFonts(fontpaths=None, fontext="ttf")
    if any(["notosanscjk" in font.lower() for font in font_list]):
        plt.rcParams["font.family"] = "Noto Sans CJK JP"
    else:
        if not any(["malgun" in font.lower() for font in font_list]):
            raise Exception(
                "Font missing, please install Noto Sans CJK or Malgun Gothic. If you're using ubuntu, try `sudo apt install fonts-noto-cjk`"
            )

        plt.rcParams["font.family"] = "Malgun Gothic"

    sns.set_palette(sns.color_palette("Spectral"))
    plt.rc("xtick", labelsize=6)


def show_store_categories_graph(dataframes, n=100):
    """
    Tutorial: 전체 음식점의 상위 `n`개 카테고리 분포를 그래프로 나타냅니다.
    """

    stores = dataframes["stores"]

    # 모든 카테고리를 1차원 리스트에 저장합니다
    categories = stores.category.apply(lambda c: c.split("|"))
    categories = itertools.chain.from_iterable(categories)

    # 카테고리가 없는 경우 / 상위 카테고리를 추출합니다
    categories = filter(lambda c: c != "", categories)
    categories_count = Counter(list(categories))
    best_categories = categories_count.most_common(n=n)
    df = pd.DataFrame(best_categories, columns=["category", "count"]).sort_values(
        by=["count"], ascending=False
    )

    # 그래프로 나타냅니다
    chart = sns.barplot(x="category", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 카테고리 분포")
    plt.show()


def show_store_review_distribution_graph(dataframes):
    """
    Req. 1-3-1 전체 음식점의 리뷰 개수 분포를 그래프로 나타냅니다. 
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )

    review_counts = stores_reviews.groupby("store").size().reset_index(name='review_count')

    plt.figure(figsize=(10, 6))
    plt.scatter(review_counts.index, review_counts['review_count'], alpha=0.5)
    plt.title('전체 음식점의 리뷰 개수 분포')
    plt.xlabel('음식점')
    plt.ylabel('리뷰 개수')
    plt.grid(True)
    plt.show()

    # raise NotImplementedError


def show_store_average_ratings_graph(dataframes):
    """
    Req. 1-3-2 각 음식점의 평균 평점을 그래프로 나타냅니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    
    avg_ratings = stores_reviews.groupby("store").agg({
        "score": "mean"
    }).reset_index().rename(columns={"score": "average_rating"})
    
    avg_ratings['store_index'] = avg_ratings.index
    
    # plt.figure(figsize=(10, 6))
    # plt.hist(avg_ratings['average_rating'], bins=20, color='blue', alpha=0.7)
    # plt.title('각 음식점의 평균 평점')
    # plt.xlabel('평균 평점')
    # plt.ylabel('음식점')
    # plt.grid(True)
    # plt.show()
    plt.figure(figsize=(10, 6))
    sns.kdeplot(avg_ratings['average_rating'], shade=True, color="blue")
    plt.title('각 음식점의 평균 평점')
    plt.xlabel('평균 평점')
    plt.ylabel('밀도')
    plt.grid(True)
    plt.show()
    # raise NotImplementedError


def show_user_review_distribution_graph(dataframes):
    """
    Req. 1-3-3 전체 유저의 리뷰 개수 분포를 그래프로 나타냅니다.
    """
    user_reviews = dataframes["reviews"].groupby("user_id").size().reset_index(name='review_count')
    
    # plt.figure(figsize=(10, 6))
    # sns.kdeplot(user_reviews['review_count'], shade=True, color="green")
    # plt.title('전체 유저의 리뷰 개수 분포')
    # plt.xlabel('리뷰 수')
    # plt.ylabel('밀도')
    # plt.grid(True)
    # plt.show()
    plt.figure(figsize=(10, 6))
    plt.scatter(user_reviews.index, user_reviews['review_count'], alpha=0.5, color="green")
    plt.title('전체 유저의 리뷰 개수 분포')
    plt.xlabel('유저')
    plt.ylabel('리뷰 수')
    plt.grid(True)
    plt.show()
    # raise NotImplementedError


def show_user_age_gender_distribution_graph(dataframes):
    """
    Req. 1-3-4 전체 유저의 성별/나이대 분포를 그래프로 나타냅니다.
    """
    user_info = dataframes["users"]
    user_info['age_group'] = (user_info['born_year'].astype(int) // 10) * 10
    
    age_gender_counts = user_info.groupby(['age_group', 'gender']).size().unstack().fillna(0)
    
    plt.figure(figsize=(10, 6))
    sns.heatmap(age_gender_counts, annot=True, fmt=".1f", cmap="YlGnBu", cbar=True)
    plt.title('전체 유저의 성별/나이대 분포')
    plt.xlabel('성별')
    plt.ylabel('나이대')
    plt.show()
    # raise NotImplementedError


def show_stores_distribution_graph(dataframes):
    """
    Req. 1-3-5 각 음식점의 위치 분포를 지도에 나타냅니다.
    """
    stores = dataframes["stores"]
    stores['latitude'] = stores['latitude'].astype(float)
    stores['longitude'] = stores['longitude'].astype(float)

    stores = stores.dropna(subset=['latitude', 'longitude'])

    map_korea = folium.Map(location=[36.5, 127.5], zoom_start=7)
    
    for idx, row in stores.iterrows():
        folium.Marker([row['latitude'], row['longitude']], 
                      popup=row['store_name']).add_to(map_korea)
    
    map_path = "stores_distribution_map.html"
    map_korea.save(map_path)

    browser_path = webbrowser.get('windows-default').name  
    webbrowser.register('chrome', None, webbrowser.BackgroundBrowser(browser_path))
    webbrowser.get('chrome').open('file://' + os.path.realpath(map_path))

def create_user_store_matrix(dataframes):
    reviews = []
    
    for store in dataframes['stores'].to_dict('records'):
        store_id = store["id"]
        review_list = store.get("review_list", [])
        if not review_list:
            continue 
        for review in review_list:
            user_id = review["writer_info"]["id"]
            score = review["review_info"]["score"]
            reviews.append([user_id, store_id, score])
    
    df_reviews = pd.DataFrame(reviews, columns=['user_id', 'store_id', 'score'])
    if df_reviews.empty:
        print("No reviews found in the data.")
        return pd.DataFrame()  

    user_store_matrix = df_reviews.pivot(index='user_id', columns='store_id', values='score')
    user_store_sparse = user_store_matrix.astype(pd.SparseDtype("float", np.nan))
    
    return user_store_sparse

def create_user_category_matrix(dataframes):
    user_category = []
    
    for store in dataframes['stores'].to_dict('records'):
        category_list = store.get("category_list", [])
        categories = [c["category"] for c in category_list]
        review_list = store.get("review_list", [])
        if not categories or not review_list:
            continue  
        for review in review_list:
            user_id = review["writer_info"]["id"]
            score = review["review_info"]["score"]
            for category in categories:
                user_category.append([user_id, category, score])
    
    df_user_category = pd.DataFrame(user_category, columns=['user_id', 'category', 'score'])
    if df_user_category.empty:
        print("No user-category data found in the data.")
        return pd.DataFrame()  
    
    user_category_matrix = df_user_category.groupby(['user_id', 'category']).mean().unstack().fillna(np.nan)
    user_category_sparse = user_category_matrix.astype(pd.SparseDtype("float", np.nan))
    
    return user_category_sparse

def main():
    set_config()
    data = load_dataframes()
    show_store_categories_graph(data)
    show_store_review_distribution_graph(data)
    show_store_average_ratings_graph(data)
    show_user_review_distribution_graph(data)
    show_user_age_gender_distribution_graph(data)
    show_stores_distribution_graph(data)
    user_store_sparse = create_user_store_matrix(data)
    print(user_store_sparse.head())
    print(user_store_sparse.info())
    user_category_sparse = create_user_category_matrix(data)
    print(user_category_sparse.head())
    print(user_category_sparse.info())

if __name__ == "__main__":
    main()
