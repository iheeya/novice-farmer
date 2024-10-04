import os

# 경로 설정
image_dir = r'C:\\Users\\SSAFY\\Desktop\\YoLo\\train\\image'
label_dir = r'C:\\Users\\SSAFY\\Desktop\\YoLo\\train\\label_yolo'

# 이미지와 라벨 파일의 확장자 리스트
image_extensions = ['.jpg', '.jpeg']
label_extension = '.txt'

# 이미지 파일과 라벨 파일 목록 불러오기
image_files = [f for f in os.listdir(image_dir) if os.path.splitext(f)[1].lower() in image_extensions]
label_files = [f for f in os.listdir(label_dir) if f.endswith(label_extension)]

# 파일 이름에서 확장자를 제거한 이름을 비교
image_base_names = [os.path.splitext(f)[0] for f in image_files]
label_base_names = [os.path.splitext(f)[0] for f in label_files]

# 일치하지 않는 이미지 파일 삭제
for image_file in image_files:
    if os.path.splitext(image_file)[0] not in label_base_names:
        image_path = os.path.join(image_dir, image_file)
        print(f"Deleting image file: {image_path}")
        os.remove(image_path)

# 일치하지 않는 라벨 파일 삭제
for label_file in label_files:
    if os.path.splitext(label_file)[0] not in image_base_names:
        label_path = os.path.join(label_dir, label_file)
        print(f"Deleting label file: {label_path}")
        os.remove(label_path)
