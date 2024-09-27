from fastapi import FastAPI, HTTPException, APIRouter
from fastapi.responses import JSONResponse
import numpy as np
import tensorflow as tf
from PIL import Image
import io
import requests
from pydantic import BaseModel
from typing import Optional

app = FastAPI()

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
@app.post('/predict')
async def predict(data: dict):
    try:
        image_path = data.get('image_path')
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