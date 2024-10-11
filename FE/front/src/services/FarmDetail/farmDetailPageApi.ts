import api from '../../utils/axios';


export interface Address {
  sido: string;    // 시/도
  sigugun: string; // 시/군/구
  bname1: string;  // 법정동/리 이름1
  bname2: string;  // 법정동/리 이름2
  bunji: string;   // 번지
  jibun: string;   // 지번 주소
  zonecode: string; // 우편번호
}


export interface PlaceInfo {
    placeId: number;
    placeName: string;
    myPlaceName: string;
    farmCount: number;
    weather: string;
    address: Address;
}
  
export interface Farm {
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
export async function getFarmDetailPageInfo(myPlaceId: number): Promise<FarmDetailPageInfoProps> {
  try {
    const response = await api.get(`/myplace/${myPlaceId}`);
    return response.data;
  } catch (error) {
    console.error('Failed to fetch farm detail data:', error);
    throw error;
  }
}

// 장소 이름 수정 요청 함수
export async function updatePlaceName(userPlaceId: number, userPlaceName: string): Promise<void> {
  try {
    const response = await api.post('/myplace/name', {
      userPlaceId,
      userPlaceName,
    });
    return response.data;
  } catch (error) {
    console.error('Failed to update place name:', error);
    throw error;
  }
}
