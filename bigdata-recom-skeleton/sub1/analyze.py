from parse import load_dataframes
import pandas as pd
import shutil


def sort_stores_by_score(dataframes, n=20, min_reviews=30):
    """
    Req. 1-2-1 각 음식점의 평균 평점을 계산하여 높은 평점의 음식점 순으로 `n`개의 음식점을 정렬하여 리턴합니다
    Req. 1-2-2 리뷰 개수가 `min_reviews` 미만인 음식점은 제외합니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    
    # Req. 2-2
    filtered_stores = stores_reviews[stores_reviews["review_cnt"]>=min_reviews]
    # Req. 2-1
    scores_group = filtered_stores.groupby(["store", "store_name"]).score.mean()
    
    sorted_scores = scores_group.sort_values(ascending=False).head(n=n)
    
    return sorted_scores.reset_index()


def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """
    # Req. 2-3
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    scores_group = stores_reviews.groupby(["store", "store_name"]).review_cnt.mean()
    filtered_stores = scores_group.sort_values(ascending=False).head(n=n)
    
    return filtered_stores.reset_index()


def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    raise NotImplementedError


def main():
    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w
    # 최고 평점 음식점 -----------------------------------------------
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
    
    # 최대 리뷰 음식점 -----------------------------------------------
    stores_most_reviewed = get_most_reviewed_stores(data)
    
    print("[최대 리뷰 음식점]")
    print(f"{separater}\n")
    for j, store in stores_most_reviewed.iterrows():
        print(
            "{rank}위: {store}(리뷰 개수 {count}개)".format(
                rank=j + 1, store=store.store_name, count=store.review_cnt
            )
        )
    print(f"\n{separater}\n\n")


if __name__ == "__main__":
    main()
