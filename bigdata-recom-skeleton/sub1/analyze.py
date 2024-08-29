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
    filtered_stores = stores_reviews[stores_reviews["review_cnt"]
                                     >= min_reviews]
    # Req. 2-1
    scores_group = filtered_stores.groupby(
        ["store", "store_name"]).score.mean()

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
    scores_group = stores_reviews.groupby(
        ["store", "store_name"]).review_cnt.mean()
    filtered_stores = scores_group.sort_values(ascending=False).head(n=n)

    return filtered_stores.reset_index()


def get_most_active_users(dataframes, n=20):
    """
    가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    # 유저 데이터와 리뷰 데이터를 병합
    users_reviews = pd.merge(
        dataframes["users"],
        dataframes["reviews"],
        left_on="id",
        right_on="user",
        suffixes=("_user", "_review") # id col 이 겹쳐서 이름을 붙임
    )
    # user 열 삭제 (중복 방지)
    users_reviews.drop(columns=["user"], inplace=True)
    # 유저별 리뷰 수 계산
    review_counts = users_reviews.groupby(["id_user", "gender", "age"]).size().reset_index(name="review_count")
    # 리뷰 수 기준으로 정렬
    sorted_users = review_counts.sort_values(by="review_count", ascending=False).reset_index(drop=True)
    # 최상위 n명의 유저 선택
    return sorted_users.head(n)


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

    # 최대 리뷰 유저 -------------------------------------------------
    most_active_users = get_most_active_users(data)

    print("[최대 리뷰 유저]")
    print(f"{separater}\n")
    for k, user in most_active_users.iterrows():
        print(
            "{rank}위: {user}번 유저 (리뷰 개수 {count}개)".format(
                rank=k + 1, user=user["id_user"], count=user["review_count"] 
            )
        )
    print(f"\n{separater}\n\n")


if __name__ == "__main__":
    main()
