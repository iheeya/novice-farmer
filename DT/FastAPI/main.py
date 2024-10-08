from fastapi import FastAPI, APIRouter
from contextlib import asynccontextmanager

# CORS 관리
from fastapi.middleware.cors import CORSMiddleware

# 함수 관리
from weather.weather import load_adminfo, load_areainfo, load_aswsinfo, load_curruent_special_weatherinfo, load_special_areainfo, load_valinfo

# 테스트
from growth.growth import update_farm_growth


# Router 관리
from growth.routers import router as growth_router
from pest.routers import router as pest_router
from recomm.routers import router as recomm_router
from todo.routers import router as todo_router
from weather.routers import router as weather_router

# Scheduler 관리
from setting.scheduler import start_scheduler

    
def start():
    load_adminfo(), load_areainfo(), load_aswsinfo(), load_special_areainfo(), load_valinfo(), load_curruent_special_weatherinfo()
    update_farm_growth()

# Scheduler 시작
# start_scheduler()
    
# 서버 시작 시 실행할 함수 등록
@asynccontextmanager
async def lifespan(app: FastAPI):
    # 예보구역 데이터, AWS 지점 데이터 등 필요한 함수들 자동 실행
    start()
    
    yield
    
app  = FastAPI(lifespan=lifespan)

# 허용할 도메인 목록
origins = [
    "http://j11d207.p.ssafy.io:8081",
    "http://localhost:8000",
    "http://j11d207.p.ssafy.io:5173",
    "https://j11d207.p.ssafy.io:8081",
    "https://localhost:8000",
    "https://j11d207.p.ssafy.io:5173",
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

router = APIRouter()

@app.get("/data-api/test")
def read_root():
    return {"message": "연결 되었습니다!"}


app.include_router(router)