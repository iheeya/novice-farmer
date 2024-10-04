import os
import torch
from torch.utils.data import Dataset
from PIL import Image
import json

class TomatoDataset(Dataset):
    def __init__(self, root, transforms=None):
        self.root = root
        self.transforms = transforms
        self.imgs = sorted(os.listdir(os.path.join(root, "image")))
        self.labels = sorted(os.listdir(os.path.join(root, "label")))

    def __len__(self):
        return len(self.imgs)

    def __getitem__(self, idx):
        img_path = os.path.join(self.root, "image", self.imgs[idx])
        label_path = os.path.join(self.root, "label", self.labels[idx])

        img = Image.open(img_path).convert("RGB")
        with open(label_path, 'r') as f:
            label_data = json.load(f)

        boxes = []
        labels = []
        width = label_data['description']['width']
        height = label_data['description']['height']

        for point in label_data['annotations']['points']:
            xtl = point['xtl']
            ytl = point['ytl']
            xbr = point['xbr']
            ybr = point['ybr']
            boxes.append([xtl, ytl, xbr, ybr])

        disease_code = label_data['annotations']['disease']
        if disease_code == 0:
            labels = [1]  # 정상
        elif disease_code == 15:
            labels = [2]  # 잎마름병
        elif disease_code == 18:
            labels = [3]  # 잎곰팡이병
        elif disease_code == 19:
            labels = [4]  # 황화잎말이바이러스병
        else:
            labels = [0]  # 배경

        boxes = torch.as_tensor(boxes, dtype=torch.float32)
        labels = torch.as_tensor(labels, dtype=torch.int64)
        image_id = torch.tensor([idx])
        area = (boxes[:, 3] - boxes[:, 1]) * (boxes[:, 2] - boxes[:, 0])
        iscrowd = torch.zeros((len(labels),), dtype=torch.int64)

        target = {}
        target["boxes"] = boxes
        target["labels"] = labels
        target["image_id"] = image_id
        target["area"] = area
        target["iscrowd"] = iscrowd

        if self.transforms is not None:
            img, target = self.transforms(img, target)

        return img, target
