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


def show_store_review_distribution_graph(dataframes, n = 100):
    """
    Req. 1-3-1 전체 음식점의 리뷰 개수 분포를 그래프로 나타냅니다. 
    """

    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    review_group = stores_reviews.groupby(["store", "store_name"])

    
    # 그룹별 리뷰 개수 계산
    review_counts_group = review_group.size()


    most_reviewed_stores = review_counts_group.sort_values(ascending=False)
    most_reviewed_stores = most_reviewed_stores.reset_index(name='review_cnt')

     # 상위 n개 음식점만 선택
    top_stores = most_reviewed_stores.head(n)
    
    # 그래프 그리기
    plt.figure()
    chart = sns.barplot(x="store_name", y="review_cnt", data=top_stores)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45, ha='right')
    plt.title("음식점 리뷰 개수 분포".format(n))
    plt.xlabel("음식점")
    plt.ylabel("리뷰 개수")
    plt.show()



def show_store_average_ratings_graph(dataframes, n= 100):
    """
    Req. 1-3-2 각 음식점의 평균 평점을 그래프로 나타냅니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    scores_group = stores_reviews.groupby(["store", "store_name"])

    # 평균 평점 계산 및 정렬
    scores = scores_group.score.mean()
    scores = scores.reset_index(name='score')

    print(f'scores: {scores}')
    top_scores = scores.head(n)

    # 그래프 그리기
    plt.figure()
    chart = sns.barplot(x="store_name", y="score", data=top_scores)
    chart.set_xticklabels(chart.get_xticklabels(), rotation=45, ha='right')
    plt.title("음식점 평균 평점 그래프".format(n))
    plt.xlabel("음식점")
    plt.ylabel("평균 평점")
    plt.show()


def show_user_review_distribution_graph(dataframes):
    """
    Req. 1-3-3 전체 유저의 리뷰 개수 분포를 그래프로 나타냅니다.
    """
    raise NotImplementedError


def show_user_age_gender_distribution_graph(dataframes):
    """
    Req. 1-3-4 전체 유저의 성별/나이대 분포를 그래프로 나타냅니다.
    """
    raise NotImplementedError


def show_stores_distribution_graph(dataframes):
    """
    Req. 1-3-5 각 음식점의 위치 분포를 지도에 나타냅니다.
    """
    raise NotImplementedError


def main():
    set_config()
    data = load_dataframes()
    # print(data)
    show_store_categories_graph(data)
    show_store_review_distribution_graph(data)
    show_store_average_ratings_graph(data)


if __name__ == "__main__":
    main()
