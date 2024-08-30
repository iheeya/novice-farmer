from parse import load_dataframes
import pandas as pd
import shutil
import os


def sort_stores_by_score(dataframes, n=20, min_reviews=30):
    """
    Req. 1-2-1 각 음식점의 평균 평점을 계산하여 높은 평점의 음식점 순으로 `n`개의 음식점을 정렬하여 리턴합니다
    Req. 1-2-2 리뷰 개수가 `min_reviews` 미만인 음식점은 제외합니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    
    # 최소 리뷰 개수 필터링
    # 리뷰 개수 기준 음식점 정렬
    review_counts = stores_reviews.groupby("store").size()
    stores_with_more_min_reviews_ind = review_counts[review_counts >= min_reviews].index

    filtered_stores = stores_reviews[stores_reviews["store"].isin(stores_with_more_min_reviews_ind)]

    sorted_review_counts = review_counts[review_counts >= min_reviews].sort_values(ascending=False)

    filtered_stores = filtered_stores[filtered_stores["store"].isin(sorted_review_counts.index)]
    filtered_stores = filtered_stores.set_index("store").loc[sorted_review_counts.index].reset_index()

    # scores_group = stores_reviews.groupby(["store", "store_name"])
    scores_group = filtered_stores.groupby(["store", "store_name"])

    scores = scores_group.score.mean()
    # 평점이 높은 음식점의 목록
    scores = scores.sort_values(ascending=False)

    return scores.head(n=n).reset_index()


def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    review_counts = stores_reviews.groupby("store").size()
    stores_ind = review_counts.index
    
    sorted_review_counts = review_counts.sort_values(ascending=False)
    
    stores = sorted_review_counts[sorted_review_counts["store"].isin(stores_ind)]

    print(stores.head(n=n))

    return stores.head(n=n)

def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    review_counts = stores_reviews.groupby("store").size()
    stores_ind = review_counts.index
    
    sorted_review_counts = review_counts.sort_values(ascending=False)
    
    stores = sorted_review_counts[sorted_review_counts["store"].isin(stores_ind)]

    print(stores.head(n=n))

    return stores.head(n=n)


def main():
    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w

    stores_most_scored = sort_stores_by_score(data)

    print("[최고 평점 음식점]")
    print(f"{separater}\n")
    for i, store in stores_most_scored.iterrows():
        print(
            "{rank}위: {store}({score}점)".format(
                rank=i + 1, store=store.store_name, score=store.score
            )
        )
    print(f"\n{separater}\n\n")


if __name__ == "__main__":
    main()
