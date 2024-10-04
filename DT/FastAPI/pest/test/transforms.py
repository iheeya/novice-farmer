from PIL import Image
import torch
import torchvision.transforms as T

class Compose(object):
    def __init__(self, transforms):
        self.transforms = transforms

    def __call__(self, image, target):
        for t in self.transforms:
            image, target = t(image, target)
        return image, target


class ToTensor(object):
    def __call__(self, image, target):
        return T.ToTensor()(image), target


class RandomHorizontalFlip(object):
    def __init__(self, flip_prob=0.5):
        self.flip_prob = flip_prob

    def __call__(self, image, target):
        # 이미지가 PIL 객체인지 확인
        if not isinstance(image, Image.Image):
            raise TypeError("Expected PIL Image but got {}".format(type(image)))

        if torch.rand(1) < self.flip_prob:
            image = T.functional.hflip(image)
            bbox = target["boxes"]
            width, height = image.size  # PIL.Image 객체일 때만 작동
            bbox[:, [0, 2]] = width - bbox[:, [2, 0]]
            target["boxes"] = bbox
        return image, target


def get_transform(train):
    transforms = []
    if train:
        transforms.append(RandomHorizontalFlip(0.5))
    transforms.append(ToTensor())  # ToTensor는 마지막에 호출됩니다.
    return Compose(transforms)
