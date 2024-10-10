from fastapi import APIRouter, HTTPException, File, UploadFile
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
yolov5_path = os.path.abspath('yolo/yolov5')
model_path = os.path.abspath('yolo/epoch32.pt')
model = torch.hub.load(yolov5_path, 'custom', path=model_path, source='local', force_reload=True)

# 라우터 생성
router = APIRouter()

# 요청 바디 모델 정의
class S3KeyRequest(BaseModel):
    s3_key: str

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

# 이미지 탐지 및 S3 업로드 API
@router.post("/plant/pest")
async def detect_s3_image(request: S3KeyRequest):
    try:
        # 1. S3에서 이미지 다운로드
        image = download_image_from_s3(request.s3_key)

        # 2. YOLOv5로 객체 탐지 수행
        results = detect_object(image)

        # 3. 탐지된 결과를 바운딩 박스와 함께 그리기
        image_with_boxes = draw_bounding_boxes(image, results)

        # 4. 수정된 이미지를 S3에 다시 업로드
        modified_s3_key = f"modified_{request.s3_key}"
        image_url = upload_image_to_s3(image_with_boxes, modified_s3_key)   

        # 5. 탐지된 객체 정보를 JSON 형태로 변환
        detections = [
            {
                "class_name": model.names[int(cls)],
                "confidence": float(conf),
                "box": {
                    "xmin": float(box[0]),
                    "ymin": float(box[1]),
                    "xmax": float(box[2]),
                    "ymax": float(box[3])
                }
            }
            for *box, conf, cls in results.xyxy[0]
        ]

        # 6. S3 키 및 탐지 결과 반환
        return {
            "s3_key": modified_s3_key,
            "image_url": image_url,
            "detections": detections
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

# 파일 업로드 API
@router.post("/upload/")
async def upload_to_s3(file: UploadFile = File(...)):
    try:
        # 파일 내용을 읽어서 S3에 업로드
        file_content = await file.read()
        file_key = f"uploads/{file.filename}"
        file_url = upload_image_to_s3(file_content, file_key)
        
        return {"file_url": file_url, "s3_key": file_key}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
