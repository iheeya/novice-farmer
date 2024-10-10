import json
import os

# 클래스 정의 (0: 정상, 15: 잎마름병, 18: 잎곰팡이병, 19: 황화잎말이바이러스병)
CLASS_MAPPING = {
    0: 0,  # 정상
    15: 1,  # 잎마름병
    18: 2,  # 잎곰팡이병
    19: 3   # 황화잎말이바이러스병
}

def convert_to_yolo_format(json_file, output_dir):
    with open(json_file, 'r') as f:
        data = json.load(f)

    # 이미지 크기
    width = data['description']['width']
    height = data['description']['height']

    # 바운딩 박스 좌표 및 클래스
    disease_code = data['annotations']['disease']
    
    # disease_code에 대한 처리 (정상, 잎마름병, 잎곰팡이병, 황화잎말이바이러스병)
    if disease_code not in CLASS_MAPPING:
        print(f"Unknown disease code {disease_code} in {json_file}")
        return

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
    class_id = CLASS_MAPPING[disease_code]
    yolo_format = f"{class_id} {x_center:.6f} {y_center:.6f} {box_width:.6f} {box_height:.6f}\n"

    # 이미지 파일 이름 기반으로 라벨 저장 (확장자에 따라 처리)
    img_name = data['description']['image'].split('.')[0]
    
    # 라벨 파일은 이미지 파일 확장자를 제거하고, txt 확장자로 저장
    with open(os.path.join(output_dir, f"{img_name}.txt"), 'w') as out_file:
        out_file.write(yolo_format)

# 데이터 경로 설정
json_dir = 'C:\\Users\\SSAFY\\Desktop\\YoLo\\train\\label'  # JSON 파일들이 있는 디렉토리
output_dir = 'C:\\Users\\SSAFY\\Desktop\\YoLo\\train\\label_yolo'  # YOLO 형식 라벨 저장 디렉토리

# 디렉토리 생성
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

# JSON 파일들을 YOLO 형식으로 변환
json_files = [f for f in os.listdir(json_dir) if f.endswith('.json')]

# 모든 JSON 파일을 YOLO 형식으로 변환
for json_file in json_files:
    convert_to_yolo_format(os.path.join(json_dir, json_file), output_dir)
