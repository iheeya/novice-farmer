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
    scores_group = stores_reviews.groupby(["store", "store_name"])
    
    # 숫자만 사용하도록 수정, 소숫점 두자리까지만 계산하도록 수정.
    scores = scores_group.mean(numeric_only=True).round(2)
    
    # 리뷰 개수 계산
    review_counts = scores_group.size()
    scores['review_counts'] = review_counts
    
    # Req 2-1, 평균 평점이 높은 순으로 음식점 목록 정렬.
    scores = scores.sort_values(by='score', ascending=False)
    
    # Req 2-2, 리뷰 개수가 일정 개수 이하인 음식점 제외.
    scores = scores[scores['review_counts'] >= min_reviews]
    
    
    return scores.head(n=n).reset_index()


def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """
    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    scores_group = stores_reviews.groupby(["store", "store_name"])
    
    # 숫자만 사용하도록 수정, 소숫점 두자리까지만 계산하도록 수정.
    scores = scores_group.mean(numeric_only=True).round(2)
    
    # 리뷰 개수 계산
    review_counts = scores_group.size()
    scores['review_counts'] = review_counts
    
    # Req 2-3, 리뷰 개수 많은 순으로 음식점 정렬.
    scores = scores.sort_values(by='review_counts', ascending=False)
    
    return scores.head(n=n).reset_index()


def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    
    # Req 2-4, 리뷰 많이 작성한 순으로 유저 정렬.
    reviews = pd.DataFrame(dataframes['reviews'])
    
    # 각 사용자별 리뷰 개수 계산
    reviews_group = reviews.groupby('user').size().reset_index(name='review_count')

    # 리뷰를 많이 작성한 순으로 정렬
    review_user = reviews_group.sort_values('review_count', ascending=False)[:21]
    
    return review_user.head(n=n).reset_index()


def main():
    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w

    stores_most_scored = sort_stores_by_score(data)
    stores_most_reviewed = get_most_reviewed_stores(data)
    users_most_active = get_most_active_users(data)
    
    print("[최고 평점 음식점]")
    print(f"{separater}\n")
    for i, store in stores_most_scored.iterrows():
        print(
            "{rank}위: {store}({score}점)".format(
                rank=i + 1, store=store.store_name, score=store.score
            )
        )
    print(f"\n{separater}\n\n")
    
    print("[최고 리뷰가 많은 음식점]")
    print(f"{separater}\n")
    for i, store in stores_most_reviewed.iterrows():
        print(
            "{rank}위: {store}({score}점)".format(
                rank=i + 1, store=store.store_name, score=store.review_counts
            )
        )
    print(f"\n{separater}\n\n")
    
    print("[최고 활동적인 유저]")
    print(f"{separater}\n")
    for i, user in users_most_active.iterrows():
        print(
            "{rank}위: id - {user}({score}개)".format(
                rank=i + 1, user=user.user, score=user.review_count
            )
        )
    print(f"\n{separater}\n\n")


if __name__ == "__main__":
    main()
