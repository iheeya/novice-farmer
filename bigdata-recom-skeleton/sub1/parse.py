import json
import pandas as pd
import os
import shutil

# 파일 경로 설정
DATA_DIR = "../data"
DATA_FILE = os.path.join(DATA_DIR, "data.json")
DUMP_FILE = os.path.join(DATA_DIR, "dump.pkl")

# 스키마 정의
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
    "review_count"  # 리뷰 개수
)

review_columns = (
    "id",  # 리뷰 고유번호
    "store",  # 음식점 고유번호
    "user_id",  # 유저 고유번호
    "user_gender",  # 유저 성별
    "user_born_year",  # 유저 출생 연도
    "score",  # 평점
    "content",  # 리뷰 내용
    "reg_time",  # 리뷰 등록 시간
)

menu_columns = (
    "store_id",
    "menu_name",
    "price",
)

bhour_columns = (
    "store_id",
    "type",
    "week_type",
    "mon",
    "tue",
    "wed",
    "thu",
    "fri",
    "sat",
    "sun",
    "start_time",
    "end_time",  
    "etc"  
)

user_columns = (
    "user_id",  # 유저 고유번호
    "gender",  # 성별
    "born_year"  # 태어난 해
)

def import_data(data_path=DATA_FILE):
    """
    음식점 데이터 파일을 읽어서 Pandas DataFrame 형태로 저장합니다.
    """

    try:
        with open(data_path, encoding="utf-8") as f:
            data = json.loads(f.read())
    except FileNotFoundError:
        print(f"`{data_path}` 가 존재하지 않습니다.")
        exit(1)
    except json.JSONDecodeError:
        print(f"`{data_path}` 파일이 올바른 JSON 형식이 아닙니다.")
        exit(1)

    stores = []  # 음식점 테이블
    reviews = []  # 리뷰 테이블
    menus = []  # 메뉴 테이블
    bhours = []  # 영업시간 테이블
    users = []  # 유저 테이블

    for d in data:
        try:
            categories = [c["category"] for c in d.get("category_list", [])]

            # 음식점 정보
            stores.append(
                [
                    d.get("id"),
                    d.get("name"),
                    d.get("branch"),
                    d.get("area"),
                    d.get("tel"),
                    d.get("address"),
                    d.get("latitude"),
                    d.get("longitude"),
                    "|".join(categories),
                    d.get("review_cnt", 0),  # 리뷰 개수
                ]
            )

            # 메뉴 정보
            for menu in d.get("menu_list", []):
                menus.append(
                    [
                        d.get("id"),
                        menu.get("menu"),
                        menu.get("price"),
                    ]
                )

            # 영업시간 정보
            for bhour in d.get("bhour_list", []):
                bhours.append(
                    [
                        d.get("id"),
                        bhour.get("type"),
                        bhour.get("week_type"),
                        bhour.get("mon"),
                        bhour.get("tue"),
                        bhour.get("wed"),
                        bhour.get("thu"),
                        bhour.get("fri"),
                        bhour.get("sat"),
                        bhour.get("sun"),
                        bhour.get("start_time"),
                        bhour.get("end_time"),
                        bhour.get("etc"),
                    ]
                )

            # 리뷰 및 유저 정보
            for review in d.get("review_list", []):
                r = review.get("review_info", {})
                u = review.get("writer_info", {})

                reviews.append(
                    [
                        r.get("id"),
                        d.get("id"),
                        u.get("id"),
                        u.get("gender"),
                        u.get("born_year"),
                        r.get("score"),
                        r.get("content"),
                        r.get("reg_time"),
                    ]
                )

                # 유저 정보 추가
                users.append(
                    [
                        u.get("id"),
                        u.get("gender"),
                        u.get("born_year"),
                    ]
                )

        except KeyError as e:
            print(f"필수 필드 {e}가 데이터에 없습니다: {d}")
            continue
        except Exception as e:
            print(f"데이터 파싱 중 오류 발생: {e}")
            continue

    store_frame = pd.DataFrame(data=stores, columns=store_columns)
    review_frame = pd.DataFrame(data=reviews, columns=review_columns)
    menu_frame = pd.DataFrame(data=menus, columns=menu_columns)
    bhour_frame = pd.DataFrame(data=bhours, columns=bhour_columns)
    user_frame = pd.DataFrame(data=users, columns=user_columns).drop_duplicates()  # 유저 정보 중복 제거

    return {
        "stores": store_frame,
        "reviews": review_frame,
        "menus": menu_frame,
        "bhours": bhour_frame,
        "users": user_frame,
    }

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

    # 각 데이터프레임 출력
    for key, df in data.items():
        print(f"[{key.capitalize()}]")  # 데이터프레임 이름 출력 (Stores, Reviews, Menus, Bhours, Users)
        print(f"{separater}\n")
        print(df.head())  # 각 데이터프레임의 첫 5행 출력
        print(f"\n{separater}\n\n")

if __name__ == "__main__":
    main()
