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


def show_store_review_distribution_graph(dataframes):
    """
    Req. 1-3-1 전체 음식점의 리뷰 개수 분포를 그래프로 나타냅니다. 
    """
    stores = dataframes["stores"]
    review_counts = stores["review_cnt"]
    # review_counts = review_counts[review_counts > 0]
    review_count_distribution = Counter(review_counts)
    
    df = pd.DataFrame(list(review_count_distribution.items()), columns=["review_count", "store_count"])

    # 리뷰 개수에 따라 정렬
    df = df.sort_values(by="review_count")

    # barplot을 사용해 리뷰 개수 분포 시각화
    plt.figure(figsize=(10, 6))
    # 바 그래프를 생성하고, ax 객체에 저장
    ax = sns.barplot(x="review_count", y="store_count", data=df, palette="viridis")

    # 각 바 위에 값을 표시하는 부분 추가
    for p in ax.patches:
        ax.annotate(f'{int(p.get_height())}',  # 바의 높이를 텍스트로 표시
                    (p.get_x() + p.get_width() / 2., p.get_height()),  # 텍스트 위치 설정
                    ha='center', va='baseline',  # 텍스트 위치 조정
                    fontsize=10, color='black', xytext=(0, 5),  # 텍스트 스타일 설정
                    textcoords='offset points', rotation=45)

    # 그래프 제목과 축 레이블 설정
    plt.title("음식점 리뷰 개수 분포")
    plt.xlabel("리뷰 개수")
    plt.ylabel("음식점 수")

    # 그래프 표시
    plt.show()


def show_store_average_ratings_graph():
    """
    Req. 1-3-2 각 음식점의 평균 평점을 그래프로 나타냅니다.
    """
    raise NotImplementedError


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
    show_store_categories_graph(data)
    # Req. 3-1.
    show_store_review_distribution_graph(data)


if __name__ == "__main__":
    main()
