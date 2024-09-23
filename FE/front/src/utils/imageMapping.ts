// 텃밭 위치에 따른 이미지 경로 매핑 함수
export const getImageForLocation = (placeId: number): string => {
  try {
    // placeId에 맞는 이미지 경로를 동적으로 불러옴
    return require(`../assets/images/farms/${placeId}.png`);
  } catch (error) {
    // 해당 placeId 이미지가 없을 경우 기본 이미지를 반환
    return require('../assets/images/farms/farm.png');
  }
};



// 작물 이름에 따른 이미지 경로 매핑 함수
export const getImageForCrop = (cropName: string): string => {
  try {
    switch (cropName) {
      case '토마토':
        return require('../assets/images/plants/Tomato.png');
      case '상추':
        return require('../assets/images/plants/Lettuce.png');
      case '바질':
        return require('../assets/images/plants/Basil.png');
      case '알로에':
        return require('../assets/images/plants/Aloe.png');
      case '로즈마리':
        return require('../assets/images/plants/Rosemary.png');
      case '딸기':
        return require('../assets/images/plants/Strawberry.png');
      default:
        return require('../assets/images/plants/Default.png');
    }
  } catch (error) {
    return require('../assets/images/plants/Default.png');
  }
};

// 날씨 제목에 따른 이미지 경로 매핑 함수
export const getImageForWeather = (title: string): string => {
  try {
    switch (title) {
      case '폭우주의보':
        return require('../assets/images/weathers/Rain.png');
      case '폭염특보':
        return require('../assets/images/weathers/Hot.png');
      case '폭설주의보':
        return require('../assets/images/weathers/Snow.png');
      default:
        return require('../assets/images/weathers/Cloudy.png');
    }
  } catch (error) {
    return require('../assets/images/weathers/Cloudy.png');
  }
};
