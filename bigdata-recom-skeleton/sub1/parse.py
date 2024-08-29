import json
import pandas as pd
import os
import shutil

DATA_DIR = "../data"
DATA_FILE = os.path.join(DATA_DIR, "data.json")
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")

store_columns = (
    "id",  # 음식점 고유번호
    "store_name",  # 음식점 이름
    "branch",  # 음식점 지점 여부
    "area",  # 음식점 위치
    "tel",  # 음식점 번호
    "address",  # 음식점 주소
    "latitude",  # 음식점 위도
    "longitude",  # 음식점 경도
    "category",  # 음식점 카테고리
)

review_columns = (
    "id",  # 리뷰 고유번호
    "store",  # 음식점 고유번호
    "user",  # 유저 고유번호
    "score",  # 평점
    "content",  # 리뷰 내용
    "gender",  # 유저 성별
    "born_year",  # 유저 출생년도
    "reg_time",  # 리뷰 등록 시간
)


def import_data(data_path=DATA_FILE):
    """
    Req. 1-1-1 음식점 데이터 파일을 읽어서 Pandas DataFrame 형태로 저장합니다
    """
    try:
        with open(data_path, encoding="utf-8") as f:
            data = json.loads(f.read())
    except FileNotFoundError as e:
        print(f"`{data_path}` 가 존재하지 않습니다.")
        exit(1)
    except json.JSONDecodeError as e:
        print(f"JSON 파일을 파싱하는 중 오류가 발생했습니다: {e}")
        exit(1)

    stores = []  # 음식점 테이블
    reviews = []  # 리뷰 테이블

    for d in data:
        try:
            categories = [c["category"] for c in d["category_list"]]
            stores.append(
                [
                    d["id"],
                    d["name"],
                    d["branch"],
                    d["area"],
                    d["tel"],
                    d["address"],
                    (d["latitude"]),  
                    (d["longitude"]),  
                    "|".join(categories),
                ]
            )

            for review in d["review_list"]:
                r = review["review_info"]
                u = review["writer_info"]

                reviews.append(
                    [
                        r["id"],
                        d["id"],
                        u["id"],
                        r["score"],
                        r["content"],
                        u["gender"],  # 성별 추가
                        u["born_year"],  # 출생년도 추가
                        pd.to_datetime(r["reg_time"]),  # 리뷰 등록 시간을 datetime으로 변환
                    ]
                )
        except KeyError as e:
            print(f"필수 키 {e}가 누락되었습니다.")
            continue
        except ValueError as e:
            print(f"잘못된 데이터 형식이 감지되었습니다: {e}")
            continue

    store_frame = pd.DataFrame(data=stores, columns=store_columns)
    review_frame = pd.DataFrame(data=reviews, columns=review_columns)

    return {"stores": store_frame, "reviews": review_frame}

def dump_dataframes(dataframes):
    pd.to_pickle(dataframes, DUMP_FILE)

def load_dataframes():
    return pd.read_pickle(DUMP_FILE)

def main():

    print("[*] Parsing data...")
    data = import_data()
    print("[+] Done")

    print("[*] Dumping data...")
    dump_dataframes(data)
    print("[+] Done\n")

    data = load_dataframes()

    term_w = shutil.get_terminal_size()[0] - 1
    separater = "-" * term_w

    print("[음식점]")
    print(f"{separater}\n")
    print(data["stores"].head())
    print(f"\n{separater}\n\n")

    print("[리뷰]")
    print(f"{separater}\n")
    print(data["reviews"].head())
    print(f"\n{separater}\n\n")

if __name__ == "__main__":
    main()
