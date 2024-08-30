import itertools
from collections import Counter
from parse import load_dataframes
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
from matplotlib.ticker import FuncFormatter
import folium


def thousands_formatter(x, pos):
    return f'{int(x):,}'

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
    plt.gca().yaxis.set_major_formatter(FuncFormatter(thousands_formatter))
    plt.title("음식점 카테고리 분포")
    plt.show()


def show_store_review_distribution_graph(dataframes):
    """
    Req. 1-3-1 전체 음식점의 리뷰 개수 분포를 그래프로 나타냅니다. 
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    scores_group = stores_reviews.groupby(["store", "store_name"])
    review_counts = scores_group.size()
    
    plt.figure(figsize=(10, 6))
    plt.scatter(review_counts, range(len(review_counts)), alpha=0.6)
    plt.title("전체 음식점 리뷰 개수 분포")
    plt.xlabel("리뷰 개수")
    plt.ylabel("음식점 인덱스")
    plt.gca().yaxis.set_major_formatter(FuncFormatter(thousands_formatter))
    plt.grid(True)
    plt.show()

    

def show_store_average_ratings_graph(dataframes):
    """
    Req. 1-3-2 각 음식점의 평균 평점을 그래프로 나타냅니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    scores_group = stores_reviews.groupby(["store", "store_name"])
    scores = scores_group.mean(numeric_only=True).round(2)
    scores = scores.sort_values(by='score', ascending=False).reset_index()
    
    plt.figure(figsize=(10, 6))
    sns.histplot(scores['score'], bins=20, kde=False)
    plt.title("평균 평점별 음식점 수 분포")
    plt.xlabel("평균 평점")
    plt.gca().yaxis.set_major_formatter(FuncFormatter(thousands_formatter))
    plt.ylabel("음식점 수")
    plt.grid(True)
    plt.show()
    
def show_user_review_distribution_graph(dataframes):
    """
    Req. 1-3-3 전체 유저의 리뷰 개수 분포를 그래프로 나타냅니다.
    """
    reviews = pd.DataFrame(dataframes['reviews'])
    # 각 사용자별 리뷰 개수 계산
    reviews_group = reviews.groupby('user').size().reset_index(name='review_count')
    review_count_dist = reviews_group.groupby('review_count').size().reset_index(name='user_count')
    max_review = review_count_dist['review_count'].max()
    
    plt.figure(figsize=(10, 6))
    plt.scatter(review_count_dist['user_count'], review_count_dist['review_count'], alpha=0.6, color='skyblue')
    plt.title('리뷰 개수별 유저 수 분포')
    plt.xlabel('유저 수')
    plt.ylabel('리뷰 개수')
    plt.gca().xaxis.set_major_formatter(FuncFormatter(thousands_formatter))
    plt.grid(True)
    plt.xlim(0, review_count_dist['user_count'].max() + 10)
    plt.ylim(0, max_review + 20)
    plt.xticks(range(0, review_count_dist['user_count'].max() + 1, 500))
    plt.show()


def show_user_age_gender_distribution_graph(dataframes):
    """
    Req. 1-3-4 전체 유저의 성별/나이대 분포를 그래프로 나타냅니다.
    """
    
    users = pd.DataFrame(dataframes['users'])
    
    plt.figure(figsize=(12, 6))

    plt.subplot(1, 2, 1)
    sns.countplot(x='gender', data=users, palette={'남': 'skyblue', '여': 'pink'}, width=0.5)
    plt.title('유저 성별 분포')
    plt.xlabel('성별')
    plt.gca().yaxis.set_major_formatter(FuncFormatter(thousands_formatter))
    plt.ylabel('유저 수')
    plt.yticks(range(0, users['gender'].value_counts().max() + 1, 5000))
    
    min_age, max_age = users['age'].min(), users['age'].max()
    plt.subplot(1, 2, 2)
    sns.histplot(x='age', data=users, bins=range(min_age, max_age, 1), kde=False, color='lightgreen')
    plt.title('유저 나이 분포')
    plt.xlabel('나이')
    plt.gca().yaxis.set_major_formatter(FuncFormatter(thousands_formatter))
    plt.ylabel('유저 수')
    plt.xlim(0, 100) 
    
    plt.tight_layout()
    plt.show()
    

def show_stores_distribution_graph(dataframes):
    """
    Req. 1-3-5 각 음식점의 위치 분포를 지도에 나타냅니다.
    """
    # 'stores'와 'reviews' 데이터프레임을 사용
    stores = pd.DataFrame(dataframes['stores'])
    reviews = pd.DataFrame(dataframes['reviews'])
    store_ratings = reviews.groupby('store')['score'].mean().reset_index(name='average_score')
    
    high_rated_stores = stores.merge(store_ratings, left_on='id', right_on='store')
    high_rated_stores = high_rated_stores[(high_rated_stores['average_score'] >= 4.9) & 
                                          (high_rated_stores['latitude'].notnull()) & 
                                          (high_rated_stores['longitude'].notnull())]
    
    map_center = [36.1136, 128.4178]  # 구미시 인동의 위도와 경도
    store_map = folium.Map(location=map_center, zoom_start=15)
    
    for idx, row in high_rated_stores.iterrows():
        folium.Marker(
            location=[row['latitude'], row['longitude']],
            popup=f"{row['store_name']} (평점: {row['average_score']:.1f})",
        ).add_to(store_map)
        
    store_map.save('인동 맛집 지도.html')
    store_map


def main():
    set_config()
    data = load_dataframes()
    show_store_categories_graph(data)
    show_store_review_distribution_graph(data)
    show_store_average_ratings_graph(data)
    show_user_review_distribution_graph(data)
    show_user_age_gender_distribution_graph(data)
    show_stores_distribution_graph(data)


if __name__ == "__main__":
    main()
