from fastapi import FastAPI

# CORS 관리
from fastapi.middleware.cors import CORSMiddleware

# Router 관리
from growth.routers import router as growth_router
from pest.routers import router as pest_router
from recomm.routers import router as recomm_router
from todo.routers import router as todo_router
from weather.routers import router as weather_router

# Scheduler 관리
from setting.scheduler import start_scheduler

if __name__ == '__main__':
    # Scheduler 시작
    start_scheduler()

app  = FastAPI()

# 허용할 도메인 목록
origins = [
    "http://j11d207.p.ssafy.io:8081",
    "http://localhost:8000",
    "http://j11d207.p.ssafy.io:5173",
]

# CORS Middelware 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(growth_router, prefix='/data-api', tags=['growth'])
app.include_router(pest_router, prefix='/data-api', tags=['pest'])
app.include_router(recomm_router, prefix='/data-api', tags=['recomm'])
app.include_router(todo_router, prefix='/data-api', tags=['todo'])
app.include_router(weather_router, prefix='/data-api', tags=['weather'])


@app.get("/data-api/test")
def read_root():
    return {"message": "연결 되었습니다!"}
