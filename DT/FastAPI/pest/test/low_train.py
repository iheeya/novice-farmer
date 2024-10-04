import torch
from torch.utils.data import DataLoader, Subset
from engine import train_one_epoch, evaluate
from dataset import TomatoDataset
from transforms import get_transform
from model import get_model
import utils
import random
import os

if __name__ == '__main__':
    # Device 설정
    device = torch.device('cuda') if torch.cuda.is_available() else torch.device('cpu')

    # 클래스 수 정의 (0: 배경, 1: 정상, 2: 잎마름병, 3: 잎곰팡이병, 4: 황화잎말이바이러스병)
    num_classes = 5

    # 경로 설정 (train 폴더에서 일정량 샘플링)
    root_dir = "C:/Users/SSAFY/Desktop/RCNN/train"
    dataset = TomatoDataset(root=root_dir, transforms=get_transform(train=True))
    dataset_test = TomatoDataset(root=root_dir, transforms=get_transform(train=False))

    # 데이터셋 샘플링 (100개의 데이터만 사용)
    indices = random.sample(range(len(dataset)), 100)
    dataset_train = Subset(dataset, indices)

    # 테스트 데이터도 20개로 샘플링
    test_indices = random.sample(range(len(dataset_test)), 20)
    dataset_val = Subset(dataset_test, test_indices)

    # DataLoader 생성
    data_loader_train = DataLoader(dataset_train, batch_size=4, shuffle=True, num_workers=4, collate_fn=utils.collate_fn)
    data_loader_val = DataLoader(dataset_val, batch_size=1, shuffle=False, num_workers=4, collate_fn=utils.collate_fn)

    # 모델 가져오기
    model = get_model(num_classes)
    model.to(device)

    # 옵티마이저 및 학습률 스케줄러 설정
    params = [p for p in model.parameters() if p.requires_grad]
    optimizer = torch.optim.SGD(params, lr=0.005, momentum=0.9, weight_decay=0.0005)
    lr_scheduler = torch.optim.lr_scheduler.StepLR(optimizer, step_size=3, gamma=0.1)

    # 모델 저장 경로
    save_dir = 'low_models/'
    if not os.path.exists(save_dir):
        os.makedirs(save_dir)

    # 학습 루프 (에포크 5로 설정)
    num_epochs = 1
    for epoch in range(num_epochs):
        # 한 에포크 학습
        train_one_epoch(model, optimizer, data_loader_train, device, epoch, print_freq=10)
        lr_scheduler.step()
        
        # 모델 평가
        evaluate(model, data_loader_val, device=device)
        
        # 에포크 끝날 때마다 모델 저장
        model_save_path = os.path.join(save_dir, f'faster_rcnn_epoch_{epoch+1}.pth')
        torch.save(model.state_dict(), model_save_path)
        print(f"Saved model for epoch {epoch+1} at {model_save_path}")
