import itertools
from collections import Counter
from parse import load_dataframes
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
import folium

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
    stores = dataframes["stores"]
    categories = stores.category.apply(lambda c: c.split("|"))
    categories = itertools.chain.from_iterable(categories)
    categories = filter(lambda c: c != "", categories)
    categories_count = Counter(list(categories))
    best_categories = categories_count.most_common(n=n)
    df = pd.DataFrame(best_categories, columns=["category", "count"]).sort_values(by=["count"], ascending=False)
    chart = sns.barplot(x="category", y="count", data=df)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 카테고리 분포")
    plt.show()

def show_store_review_distribution_graph(dataframes):
    stores_reviews = pd.merge(dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store")
    review_counts = stores_reviews.groupby("store")["id_y"].count()  
    plt.figure(figsize=(10, 6))
    plt.hist(review_counts, bins=30, color='skyblue', edgecolor='black')
    plt.title('음식점별 리뷰 개수 분포')
    plt.xlabel('리뷰 개수')
    plt.ylabel('음식점 수')
    plt.grid(axis='y')
    plt.show()

def show_store_average_ratings_graph(dataframes):
    stores_reviews = pd.merge(dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store")
    avg_ratings = stores_reviews.groupby("store")["score"].mean()
    plt.figure(figsize=(10, 6))
    plt.hist(avg_ratings, bins=30, color='salmon', edgecolor='black')
    plt.title('음식점별 평균 평점 분포')
    plt.xlabel('평균 평점')
    plt.ylabel('음식점 수')
    plt.grid(axis='y')
    plt.show()

def show_user_review_distribution_graph(dataframes):
    user_review_counts = dataframes["reviews"].groupby("user")["id"].count()
    plt.figure(figsize=(10, 6))
    plt.hist(user_review_counts, bins=30, color='lightgreen', edgecolor='black')
    plt.title('유저별 리뷰 개수 분포')
    plt.xlabel('리뷰 개수')
    plt.ylabel('유저 수')
    plt.grid(axis='y')
    plt.show()

def show_user_age_gender_distribution_graph(dataframes):
    reviews = dataframes["reviews"]
    user_info = reviews[['user', 'gender', 'born_year']].drop_duplicates()  # 'id'가 아닌 'user' 사용
    current_year = pd.Timestamp.now().year
    user_info['age'] = current_year - user_info['born_year'].astype(int)
    user_info['age_group'] = pd.cut(
        user_info['age'],
        bins=[10, 20, 30, 40, 50, 60, 100],
        labels=['10대', '20대', '30대', '40대', '50대', '60대 이상']
    )
    plt.figure(figsize=(12, 6))
    sns.countplot(data=user_info, x='age_group', hue='gender', palette='pastel')
    plt.title('유저 성별/나이대 분포')
    plt.xlabel('나이대')
    plt.ylabel('유저 수')
    plt.legend(title='성별')
    plt.show()

def show_stores_distribution_graph(dataframes):
    stores = dataframes["stores"]
    reviews = dataframes["reviews"]
    store_reviews = reviews.groupby('store').agg(
        review_count=('id', 'count'),
        avg_score=('score', 'mean')
    ).reset_index()
    
    filtered_stores = stores.merge(store_reviews, left_on='id', right_on='store')
    filtered_stores = filtered_stores[
        (filtered_stores['review_count'] >= 20) &
        (filtered_stores['avg_score'] >= 4.0)
    ]
    center_location = [37.5665, 126.9780]
    m = folium.Map(location=center_location, zoom_start=12)
    for idx, row in filtered_stores.iterrows():
        folium.Marker(
            location=[row['latitude'], row['longitude']],
            popup=(
                f"{row['store_name']} ({row['category']})<br>"
                f"평점: {row['avg_score']:.1f} (리뷰 {row['review_count']}개)"
            ),
            icon=folium.Icon(color='blue', icon='info-sign')
        ).add_to(m)
    m.save('stores_distribution_map.html')
    print("지도 저장 완료: stores_distribution_map.html")
    return m

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
