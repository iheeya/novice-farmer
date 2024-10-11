import json
import os
import re

# 클래스 정의 (0: 정상, 1: 고추탄저병, 2: 고추흰가루병, ...)
CLASS_MAPPING = {
    0: "고추정상",  # 정상
    1: "고추탄저병",  # 고추탄저병
    2: "고추흰가루병",  # 고추흰가루병
    3: "고추마일드모틀바이러스병",  # 고추마일드모틀바이러스병
    4: "고추점무늬병",  # 고추점무늬병
}

# 부위 코드 정의 (00: 구분 없음, 01: 열매, 02: 꽃, 03: 잎, 04: 가지, 05: 줄기, 06: 뿌리, 07: 해충)
PART_MAPPING = {
    '00': '구분 없음',
    '01': '열매',
    '02': '꽃',
    '03': '잎',
    '04': '가지',
    '05': '줄기',
    '06': '뿌리',
    '07': '해충',
}

# 질병과 부위를 조합하여 YOLO 클래스 ID를 생성합니다.
def generate_combined_class_mapping():
    combined_mapping = {}
    class_id = 0
    for disease_code, disease_name in CLASS_MAPPING.items():
        for part_code, part_name in PART_MAPPING.items():
            combined_class = f"{disease_name}_{part_name}"
            combined_mapping[f"{disease_code}-{part_code}"] = class_id
            class_id += 1
    return combined_mapping

# 조합된 클래스 ID 매핑 생성
COMBINED_CLASS_MAPPING = generate_combined_class_mapping()

# JSON으로 클래스 매핑 정보 저장
mapping_output_path = 'combined_class_mapping.json'
with open(mapping_output_path, 'w', encoding='utf-8') as json_file:
    json.dump(COMBINED_CLASS_MAPPING, json_file, ensure_ascii=False, indent=4)
print(f"Class mapping saved to {mapping_output_path}")

# 이미지 파일 이름 형식 검증을 위한 정규식 패턴
IMAGE_NAME_PATTERN = re.compile(r'^[A-Za-z0-9_]+\.jpg$')

def convert_to_yolo_format(json_file, output_dir, image_files):
    with open(json_file, 'r', encoding='utf-8') as f:
        data = json.load(f)

    # 이미지 파일 이름 검증
    img_name = data['description']['image']
    if img_name not in image_files:
        print(f"Image {img_name} not found in images directory. Skipping {json_file}.")
        return

    if not IMAGE_NAME_PATTERN.match(img_name):
        print(f"Invalid image name format: {img_name}. Skipping {json_file}.")
        return

    # 이미지 크기
    width = data['description']['width']
    height = data['description']['height']

    # 바운딩 박스 좌표 및 클래스
    disease_code = data['annotations']['disease']
    part_code = data['annotations'].get('part', '00')  # part 정보가 없으면 '00' 사용

    # 질병과 부위 조합에 해당하는 클래스 ID 조회
    class_key = f"{disease_code}-{part_code}"
    class_id = COMBINED_CLASS_MAPPING.get(class_key)
    if class_id is None:
        print(f"Unknown disease and part combination ({disease_code}, {part_code}) in {json_file}")
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
    yolo_format = f"{class_id} {x_center:.6f} {y_center:.6f} {box_width:.6f} {box_height:.6f}\n"

    # 라벨 파일은 이미지 파일 확장자를 제거하고, txt 확장자로 저장
    img_base_name = os.path.splitext(img_name)[0]
    label_path = os.path.join(output_dir, f"{img_base_name}.txt")

    # YOLO 형식으로 라벨 저장
    with open(label_path, 'w', encoding='utf-8') as out_file:
        out_file.write(yolo_format)
    print(f"Converted {json_file} to YOLO format: {label_path}")

    # JSON 파일 삭제
    os.remove(json_file)
    print(f"Deleted JSON file: {json_file}")

# 데이터 경로 설정
json_dir = 'C:\\Users\\SSAFY\\Desktop\\custom_data\\labels_input'  # JSON 파일들이 있는 디렉토리
output_dir = 'C:\\Users\\SSAFY\\Desktop\\custom_data\\labels'
image_dir = 'C:\\Users\\SSAFY\\Desktop\\custom_data\\images'  # 이미지 파일들이 있는 디렉토리

# 디렉토리 생성
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

# 이미지 파일 목록 (확장자까지 포함)
image_files = {f for f in os.listdir(image_dir) if f.endswith('.jpg')}

# JSON 파일들을 YOLO 형식으로 변환
json_files = [f for f in os.listdir(json_dir) if f.endswith('.json')]

# 모든 JSON 파일을 YOLO 형식으로 변환
for json_file in json_files:
    convert_to_yolo_format(os.path.join(json_dir, json_file), output_dir, image_files)
