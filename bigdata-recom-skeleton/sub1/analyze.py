import os
from parse import load_dataframes
import pandas as pd
import shutil

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DATA_DIR = os.path.join(BASE_DIR, "../data")
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")

def load_dataframes():
    return pd.read_pickle(DUMP_FILE)

def sort_stores_by_score(dataframes, n=20, min_reviews=30):
    """
    Req. 1-2-1 각 음식점의 평균 평점을 계산하여 높은 평점의 음식점 순으로 `n`개의 음식점을 정렬하여 리턴합니다
    Req. 1-2-2 리뷰 개수가 `min_reviews` 미만인 음식점은 제외합니다.
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    scores_group = stores_reviews.groupby(["store", "store_name"]).agg(
        score=('score', 'mean'),
        review_count=('score', 'count')
    )
    scores_filtered = scores_group[scores_group['review_count'] >= min_reviews]
    sorted_scores = scores_filtered.sort_values(by='score', ascending=False)
    return sorted_scores.head(n=n).reset_index()

def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    
    review_counts = stores_reviews.groupby(["store", "store_name"]).size().reset_index(name='review_count')

    most_reviewed_stores = review_counts.sort_values(by='review_count', ascending=False)
    
    return most_reviewed_stores.head(n=n)
    # raise NotImplementedError


def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    user_reviews = dataframes["reviews"].groupby(["user_id"]).size().reset_index(name='review_count')
    
    most_active_users = user_reviews.sort_values(by='review_count', ascending=False)
    
    return most_active_users.head(n=n)
    # raise NotImplementedError


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

    most_reviewed_stores = get_most_reviewed_stores(data)
    print("[최대 리뷰 작성 음식점]")
    print(f"{separater}\n")
    for i, store in most_reviewed_stores.iterrows():
        print(
            "{rank}위: {store}({reviews} 리뷰)".format(
                rank= i+1, store=store.store_name, reviews=store.review_count
            )
        )
    print(f"\n{separater}\n\n")

    most_active_users = get_most_active_users(data)
    print("[최대 리뷰 작성 유저]")
    print(f"{separater}\n")
    for i, user in most_active_users.iterrows():
        print(
            "{rank}위: User ID {user_id}({reviews} 리뷰)".format(
                rank=i + 1, user_id=user.user_id, reviews=user.review_count
            )
        )
    print(f"\n{separater}\n\n")


if __name__ == "__main__":
    main()
