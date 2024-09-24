# main.py
from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "Hello, FastAPI!"}

@app.get("/items/{item_id}")
def read_item(item_id: int, q: str = None):
    return {"item_id": item_id, "q": q}

@app.get("/data-api/plant/growth")
def crops_growth():
    return {"message": "Crops Success"}

@app.get("/data-api/plant/pest")
def pest_diagnosis():
    return {"message": "Pest Success"}

