import api from '../../utils/axios';

export const sendPlantDiagnosis = async (farmId: number, image: File) => {
  try {
    // File 객체를 JPEG로 변환하는 함수
    const convertToJpeg = async (file: File): Promise<File> => {
      const imageBitmap = await createImageBitmap(file);
      const canvas = document.createElement('canvas');
      canvas.width = imageBitmap.width;
      canvas.height = imageBitmap.height;
      
      const context = canvas.getContext('2d');
      context?.drawImage(imageBitmap, 0, 0);
      
      // canvas를 JPEG로 변환
      const jpegDataUrl = canvas.toDataURL('image/jpeg');
      
      // Data URL을 Blob으로 변환
      const blob = await (await fetch(jpegDataUrl)).blob();
      
      // Blob을 JPEG File로 변환
      const jpegFile = new File([blob], `plant_image_${Date.now()}.jpeg`, {
        type: 'image/jpeg',
      });
      
      return jpegFile;
    };

    // 이미지 파일을 JPEG로 변환
    const jpegFile = await convertToJpeg(image);

    // FormData 객체 생성
    const formData = new FormData();
    formData.append('farmId', String(farmId));
    formData.append('file', jpegFile); // JPEG 파일로 전송

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
