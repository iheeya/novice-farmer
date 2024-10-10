import os
import cv2
import numpy as np
from tqdm import tqdm
import imgaug as ia
import imgaug.augmenters as iaa

# YOLO 형식 -> Pascal VOC 형식으로 변환
def Yolo2Coord(size, box):
    category = int(box[0])
    x_center, y_center, width, height = float(box[1]), float(box[2]), float(box[3]), float(box[4])
    img_width, img_height = size[0], size[1]
    x_min = int((x_center - width / 2.) * img_width)
    x_max = int((x_center + width / 2.) * img_width)
    y_min = int((y_center - height / 2.) * img_height)
    y_max = int((y_center + height / 2.) * img_height)
    return [category, x_min, y_min, x_max, y_max]

# Pascal VOC 형식 -> YOLO 형식으로 변환
def Pascal2Yolo(box, size):
    image_width, image_height = size[0], size[1]
    x_center = ((box[2] + box[0]) / 2) / image_width
    y_center = ((box[3] + box[1]) / 2) / image_height
    width = (box[2] - box[0]) / image_width
    height = (box[3] - box[1]) / image_height
    return [str(x_center), str(y_center), str(width), str(height)]

# 바운딩 박스 리스트로 변환
def after_box(bbs):
    before = []
    for i in range(len(bbs)):
        x_min = bbs[i][0][0]
        y_min = bbs[i][0][1]
        x_max = bbs[i][1][0]
        y_max = bbs[i][1][1]
        before.append([x_min, y_min, x_max, y_max])
    return before

# 경로 설정
train_img_dir = "C:/Users/SSAFY/Desktop/YoLo/train/image/"
train_label_dir = "C:/Users/SSAFY/Desktop/YoLo/train/label_yolo/"
output_img_dir = "C:/Users/SSAFY/Desktop/YoLo/train/resized_images/"
output_label_dir = "C:/Users/SSAFY/Desktop/YoLo/train/resized_labels/"

# 디렉토리 생성
os.makedirs(output_img_dir, exist_ok=True)
os.makedirs(output_label_dir, exist_ok=True)

# 리사이즈 크기 설정
size = 640

# 이미지와 라벨을 리사이즈
for img_file in tqdm(os.listdir(train_img_dir)):
    if img_file.endswith(".jpg") or img_file.endswith(".jpeg"):
        image_path = os.path.join(train_img_dir, img_file)
        label_path = os.path.join(train_label_dir, img_file.replace('.jpg', '.txt').replace('.jpeg', '.txt'))
        
        # 이미지 읽기
        image = cv2.imread(image_path)
        height, width = image.shape[:2]
        image_size = [width, height]
        
        # 바운딩 박스 읽기
        label_list = []
        with open(label_path, "r") as f:
            labs = f.readlines()
            for lab in labs:
                label_list.append(lab.split())
        
        # YOLO 형식 -> Pascal VOC 형식 변환
        new_bounding_boxes = [Yolo2Coord(image_size, box) for box in label_list]
        cate = str(new_bounding_boxes[0][0])
        
        # 바운딩 박스를 이미지 리사이즈 후 적용
        ia_bounding_boxes = [ia.BoundingBox(x1=box[1], y1=box[2], x2=box[3], y2=box[4]) for box in new_bounding_boxes]
        bbs = ia.BoundingBoxesOnImage(ia_bounding_boxes, shape=image.shape)
        
        seq = iaa.Resize({"height": size, "width": size})
        image_resized, bbs_resized = seq(image=image, bounding_boxes=bbs)
        
        # 이미지 저장
        resized_img_path = os.path.join(output_img_dir, f"resized_{img_file}")
        cv2.imwrite(resized_img_path, image_resized)
        
        # 리사이즈된 바운딩 박스 좌표 변환 후 YOLO 형식으로 저장
        resized_coords = after_box(bbs_resized)
        yolos = [Pascal2Yolo(box, [size, size]) for box in resized_coords]
        
        # 라벨 저장
        resized_label_path = os.path.join(output_label_dir, f"resized_{img_file.replace('.jpg', '.txt').replace('.jpeg', '.txt')}")
        with open(resized_label_path, "w") as f:
            for yolo in yolos:
                f.write(f"{cate} {' '.join(yolo)}\n")
