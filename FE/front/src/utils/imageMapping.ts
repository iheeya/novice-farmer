// 텃밭 위치에 따른 이미지 경로 매핑 함수
export const getImageForLocation = (placeId: number): string => {
  try {
    // placeId에 맞는 이미지 경로를 동적으로 불러옴
    return require(`../assets/img/farms/${placeId}.png`);
  } catch (error) {
    // 해당 placeId 이미지가 없을 경우 기본 이미지를 반환
    return require('../assets/img/farms/farm.png');
  }
};


// 작물 이름에 따른 이미지 경로 매핑 함수
export const getImageForCrop = (cropName: string): string => {
  try {
    switch (cropName) {
      case '토마토':
        return require('../assets/img/plants/Tomato.png');
      case '상추':
        return require('../assets/img/plants/Lettuce.png');
      case '바질':
        return require('../assets/img/plants/Basil.png');
      case '알로에':
        return require('../assets/img/plants/Aloe.png');
      case '로즈마리':
        return require('../assets/img/plants/Rosemary.png');
      case '딸기':
        return require('../assets/img/plants/Strawberry.png');
      default:
        return require('../assets/img/plants/Default.png');
    }
  } catch (error) {
    return require('../assets/img/plants/Default.png');
  }
};

// 작물 이름과 성장 단계에 따른 일러스트 이미지 경로 매핑 함수
export const getImageForPlantGrowthStep = (plantName: string, growthStep: number): string => {
  try {
    return require(`../assets/img/illustration/${plantName}${growthStep}.png`);
  } catch (error) {
    return require('../assets/img/illustration/Default.png');
  }
};


// 날씨 제목에 따른 이미지 경로 매핑 함수
export const getImageForWeather = (title: string): string => {
  try {
    switch (title) {
      case '폭우주의보':
        return require('../assets/img/weathers/Rain.png');
      case '폭염특보':
        return require('../assets/img/weathers/Hot.png');
      case '폭설주의보':
        return require('../assets/img/weathers/Snow.png');
      default:
        return require('../assets/img/weathers/Cloudy.png');
    }
  } catch (error) {
    return require('../assets/img/weathers/Cloudy.png');
  }
};


// 할 일 타입에 따른 이미지 경로 매핑 함수
export const getImageForTodoType = (todoType: string): string => {
  try {
    switch (todoType) {
      case 'WATERING':
        return require('../assets/icons/Water.png');
      case 'FERTILIZERING':
        return require('../assets/icons/Fertilize.png');
      case 'HARVESTING':
        return require('../assets/icons/Harvest.png');
      default:
        return require('../assets/icons/Cloudy.png');
    }
  } catch (error) {
    return require('../assets/icons/Cloudy.png');
  }
};
