import api from '../../utils/axios';

export const sendPlantDiagnosis = async (farmId: number, image: Blob) => {
  try {
    // 파일 이름을 동적으로 생성 (예: 현재 타임스탬프 사용)
    const timestamp = new Date().getTime();
    const fileName = `plant_image_${timestamp}.jpeg`;  // JPEG 파일로 변경

    // Blob을 File로 변환 (JPEG 형식으로)
    const file = new File([image], fileName, { type: 'image/jpeg' });

    // FormData 객체 생성
    const formData = new FormData();
    formData.append('farmId', String(farmId));
    formData.append('file', file);  // JPEG 파일로 전송

    // API 요청 보내기
    const response = await api.post('/myplant/pest', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    // 성공적으로 응답을 받은 경우
    return response.data;
  } catch (error) {
    console.error('병해충 진단 요청 중 오류 발생:', error);
    throw error;
  }
};
