# 가져올 이미지를 정의
FROM node:20 AS builder

# 경로 설정하기
WORKDIR /app

# package.json 워킹 디렉토리에 복사
COPY package.json package-lock.json ./

# 명령어 실행 (의존성 설치)
RUN npm install
# 애플리케이션 소스 코드 복사
COPY . .

EXPOSE 3000
# 환경 변수 설정
ENV HOST=0.0.0.0
ENTRYPOINT ["npm", "start"]
