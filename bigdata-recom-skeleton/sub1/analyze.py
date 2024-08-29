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


    # 그룹별 리뷰 개수 계산
    review_counts = scores_group.size()

    # 리뷰 개수가 min_reviews 이상인 음식점만 선택
    filtered_scores_group = scores_group.filter(
        lambda x: review_counts[x.name] >= min_reviews
    )


    # 평균 평점 계산 및 정렬
    scores = filtered_scores_group.groupby(["store", "store_name"]).score.mean()
    scores = scores.sort_values(ascending=False)

  
    return scores.head(n=n).reset_index()




def get_most_reviewed_stores(dataframes, n=20):
    """
    Req. 1-2-3 가장 많은 리뷰를 받은 `n`개의 음식점을 정렬하여 리턴합니다
    """

    stores_reviews = pd.merge(
        dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    )
    review_group = stores_reviews.groupby(["store", "store_name"])

    
    # 그룹별 리뷰 개수 계산
    review_counts_group = review_group.size()


    most_reviewed_stores = review_counts_group.sort_values(ascending=False)
   

    return most_reviewed_stores.head(n=n).reset_index()



 


def get_most_active_users(dataframes, n=20):
    """
    Req. 1-2-4 가장 많은 리뷰를 작성한 `n`명의 유저를 정렬하여 리턴합니다.
    """
    # stores_reviews = pd.merge(
    #     dataframes["stores"], dataframes["reviews"], left_on="id", right_on="store"
    # )
    # review_group = stores_reviews.groupby(["store", "store_name"])

    reviews = pd.merge(
        dataframes["users"], dataframes["reviews"], left_on="id", right_on="user"
    )

    review_group = reviews.groupby(["user", "store"])

    # # 그룹별 리뷰 개수 계산
    # review_counts_group = review_group.size()

    user_counts_group = review_group.size()


    # most_reviewed_stores = review_counts_group.sort_values(ascending=False)
    most_reviewed_users = user_counts_group.sort_values(ascending=False)

    # print(f'most_reviewed_users: {most_reviewed_users}')

    return most_reviewed_users.head(n=n).reset_index()
    # return most_reviewed_stores.head(n=n).reset_index()


    raise NotImplementedError





def main():
    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w

    # print(f'data: {data}')

    stores_most_scored = sort_stores_by_score(data)
    get_most_reviewed_stores(data)
    get_most_active_users(data)

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
