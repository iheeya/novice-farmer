import api from '../../utils/axios';

// 작물 삭제 API 함수
export const deletePlant = async (farmId: number): Promise<void> => {
  try {
    const response = await api.post('/myplant/manage/delete', { farmId });
    return response.data;
  } catch (error) {
    console.error('작물 삭제에 실패했습니다.', error);
    throw error;
  }
};

// 첫 수확 API 함수
export const harvestPlant = async (farmId: number): Promise<void> => {
  try {
    const response = await api.post('/myplant/manage/harvest', { farmId });
    return response.data;
  } catch (error) {
    console.error('첫 수확 처리에 실패했습니다.', error);
    throw error;
  }
};

// 재배 종료 API 함수
export const endCultivation = async (farmId: number): Promise<void> => {
  try {
    const response = await api.post('/myplant/manage/end', { farmId });
    return response.data;
  } catch (error) {
    console.error('재배 종료에 실패했습니다.', error);
    throw error;
  }
};

// 물주기 API 함수
export const waterPlant = async (farmId: number): Promise<void> => {
  try {
    const response = await api.post('/myplant/manage/water', { farmId });
    return response.data;
  } catch (error) {
    console.error('물주기 처리에 실패했습니다.', error);
    throw error;
  }
};

// 비료주기 API 함수
export const fertilizePlant = async (farmId: number): Promise<void> => {
  try {
    const response = await api.post('/myplant/manage/fertilizer', { farmId });
    return response.data;
  } catch (error) {
    console.error('비료주기 처리에 실패했습니다.', error);
    throw error;
  }
};
