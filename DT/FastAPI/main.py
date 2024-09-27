from fastapi import FastAPI

app = FastAPI()

@app.get("/data-api/test")
def read_root():
    return {"message": "연결 되었습니다!"}
