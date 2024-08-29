import pandas as pd

# DUMP_FILE 경로 설정 (parse.py와 동일하게 설정해야 함)
DUMP_FILE = "../data/dump.pkl"

def load_dataframes():
    """피클 파일에서 데이터프레임 로드"""
    return pd.read_pickle(DUMP_FILE)

def main():
    data = load_dataframes()

    print("[음식점 데이터]")
    print(data["stores"].head())
    print("\n")

    print("[리뷰 데이터]")
    print(data["reviews"].head())

if __name__ == "__main__":
    main()
