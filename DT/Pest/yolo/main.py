from fastapi import APIRouter, HTTPException, File, UploadFile, FastAPI
from pydantic import BaseModel
from dotenv import load_dotenv
import os
import torch
from PIL import Image, ImageDraw
import io
import boto3
from botocore.exceptions import NoCredentialsError
import pathlib

# pathlib.PosixPath 수정 (Windows용)
temp = pathlib.PosixPath
pathlib.PosixPath = pathlib.WindowsPath

# 환경 변수 로드
load_dotenv()

# S3 설정
AWS_ACCESS_KEY_ID = os.getenv('AWS_ACCESS_KEY_ID')
AWS_SECRET_ACCESS_KEY = os.getenv('AWS_SECRET_ACCESS_KEY')
AWS_REGION = os.getenv('AWS_REGION')
S3_BUCKET_NAME = os.getenv('AWS_BUCKET_NAME')

# S3 클라이언트 생성
s3 = boto3.client(
    's3',
    aws_access_key_id=AWS_ACCESS_KEY_ID,
    aws_secret_access_key=AWS_SECRET_ACCESS_KEY,
    region_name=AWS_REGION
)

# YOLOv5 모델 로드
yolov5_path = os.path.abspath('yolov5')
model_path = os.path.abspath('epoch32.pt')
model = torch.hub.load(yolov5_path, 'custom', path=model_path, source='local', force_reload=True)

# 라우터 생성
app = FastAPI()

# 요청 바디 모델 정의
class S3KeyRequest(BaseModel):
    imagePath: str

# 이미지 다운로드 함수
def download_image_from_s3(key: str):
    try:
        response = s3.get_object(Bucket=S3_BUCKET_NAME, Key=key)
        image_bytes = response['Body'].read()
        img = Image.open(io.BytesIO(image_bytes))
        return img
    except NoCredentialsError:
        raise HTTPException(status_code=403, detail="S3 credentials not available")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error fetching image from S3: {e}")

# YOLOv5로 객체 탐지 수행
def detect_object(image: Image.Image):
    results = model(image)
    return results

# 바운딩 박스 그리기
def draw_bounding_boxes(image: Image.Image, results):
    draw = ImageDraw.Draw(image)
    for *box, conf, cls in results.xyxy[0]:
        draw.rectangle(box, outline="red", width=2)
        draw.text((box[0], box[1]), f'{model.names[int(cls)]} {conf:.2f}', fill="red")
    return image

# S3에 이미지 업로드
def upload_image_to_s3(image: Image.Image, key: str):
    buffer = io.BytesIO()
    image.save(buffer, 'JPEG')
    buffer.seek(0)
    try:
        s3.upload_fileobj(buffer, S3_BUCKET_NAME, key, ExtraArgs={"ContentType": "image/jpeg"})
        image_url = f"https://{S3_BUCKET_NAME}.s3.amazonaws.com/{key}"
        return image_url
    except NoCredentialsError:
        raise HTTPException(status_code=403, detail="S3 credentials not available")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error uploading image to S3: {e}")

import logging

# 로그 설정
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

@app.post("/data-api/plant/pest")
async def detect_image_from_s3(request: S3KeyRequest):
    try:
        logger.info("Request received with imagePath: %s", request.imagePath)
        
        # 1. S3에서 이미지 다운로드
        image = download_image_from_s3(request.imagePath)
        logger.info("Image downloaded successfully from S3.")

        # 2. YOLOv5로 객체 탐지 수행
        results = detect_object(image)
        logger.info("Object detection completed.")

        # 3. 탐지된 결과를 바운딩 박스와 함께 그리기
        image_with_boxes = draw_bounding_boxes(image, results)
        logger.info("Bounding boxes drawn on image.")

        # 4. 수정된 이미지를 S3에 다시 업로드 (원래 S3 키 유지)
        modified_s3_key = request.imagePath
        _ = upload_image_to_s3(image_with_boxes, modified_s3_key)
        logger.info("Image uploaded to S3 with key: %s", modified_s3_key)

        # 5. 탐지된 객체 정보를 처리하여 병해충 여부 판단
        has_pest = len(results.xyxy[0]) > 0
        logger.info("Pest detection status: %s", has_pest)

        # 탐지된 객체가 없는 경우 처리
        if not has_pest:
            return {
                "hasPest": False,
                "pestInfo": {
                    "pestImagePath": modified_s3_key,
                    "pestName": None,
                    "pestDesc": None,
                    "pestCureDesc": None
                }
            }

        # 6. 탐지된 병해충 정보 설정
        pest_name = model.names[int(results.xyxy[0][0][-1])]
        pest_desc = "잎이 마르거나 곰팡이가 폈다"
        pest_cure_desc = "농약을 뿌립시다"

        # 7. 응답 데이터 구성 (S3 키를 사용)
        return {
            "hasPest": has_pest,
            "pestInfo": {
                "pestImagePath": modified_s3_key,
                "pestName": pest_name,
                "pestDesc": pest_desc,
                "pestCureDesc": pest_cure_desc
            }
        }
    except Exception as e:
        logger.error("An error occurred: %s", str(e))
        raise HTTPException(status_code=500, detail=str(e))

# 파일 업로드 API
@app.post("/data-api/upload/")
async def upload_to_s3(file: UploadFile = File(...)):
    try:
        # 파일 내용을 읽어서 S3에 업로드
        # file_content = await file.read()
        image = Image.open(io.BytesIO(await file.read()))
        file_key = f"pest/{file.filename}"
        file_url = upload_image_to_s3(image, file_key)
        
        return {"file_url": file_url, "s3_key": file_key}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

