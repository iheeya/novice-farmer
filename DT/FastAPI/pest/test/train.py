import os
import torch
from yolov5 import train as yolo_train  # YOLOv5 라이브러리

def main():
    # 절대 경로 설정
    root_dir = r'C:\Users\SSAFY\Desktop\YoLo\train'
    image_dir = r'C:\Users\SSAFY\Desktop\YoLo\train\image'
    label_dir = r'C:\Users\SSAFY\Desktop\YoLo\train\label_yolo'

    # YOLO 학습 설정 파일 생성
    data_yaml = f'''
    train: {image_dir}  # 훈련 이미지 경로
    val: {image_dir}    # 검증 이미지 경로
    
    nc: 5  # 클래스 수
    names: ['background', 'normal', 'leaf_blight', 'leaf_mold', 'yellow_curl_virus']  # 클래스 이름
    '''

    # data.yaml 파일을 train 폴더 외부에 저장
    yaml_dir = r'C:\Users\SSAFY\Desktop\YoLo'
    yaml_path = os.path.join(yaml_dir, 'data.yaml')
    with open(yaml_path, 'w') as f:
        f.write(data_yaml)

    # YOLOv5 학습
    yolo_train.run(imgsz=640,  # 입력 이미지 크기
                   batch_size=8,  # 배치 크기
                   epochs=10,  # 에포크 수
                   data=yaml_path,  # 학습 데이터 경로 설정 (절대 경로로 수정)
                   weights='yolov5s.pt',  # 사전 학습된 가중치 파일
                   cache=True,  # 캐시 사용
                   device=0 if torch.cuda.is_available() else 'cpu')  # GPU or CPU 사용

if __name__ == '__main__':
    main()
    # V006_77_0_00_11_01_13_0_c06_20201209_0002_S01_2.jpg
    # V006_77_0_00_11_01_13_0_c06_20201209_0002_S01_2.txt
    # V006_77_0_00_11_01_13_0_c06_20201209_0002_S01_2.jpg.txt