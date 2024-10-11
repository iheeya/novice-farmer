import json
import os
import cv2
from tqdm import tqdm

# 클래스 정의 (0: 정상, 1: 고추탄저병, 2: 고추흰가루병, ...)
CLASS_MAPPING = {
    0: "고추정상",  # 정상
    1: "고추탄저병",  # 고추탄저병
    2: "고추흰가루병",  # 고추흰가루병
    3: "고추마일드모틀바이러스병",  # 고추마일드모틀바이러스병
    4: "고추점무늬병",  # 고추점무늬병
}

# JSON 파일을 YOLO 형식으로 변환
def convert_to_yolo_format(json_file, output_dir, image_files):
    with open(json_file, 'r', encoding='utf-8') as f:
        data = json.load(f)

    # 이미지 파일 이름 검증 (대소문자와 확장자를 무시하고 비교)
    img_name = data['description']['image']
    img_name_lower = img_name.lower()
    image_files_lower = {img.lower() for img in image_files}

    if img_name_lower not in image_files_lower:
        print(f"Image {img_name} not found in images directory. Skipping {json_file}.")
        return

    # 이미지 크기
    width = data['description']['width']
    height = data['description']['height']

    # 바운딩 박스 좌표 및 클래스
    disease_code = data['annotations']['disease']
    class_id = disease_code  # YOLO의 클래스 ID로 질병 코드를 직접 사용

    # 첫 번째 포인트만 사용 (포인트 리스트에서 첫 번째 값)
    points = data['annotations']['points'][0]
    xtl = points['xtl']
    ytl = points['ytl']
    xbr = points['xbr']
    ybr = points['ybr']

    # YOLO 형식으로 변환 (중심점 및 폭, 높이)
    x_center = (xtl + xbr) / 2 / width
    y_center = (ytl + ybr) / 2 / height
    box_width = (xbr - xtl) / width
    box_height = (ybr - ytl) / height

    # YOLO 형식: <class_id> <x_center> <y_center> <width> <height>
    yolo_format = f"{class_id} {x_center:.6f} {y_center:.6f} {box_width:.6f} {box_height:.6f}\n"

    # 라벨 파일은 이미지 파일 확장자를 제거하고, txt 확장자로 저장
    img_base_name = os.path.splitext(img_name)[0]
    label_path = os.path.join(output_dir, f"{img_base_name}.txt")

    # YOLO 형식으로 라벨 저장
    with open(label_path, 'w', encoding='utf-8') as out_file:
        out_file.write(yolo_format)
    print(f"Converted {json_file} to YOLO format: {label_path}")

# 리사이즈 및 변환 작업을 수행하는 함수
def resize_images_and_labels(image_dir, json_dir, output_img_dir, output_label_dir, size=640):
    os.makedirs(output_img_dir, exist_ok=True)
    os.makedirs(output_label_dir, exist_ok=True)

    # 이미지 파일 목록 (확장자까지 포함)
    image_files = {f for f in os.listdir(image_dir) if f.lower().endswith('.jpg')}
    json_files = [f for f in os.listdir(json_dir) if f.lower().endswith('.json')]

    # 모든 JSON 파일을 YOLO 형식으로 변환
    for json_file in tqdm(json_files, desc="Converting JSON to YOLO"):
        convert_to_yolo_format(os.path.join(json_dir, json_file), output_label_dir, image_files)

    # 이미지와 라벨을 리사이즈
    for img_file in tqdm(image_files, desc="Resizing images and labels"):
        image_path = os.path.join(image_dir, img_file)
        resized_img_path = os.path.join(output_img_dir, img_file)
        label_path = os.path.join(output_label_dir, img_file.replace('.jpg', '.txt').replace('.jpeg', '.txt'))

        # 이미 리사이즈된 이미지가 있는 경우, 크기 확인 후 건너뛰기
        if os.path.exists(resized_img_path):
            resized_image = cv2.imread(resized_img_path)
            h, w = resized_image.shape[:2]
            if h == size and w == size:
                print(f"Image {img_file} is already resized. Skipping.")
                continue

        # 이미지 읽기
        image = cv2.imread(image_path)
        height, width = image.shape[:2]
        
        # 이미지 리사이즈
        resized_image = cv2.resize(image, (size, size))
        cv2.imwrite(resized_img_path, resized_image)

        # 바운딩 박스 리사이즈 (YOLO 형식 유지)
        if os.path.exists(label_path):
            with open(label_path, 'r') as f:
                labels = f.readlines()

            resized_labels = []
            for label in labels:
                class_id, x_center, y_center, box_width, box_height = map(float, label.split())
                x_center /= width / size
                y_center /= height / size
                box_width /= width / size
                box_height /= height / size
                resized_labels.append(f"{int(class_id)} {x_center:.6f} {y_center:.6f} {box_width:.6f} {box_height:.6f}\n")

            # 리사이즈된 라벨 저장
            resized_label_path = os.path.join(output_label_dir, img_file.replace('.jpg', '.txt').replace('.jpeg', '.txt'))
            with open(resized_label_path, 'w') as f:
                f.writelines(resized_labels)
            print(f"Resized {img_file} and saved to {resized_label_path}")

# 데이터 경로 설정
image_dir = 'C:\\Users\\SSAFY\\Desktop\\custom_data\\resized_images'
json_dir = 'C:\\Users\\SSAFY\\Desktop\\custom_data\\labels_input'
output_img_dir = 'C:\\Users\\SSAFY\\Desktop\\custom_data\\resized_images'
output_label_dir = 'C:\\Users\\SSAFY\\Desktop\\custom_data\\resized_labels'

# 리사이즈 및 YOLO 변환 실행
resize_images_and_labels(image_dir, json_dir, output_img_dir, output_label_dir)
