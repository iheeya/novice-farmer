import torch
from torch.utils.data import DataLoader
from engine import train_one_epoch, evaluate
from dataset import TomatoDataset
from transforms import get_transform
from model import get_model
import utils

import os


if __name__ == '__main__':
    # Device 설정
    device = torch.device('cuda') if torch.cuda.is_available() else torch.device('cpu')

    # 클래스 수 정의 (0: 배경, 1: 정상, 2: 잎마름병, 3: 잎곰팡이병, 4: 황화잎말이바이러스병)
    num_classes = 5

    root_dir = "C:/Users/SSAFY/Desktop/RCNN/train"
    dataset = TomatoDataset(root=root_dir, transforms=get_transform(train=True))
    dataset_test = TomatoDataset(root=root_dir, transforms=get_transform(train=False))
    # Dataset 및 DataLoader 정의
    # dataset = TomatoDataset(root='RCNN/train', transforms=get_transform(train=True))
    # dataset_test = TomatoDataset(root='RCNN/train', transforms=get_transform(train=False))

    # 데이터셋 분할
    indices = torch.randperm(len(dataset)).tolist()
    dataset_train = torch.utils.data.Subset(dataset, indices[:-50])
    dataset_val = torch.utils.data.Subset(dataset_test, indices[-50:])

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

    # 학습 루프
    num_epochs = 10
    for epoch in range(num_epochs):
        train_one_epoch(model, optimizer, data_loader_train, device, epoch, print_freq=10)
        lr_scheduler.step()
        evaluate(model, data_loader_val, device=device)

    # 모델 저장
    torch.save(model.state_dict(), 'faster_rcnn_tomato.pth')
