# main.py
from fastapi import FastAPI
import math

app = FastAPI()

@app.get("/data-api/test")
def read_root():
    return {"message": "Hello, FastAPI!"}

@app.get("/data-api/items/{item_id}")
def read_item(item_id: int, q: str = None):
    return {"item_id": item_id, "q": q}

@app.get("/data-api/plant/growth")
def crops_growth(): # 현재는 토마토에 대한 값만 계산 중.
    thi, tlow = 33.33, 7.22
    def docalcs_S(max_val, min_val):
        if min_val > thi:
            heat = thi - tlow
        else:
            if max_val <= tlow:
                heat = 0
            else:
                fk1 = 2 * tlow
                diff = max_val - min_val
                sum_val = max_val + min_val
                if min_val >= tlow:
                    heat = (sum_val - fk1) / 2
                else:
                    heat = sinec(sum_val, diff, fk1)
                if max_val > thi:
                    fk1 = 2 * thi
                    heat -= sinec(sum_val, diff, fk1)
        
        ddtmp = heat / 2
        return ddtmp

    def sinec(sum_val, diff, fk1):
        twopi = 6.28318530717959
        pihlf = 1.5707963267949
        d2 = fk1 - sum_val
        d3 = diff**2 - d2**2
        # 부동소수점 오차로 인한 sqrt 오류 방지
        if d3 < 0 and d3 > -1e-9:
            d3 = 0
        theta = math.atan2(d2, math.sqrt(d3))
        if d2 < 0 and theta > 0:
            theta -= 3.1416
        heat = (diff * math.cos(theta) - d2 * (pihlf - theta)) / twopi
        return heat
    
    tmax, tmin = 21.56, 14.95
    DD1 = 2*docalcs_S(tmax, tmin)
    tmep_tlow, temp_thi = tlow, tmin
    tlow, thi = thi, 2*thi - tlow
    DD2 = 2*docalcs_S(tmax, tmin)
    tlow, thi = tmep_tlow, temp_thi

    DDs = round((DD1-DD2) * (9/5), 2)
    return { 'degreeDay' : DDs}

from fastapi import FastAPI, HTTPException, APIRouter
from fastapi.responses import JSONResponse
import numpy as np
import tensorflow as tf
from PIL import Image
import io
import requests
from pydantic import BaseModel
from typing import Optional

# app = FastAPI()

# schemas
class PestInfo(BaseModel):
    pestImagePath: str
    pestName: str
    pestDesc: str
    pestCureDesc: str

class PestResponse(BaseModel):
    hasPest: bool
    pestInfo: Optional[PestInfo]

# utils
interpreter = tf.lite.Interpreter(model_path='tomato_test/model.tflite')
interpreter.allocate_tensors()

input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()

labels = ["Tomato - Powdery Mildew", "Tomato - Healthy", "Tomato - Early Blight", "Class 7"]

# router
@app.post('/data-api/plant/pest')
async def predict(data: dict):
    try:
        image_path = data.get('imagePath')
        if not image_path:
            raise HTTPException(status_code=400, detail="image_path is missing")
        
        image_response = requests.get(image_path)
        image = Image.open(io.BytesIO(image_response.content))

        img = image.resize((224, 224))
        img = np.array(img).astype(np.float32) / 255.0
        img = np.expand_dims(img, axis=0)
        
        interpreter.set_tensor(input_details[0]['index'], img)
        interpreter.invoke()

        output_data = interpreter.get_tensor(output_details[0]['index'])
        predicted_class = np.argmax(output_data)
        predicted_label = labels[predicted_class]

        has_pest = predicted_label != "Tomato - Healthy"
        
        if has_pest:
            pest_info = PestInfo(
                pestImagePath = image_path,
                pestName = predicted_label,
                pestDesc = '토마토에 나타나는 병입니다.',
                pestCureDesc = f'{predicted_label} 맞춤 농약을 뿌립시다.'
            )
            response = PestResponse(hasPest=has_pest, pestInfo=pest_info)
        else:
            response = PestResponse(hasPest=has_pest, pestInfo=None)

        return JSONResponse(content=response.dict())
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error processing image: {str(e)}")