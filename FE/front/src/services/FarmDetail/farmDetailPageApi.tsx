import api from '../../utils/axios';

export interface PlaceInfo {
    placeId: number;
    placeName: string;
    myPlaceName: string;
    farmCount: number;
    weather: string;
  }
  
  export interface Farm {  // Farm 타입을 export로 내보내기
    plantId: number;
    plantName: string;
    myPlantId: number;
    myPlantName: string;
    myPlantGrowthStep: number;
    imagePath: string;
    todoInfo: string;
    seedDate: string;
  }

// 전체 API 응답 타입 정의
export interface FarmDetailPageInfoProps {
  placeInfo: PlaceInfo;
  farms: Farm[];
}

// MyPlaceId에 따라 해당 농장 정보를 가져오는 함수
export function getFarmDetailPageInfo(myPlaceId: number): Promise<FarmDetailPageInfoProps> {
  return api
    .get(`/myplace/${myPlaceId}`)
    .then((response) => {
      return Promise.resolve(response.data);
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}

// 장소 이름 수정 요청 함수
export function updatePlaceName(userPlaceId: number, userPlaceName: string): Promise<void> {
  return api
    .post('/myplace/name', {
      userPlaceId,
      userPlaceName,
    })
    .then((response) => {
      return Promise.resolve(response.data);
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}
