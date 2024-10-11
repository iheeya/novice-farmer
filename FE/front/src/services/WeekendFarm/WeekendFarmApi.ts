import api from '../../utils/axios';

// 주말농장 추천 API 요청 함수
export const getWeekendFarmInfo = async () => {
  try {
    const response = await api.get('/weekend/recommend');
    return response.data;
  } catch (error) {
    console.error('주말농장 데이터를 불러오는 중 오류 발생:', error);
    throw error;
  }
};
