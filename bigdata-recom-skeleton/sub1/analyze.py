from parse import load_dataframes
import pandas as pd
import shutil

def merge_store_reviews(dataframes):
    """
    음식점 정보와 리뷰 정보를 결합하여 단일 데이터프레임을 생성합니다.
    """
    return pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )

def sort_stores_by_score(dataframes, n=20, min_reviews=30):
    """
    Req. 1-2-1 각 음식점의 평균 평점을 계산하여 높은 평점의 음식점 순으로 `n`개의 음식점을 정렬하여 리턴합니다
    Req. 1-2-2 리뷰 개수가 `min_reviews` 미만인 음식점은 제외합니다.
    """
    stores_reviews = merge_store_reviews(dataframes)

    # 그룹화하여 각 음식점별로 리뷰 개수와 평균 평점 계산
    scores_group = stores_reviews.groupby(["store", "store_name"])
    scores = scores_group.agg(
        review_count=('score', 'count'), # 리뷰 개수를 세는 연산
        avg_score=('score', 'mean') # 평균 평점을 계산하는 연산
    )

    # 리뷰 개수가 min_reviews 이상인 음식점만 필터링
    scores = scores[scores['review_count'] >= min_reviews]

    # 평균 평점으로 내림차순 정렬
    scores = scores.sort_values(by='avg_score', ascending=False)

    return scores.head(n=n).reset_index()

def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """
    stores_reviews = merge_store_reviews(dataframes)

    # 음식점별로 그룹화하여 리뷰 개수 계산
    review_counts = stores_reviews.groupby(["store", "store_name"]).size()

    # 리뷰 개수로 내림차순 정렬
    review_counts = review_counts.sort_values(ascending=False)

    # 상위 `n`개의 음식점을 반환
    return review_counts.head(n=n).reset_index(name='review_count')

def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    # 리뷰 정보에서 유저별로 그룹화하여 리뷰 개수 계산
    user_review_counts = dataframes["reviews"].groupby("user").size()

    # 리뷰 개수로 내림차순 정렬
    user_review_counts = user_review_counts.sort_values(ascending=False)

    # 상위 `n`명의 유저를 반환
    return user_review_counts.head(n=n).reset_index(name='review_count')

def main():
    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w

    # 각각의 분석 결과 가져오기
    stores_most_scored = sort_stores_by_score(data)
    most_reviewed_stores = get_most_reviewed_stores(data)
    most_active_users = get_most_active_users(data)

    print("[최고 평점 음식점]")
    print(f"{separater}\n")
    for i, store in stores_most_scored.iterrows():
        print(
            "{rank}위: {store} (평점: {score:.2f}점)".format(
                rank=i + 1, store=store.store_name, score=store.avg_score
            )
        )
    print(f"\n{separater}\n\n")

    print("[리뷰가 가장 많은 음식점]")
    print(f"{separater}\n")
    for i, store in most_reviewed_stores.iterrows():
        print(
            "{rank}위: {store} (리뷰 개수: {count})".format(
                rank=i + 1, store=store.store_name, count=store.review_count
            )
        )
    print(f"\n{separater}\n\n")

    print("[리뷰를 가장 많이 작성한 유저]")
    print(f"{separater}\n")
    for i, user in most_active_users.iterrows():
        print(
            "{rank}위: 유저 {user} (리뷰 개수: {count})".format(
                rank=i + 1, user=user.user, count=user.review_count
            )
        )
    print(f"\n{separater}\n\n")

if __name__ == "__main__":
    main()
