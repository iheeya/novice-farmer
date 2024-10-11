import React, { useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../../styles/Detail/cameraPage.module.css';
import CameraLoading from '../../components/Detail/cameraLoading'; // 로딩 컴포넌트 임포트
import { sendPlantDiagnosis } from '../../services/PlantDetail/CameraDiagnosis'; // 분석 요청 함수 임포트

const CameraPage: React.FC = () => {
  const [capturedImage, setCapturedImage] = useState<string | null>(null); // 이미지 미리보기 URL 상태
  const [isLoading, setIsLoading] = useState(false); // 로딩 상태
  const [selectedFile, setSelectedFile] = useState<File | null>(null); // 실제 선택한 파일 상태
  const fileInputRef = useRef<HTMLInputElement | null>(null); // 파일 input 참조
  const navigate = useNavigate();

  // URL에서 myPlaceId와 myPlantId를 받아옴
  const { myPlaceId, myPlantId } = useParams<{ myPlaceId: string; myPlantId: string }>();

  // 이미지 선택 처리 함수
  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0]; // 첫 번째 파일만 사용
    if (file) {
      const imageUrl = URL.createObjectURL(file); // 이미지 미리보기 URL 생성
      setCapturedImage(imageUrl); // 이미지 URL 상태 설정
      setSelectedFile(file); // 선택된 파일 저장
    }
  };

  // 다시 찍기 버튼 처리
  const retakeImage = () => {
    setCapturedImage(null); // 미리보기 이미지 제거
    setSelectedFile(null); // 선택된 파일 초기화
    if (fileInputRef.current) {
      fileInputRef.current.value = ''; // 입력 필드 초기화
    }
  };

  // 이미지 전송 처리
  const sendImageToBackend = async () => {
    if (!selectedFile || !myPlaceId) return; // 파일이 없거나 장소 ID가 없을 때 처리

    try {
      // 로딩 상태 설정
      setIsLoading(true);

      // 선택된 파일을 sendPlantDiagnosis 함수에 넘김
      const response = await sendPlantDiagnosis(Number(myPlaceId), selectedFile);

      // 서버 응답에 따라 진단 결과 페이지로 이동
      navigate(`/myGarden/${myPlaceId}/${myPlantId}/diagnosis`, {
        state: {
          plantName: response.isPest.plantName, // 작물 이름
          capturedImage: response.isPest.myImagePath, // 내가 찍은 사진 경로
          pestDetected: response.isPest.hasPest, // 병해충 발견 여부
          pestName: response.pestInfo?.pestName || '', // 병해충 이름
          pestDescription: response.pestInfo?.pestDesc || '', // 병해충 설명
          treatmentMethod: response.pestInfo?.pestCureDesc || '', // 치료법
          pestImagePath: response.pestInfo?.pestImagePath || '', // 병해충 이미지 경로
        },
      });

      setIsLoading(false); // 로딩 상태 해제
    } catch (error) {
      console.error('이미지 분석 중 오류 발생:', error);
      setIsLoading(false); // 오류 발생 시 로딩 상태 해제
    }
  };

  return (
    <div className={styles.cameraPage}>
      {isLoading ? (
        <CameraLoading /> // 로딩 중일 때 로딩 컴포넌트 표시
      ) : capturedImage ? (
        <div className={styles.capturedImageContainer}>
          <img src={capturedImage} alt="Captured" className={styles.capturedImage} />
          <div className={styles.buttonsContainer}>
            {/* 분석하기 버튼 */}
            <button onClick={sendImageToBackend} className={styles.analyzeButton}>
              병충해 <br /> 분석하기
            </button>

            {/* 다시 찍기 버튼 */}
            <button onClick={retakeImage} className={styles.retakeButton}>
              <img src={require('../../assets/icons/Restart.png')} alt="다시 찍기" />
            </button>
          </div>
        </div>
      ) : (
        <div className={styles.cameraContainer}>
          <input
            type="file"
            accept="image/*"
            capture="environment" // 후면 카메라로 사진 촬영
            ref={fileInputRef}
            onChange={handleFileChange} // 파일 선택 시 호출
            style={{ display: 'none' }} // 파일 선택 input 숨김
          />
          <button
            onClick={() => fileInputRef.current?.click()} // 파일 선택 창 열기
            className={styles.captureButton}
          >
            촬영하기
          </button>
          <p className={styles.instructions}>
            작물을 화면 안에 <br /> 인식시켜 주세요.
          </p>
          <button
            onClick={() => navigate(-1)} // 취소 시 이전 페이지로 이동
            className={styles.cancelButton}
          >
            취소
          </button>
        </div>
      )}
    </div>
  );
};

export default CameraPage;
