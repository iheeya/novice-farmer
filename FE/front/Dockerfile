# 가져올 이미지를 정의
FROM node:20 AS builder

# 경로 설정하기
WORKDIR /app

# package.json 워킹 디렉토리에 복사
COPY package.json package-lock.json ./

# 명령어 실행 (의존성 설치)
RUN npm install

# 나머지 파일 복사
COPY . .

# 빌드 명령어 실행
RUN npm run build

# /app/build의 내용 확인
RUN ls -al /app/build

# 이 부분에서 애플리케이션을 실행할 수 있습니다. 
# 예를 들어, React 앱의 경우, build 폴더를 정적 파일로 제공할 수 있는 서버를 사용할 수 있습니다.
# 아래는 간단한 예시로 serve 패키지를 사용하는 경우입니다.
FROM node:20

WORKDIR /app

COPY --from=builder /app/build ./build

# serve 패키지를 설치
RUN npm install -g serve

# serve로 애플리케이션 실행
CMD ["serve", "-s", "build"]
