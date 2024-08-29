import json
import os

# 데이터 파일 경로 설정
DATA_DIR = "../data"
DATA_FILE = os.path.join(DATA_DIR, "data.json")

def print_top_reviews(file_path, num_reviews=10):
    """
    JSON 파일에서 상위 `num_reviews`개의 리뷰 데이터를 출력합니다.
    
    :param file_path: JSON 파일의 경로
    :param num_reviews: 출력할 리뷰 개수 (기본값: 10)
    """
    try:
        with open(file_path, encoding="utf-8") as f:
            data = json.load(f)
            
            reviews = []
            for entry in data:
                if "review_list" in entry:
                    reviews.extend(entry["review_list"])
            
            print(f"총 리뷰 개수: {len(reviews)}")
            
            # 상위 `num_reviews`개의 리뷰 출력
            for i in range(min(num_reviews, len(reviews))):
                print(f"=== 리뷰 {i+1} ===")
                print(json.dumps(reviews[i], indent=2, ensure_ascii=False))
                print("\n" + "="*40 + "\n")

    except FileNotFoundError:
        print(f"`{file_path}` 파일이 존재하지 않습니다.")
    except json.JSONDecodeError as e:
        print(f"JSON 파일을 읽는 중 오류가 발생했습니다: {e}")

# JSON 파일에서 상위 10개의 리뷰 데이터를 출력
print_top_reviews(DATA_FILE)
