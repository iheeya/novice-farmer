import pandas as pd
from scipy.sparse import csr_matrix
from parse import load_dataframes

def create_user_item_matrix(dataframes):
    """
    유저-아이템 행렬 생성
    유저와 음식점을 축으로 하고 평점을 값으로 갖는 행렬을 만들어 저장합니다.
    """
    reviews = dataframes['reviews']
    
    # 유저-아이템 매트릭스 생성
    user_item_matrix = reviews.pivot_table(index='user', columns='store', values='score')
    
    # pandas sparse matrix로 변환하여 메모리 효율을 높입니다
    user_item_sparse_matrix = user_item_matrix.astype(pd.SparseDtype("float", pd.NA))
    
    return user_item_sparse_matrix

def create_user_category_matrix(dataframes):
    """
    유저-카테고리 행렬 생성
    유저와 음식점 카테고리를 축으로 하고 평점 평균을 값으로 갖는 행렬을 만들어 저장합니다.
    """
    reviews = dataframes['reviews']
    stores = dataframes['stores']
    
    # 음식점의 카테고리를 리뷰 데이터프레임에 추가
    reviews_with_category = reviews.merge(stores[['id', 'category']], left_on='store', right_on='id')
    
    # 카테고리를 분리하여 유저-카테고리 매트릭스 생성
    reviews_with_category['category'] = reviews_with_category['category'].str.split('|')
    reviews_exploded = reviews_with_category.explode('category')
    
    # 유저-카테고리 매트릭스 생성
    user_category_matrix = reviews_exploded.pivot_table(index='user', columns='category', values='score', aggfunc='mean')
    
    # pandas sparse matrix로 변환하여 메모리 효율을 높입니다
    user_category_sparse_matrix = user_category_matrix.astype(pd.SparseDtype("float", pd.NA))
    
    return user_category_sparse_matrix

def save_sparse_matrix(matrix, filename):
    matrix.to_pickle(filename)
    print(f"Sparse matrix saved to {filename}")

def main():
    # 데이터 로드
    data = load_dataframes()

    # 유저-아이템 행렬 생성 및 저장
    user_item_matrix = create_user_item_matrix(data)
    save_sparse_matrix(user_item_matrix, 'user_item_matrix.pkl')

    # 유저-카테고리 행렬 생성 및 저장
    user_category_matrix = create_user_category_matrix(data)
    save_sparse_matrix(user_category_matrix, 'user_category_matrix.pkl')

if __name__ == "__main__":
    main()
