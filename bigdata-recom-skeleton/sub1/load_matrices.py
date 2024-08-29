import pandas as pd

def load_matrix(filename):
    """
    피클 파일에서 스파스 행렬을 불러옵니다.
    """
    matrix = pd.read_pickle(filename)
    return matrix

def display_matrix_info(matrix, name):
    """
    행렬의 정보를 출력합니다.
    """
    print(f"=== {name} ===")
    print(matrix)
    print("\n=== 행렬 요약 정보 ===")
    print(matrix.info())
    print("\n=====================\n")

def main():
    # 유저-아이템 행렬 로드 및 출력
    user_item_matrix = load_matrix('user_item_matrix.pkl')
    display_matrix_info(user_item_matrix, 'User-Item Matrix')

    # 유저-카테고리 행렬 로드 및 출력
    user_category_matrix = load_matrix('user_category_matrix.pkl')
    display_matrix_info(user_category_matrix, 'User-Category Matrix')

if __name__ == "__main__":
    main()
