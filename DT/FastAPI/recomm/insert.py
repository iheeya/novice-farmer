# import pandas as pd
# from sqlalchemy.orm import Session
# from recomm.models import BJDCode
# from recomm.database import SessionLocal, Base, engine
# import sys
# import os

# sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

# Base.metadata.create_all(bind=engine)

# def insert_bjd_codes_from_csv(csv_file: str):
#     df = pd.read_csv(csv_file, encoding='cp949')

#     db = SessionLocal()
#     try:
#         for _, row in df.iterrows():
#             # 기존에 동일한 bjd_code가 있는지 확인
#             existing_entry = db.query(BJDCode).filter(BJDCode.bjd_code == row['법정동코드']).first()
#             if not existing_entry:
#                 # abolition 값을 True 또는 False로 설정 (폐지여부에 따라)
#                 abolition = row['폐지여부'] == '폐지'

#                 # 새로 삽입
#                 bjd_code_entry = BJDCode(
#                     bjd_code=row['법정동코드'],
#                     bjd_name=row['법정동명'],
#                     abolition=abolition  # 폐지여부 저장
#                 )
#                 db.add(bjd_code_entry)
#         db.commit()
#         print("BJD Code 데이터 삽입 완료")
#     except Exception as e:
#         db.rollback()
#         print(f"Error inserting BJD codes: {e}")
#     finally:
#         db.close()

# if __name__ == "__main__":
#     # bjd_code.csv 파일 경로
#     insert_bjd_codes_from_csv('bjd_code.csv')

