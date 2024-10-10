# 베이스 이미지로 Python 사용
FROM python:3.8

# 작업 디렉토리 설정
WORKDIR /app

# yolo 폴더 복사 (yolo 폴더가 Dockerfile과 동일한 경로에 있다고 가정)
COPY yolo/ /app/yolo/

# 작업 디렉토리 이동
WORKDIR /app/yolo

# 종속성 설치 (yolo 폴더 내에 있는 requirements.txt를 사용)
RUN pip install --no-cache-dir --upgrade -r requirements.txt

# .env 파일 복사
COPY PEST/yolo/.env .env

# 외부 접속 허용 포트번호
EXPOSE 8000

# Uvicorn을 이용해 FastAPI 서버 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]