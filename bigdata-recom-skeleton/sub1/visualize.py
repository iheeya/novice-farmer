import itertools
from collections import Counter
from parse import load_dataframes
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm


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


def show_store_categories_graph(dataframes, n=10):
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



def show_store_review_distribution_graph(dataframes ):
    """
    Req. 1-3-1 전체 음식점의 리뷰 개수 분포를 그래프로 나타냅니다. 
    """
    
    reviews = dataframes["reviews"]

    print(reviews)

     # 음식점별 리뷰 수를 계산합니다.
    review_counts = reviews.groupby('store').size().reset_index(name='count')

    # 리뷰 수가 많은 상위 n개 음식점을 선택합니다.
    #n = len(review_counts)
    n=10
    top_stores = review_counts.nlargest(n, 'count')

    # 그래프로 나타냅니다.
    chart = sns.barplot(x='store', y='count', data=top_stores)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 리뷰 개수 분포")
    plt.xlabel("음식점 ID")
    plt.ylabel("리뷰 개수")
    plt.show()


    친
    #raise NotImplementedError


def show_store_average_ratings_graph(dataframes):
    """
    Req. 1-3-2 각 음식점의 평균 평점을 그래프로 나타냅니다.
    """

    reviews = dataframes["reviews"]
    #print(reviews)
    
    # 음식점별 평균 평점을 계산합니다.
    average_ratings = reviews.groupby('store')['score'].mean().reset_index(name='average_score')

    # 그래프를 그립니다.
    chart = sns.barplot(x='store', y='average_score', data=average_ratings)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("음식점 평균 평점")
    plt.xlabel("음식점 ID")
    plt.ylabel("평균 평점")
    plt.show()
    
    
    
    #raise NotImplementedError


def show_user_review_distribution_graph(dataframes):
    """
    Req. 1-3-3 전체 유저의 리뷰 개수 분포를 그래프로 나타냅니다.
    """

    reviews = dataframes["reviews"]
    review_rating = reviews.groupby('store')['user'].size().reset_index(name='review_score')

    n=10
    top_stores = review_rating.nlargest(n, 'review_score')


      # 그래프를 그립니다.
    chart = sns.barplot(x='store', y='review_score', data=top_stores)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("유저의 음식점 리뷰 개수")
    plt.xlabel("사용자 ID")
    plt.ylabel("유저 리뷰 갯수")
    plt.show()




    raise NotImplementedError


def show_user_age_gender_distribution_graph(dataframes):
    """
    Req. 1-3-4 전체 유저의 성별/나이대 분포를 그래프로 나타냅니다.
    """

    user = dataframes["users"]
    #print(user)
     # 성별 및 나이별로 카운트
    age_gender_distribution = user.groupby(['gender', 'age']).size().reset_index(name='count')

    # 그래프로 나타냅니다.
    plt.figure(figsize=(12, 6))
    chart = sns.barplot(x='age', y='count', hue='gender', data=age_gender_distribution)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45)
    plt.title("유저 성별 및 나이 분포")
    plt.xlabel("나이")
    plt.ylabel("유저 수")
    plt.legend(title='성별')
    plt.show()



    #raise NotImplementedError


def show_stores_distribution_graph(dataframes):
    """
    Req. 1-3-5 각 음식점의 위치 분포를 지도에 나타냅니다.
    """
    raise NotImplementedError


def main():
    set_config()
    data = load_dataframes()
    #show_store_categories_graph(data)
    #print(data)
    #show_store_review_distribution_graph(data)
    #show_store_average_ratings_graph(data)
    #show_user_review_distribution_graph(data)
    show_user_age_gender_distribution_graph(data)


if __name__ == "__main__":
    main()
