from fastapi import FastAPI, File, UploadFile
from fastapi.responses import JSONResponse
import numpy as np
import tensorflow as tf
from PIL import Image
import io
import requests

app = FastAPI()

interpreter = tf.lite.Interpreter(model_path='tomato_test/model.tflite')
interpreter.allocate_tensors()

input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()

@app.post("/predict")
async def predict(file: UploadFile = File(...)):
    image = Image.open(io.BytesIO(await file.read()))

    img = image.resize((224, 224))
    img = np.array(img).astype(np.float32) / 255.0
    img = np.expand_dims(img, axis=0)

    interpreter.set_tensor(input_details[0]['index'], img)

    interpreter.invoke()

    output_data = interpreter.get_tensor(output_details[0]['index'])

    predicted_class = np.argmax(output_data)
    labels = ["Tomato - Pow...","Tomato - Hea...","Tomato - Ear...","Class 7"]
    predicted_label = labels[predicted_class]

    return JSONResponse(content={"predicted_label":predicted_label, "confidence":output_data.tolist()})
