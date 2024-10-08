import api from '../../utils/axios';

// PlantData 타입 정의
export interface PlantData {
  isStarted: boolean;
  isAlreadyFirstHarvest: boolean;
  plantInfo: {
    placeName: string;
    myPlaceId: number;
    myPlaceName: string;
    plantName: string;
    myPlantName: string;
    plantImagePath: string;
    startDate: string;
    plantGrowthStep: number;
    plantDegreeRatio: number;
    threshold: {
      totalStep: number;
      step1: number;
      step2: number;
      step3: number | null;
    };
    firstHarvestDate: string | null;
    recentWateringDate: string | null;
    recentFertilizingDate: string | null;
    memo: string;  // 메모 필드 추가
  };
  todos: {
    todoDate: string;
    todoType: string;
    remainDay: number;
  }[];
}

// myPlantId에 해당하는 작물 정보를 가져오는 API 함수
export const getPlantDetailInfo = async (myPlantId: number): Promise<PlantData> => {
  try {
    const response = await api.get(`/myplant/${myPlantId}`);
    return response.data;  // API에서 받아온 데이터 반환
  } catch (error) {
    console.error('작물 상세 정보를 가져오는 데 실패했습니다.', error);
    throw error;  // 에러 발생 시 상위 컴포넌트로 에러 전달
  }
};


// 작물 이름 수정 요청 함수
export async function updatePlantName(farmId: number, plantName: string): Promise<void> {
  try {
    const response = await api.post('/myplant/name', {
      farmId,
      plantName,
    });
    return response.data;
  } catch (error) {
    console.error('Failed to update plant name:', error);
    throw error;
  }
}

// 작물 시작 함수
export async function startPlant(farmId: number): Promise<void> {
  try {
    const response = await api.post('/myplant', {
      farmId
    });
    return response.data;
  } catch (error) {
    console.error('Failed to start plant', error);
  throw error;
  }
}


// 메모 수정 요청 함수
export async function updateMemo(farmId: number, memo: string): Promise<void> {
  try {
    const response = await api.post('/myplant/memo', {
      farmId,
      memo,
    });
    return response.data;
  } catch (error) {
    console.error('메모 수정 실패:', error);
    throw error;
  }
}


