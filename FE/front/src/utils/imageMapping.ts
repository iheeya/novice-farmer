// 텃밭 위치에 따른 이미지 경로 매핑 함수
export const getImageForLocation = (location: string): string => {
  switch (location) {
    case '베란다':
      return '/assets/img/veranda.jpg';
    case '주말 농장':
      return '/assets/img/weekend_farm.jpg';
    default:
      return '/assets/img/default_farm.jpg'; // 기본 이미지
  }
};

// 작물 이름에 따른 이미지 경로 매핑 함수
export const getImageForCrop = (cropName: string): string => {
  switch (cropName) {
    case '토마토':
      return '/src/img/plants/Tomato.jpg';
    case '상추':
      return '/src/img/plants/Lettuce.jpg';
    case '바질':
      return '/src/img/plants/Basil.jpg';
    case '알로에':
      return '/src/img/plants/Aloe.jpg';
    case '로즈마리':
      return '/src/img/plants/Rosemary.jpg';
    case '딸기':
      return '/src/img/plants/Strawberry.jpg';
    default:
      return '/src/img/plants/Default.jpg'; // 기본 이미지
  }
};

// 날씨 제목에 따른 이미지 경로 매핑 함수
export const getImageForWeather = (title: string): string => {
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
};
