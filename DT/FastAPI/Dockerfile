# 베이스 이미지로 Python 사용
FROM python:3.8

# 작업 디렉토리 설정
WORKDIR /app

# 필요한 파일 복사
COPY .env .env
COPY requirements.txt .

# 종속성 설치
RUN pip install --no-cache-dir --upgrade -r requirements.txt

# 앱 소스 복사
COPY . .

# 외부 접속 허용 포트번호
EXPOSE 8000

# Uvicorn을 이용해 FastAPI 서버 실행
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]