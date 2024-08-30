from parse import load_dataframes
import pandas as pd


def create_user_store_matrix(dataframes):
    """
    Req. 4-1
    """
    reviews = dataframes["reviews"]

    # 유저-음식점 매트릭스 생성 (행: 유저, 열: 음식점, 값: 평점)
    user_store_matrix = reviews.pivot(
        index='user', columns='store', values='score')

    # 결측값(NaN)을 0으로 채우고 Sparse 행렬로 변환
    user_store_sparse_matrix = user_store_matrix.fillna(
        0).astype(pd.SparseDtype("float", 0.0))

    return user_store_sparse_matrix


def create_user_category_matrix(dataframes):
    """
    Req. 4-2
    """
    reviews = dataframes["reviews"]
    stores = dataframes["stores"]

    merged_data = pd.merge(
        reviews, stores[['id', 'category']], left_on='store', right_on='id', how='left')
    user_category_matrix = merged_data.pivot_table(
        index='user', columns='category', values='score', aggfunc='mean')
    user_category_sparse_matrix = user_category_matrix.fillna(
        0).astype(pd.SparseDtype("float", 0.0))

    return user_category_sparse_matrix


def main():
    data = load_dataframes()
    user_store_matrix = create_user_store_matrix(data)
    user_category_matrix = create_user_category_matrix(data)
    print(user_store_matrix.shape)
    print(user_category_matrix.shape)

if __name__ == "__main__":
    main()
