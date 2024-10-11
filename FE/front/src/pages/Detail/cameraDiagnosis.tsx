import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import styles from '../../styles/Detail/cameraDiagnosis.module.css';
import { GetImage } from '../../services/getImage'; // S3에서 이미지 가져오는 함수 임포트

const CameraDiagnosis: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { myPlaceId, myPlantId } = useParams<{ myPlaceId: string; myPlantId: string }>();

  // 상태로 이미지 경로 관리
  const [myImageUrl, setMyImageUrl] = useState<string | null>(null);
  const [pestImageUrl, setPestImageUrl] = useState<string | null>(null);

  // `location.state`에서 필요한 데이터를 받아옴
  const {
    plantName,
    capturedImage,
    pestDetected,
    pestName,
    pestDescription,
    treatmentMethod,
    pestImagePath  // pestImagePath도 가져오기
  } = location.state || {};

  // S3에서 이미지 가져오기
  useEffect(() => {
    const fetchImages = async () => {
      try {
        if (capturedImage) {
          const myImage = await GetImage(capturedImage); // S3에서 내 작물 이미지 가져오기
          setMyImageUrl(myImage);
        }

        // pestImagePath 값이 올바른지 확인하는 로그
        console.log("Pest Image Path:", pestImagePath);

        if (pestImagePath) {
          const pestImage = await GetImage(pestImagePath); // S3에서 병해충 이미지 가져오기
          setPestImageUrl(pestImage);
        }
      } catch (error) {
        console.error('이미지 로딩 중 오류 발생:', error);
      }
    };

    fetchImages(); // 이미지 가져오기 실행
  }, [capturedImage, pestImagePath]);

  return (
    <div className={styles.diagnosisPage}>
      <div className={styles.header}>
        <img
          src={require('../../assets/icons/Back.png')}
          alt="뒤로가기"
          className={styles.backButton}
          onClick={() => navigate(-1)}
        />
        <h2 className={styles.title}>병해충 검사 결과</h2>
      </div>

      {/* 내 작물 사진과 버튼을 감싸는 박스 */}
      <div className={styles.imageContainer}>
        <div className={styles.sectionButton}>내 {plantName} 사진</div>
        {/* 내 작물 이미지가 로드되면 표시 */}
        {myImageUrl ? (
          <img src={myImageUrl} alt="Captured" className={styles.diagnosisImage} />
        ) : (
          <p>내 작물 사진을 로딩 중입니다...</p>
        )}
      </div>

      {/* 병해충 검사 결과 */}
      {pestDetected ? (
        <>
          <div className={styles.imageContainer}>
            <div className={styles.sectionButton}>병해충 {plantName} 사진</div>
            {/* 병해충 이미지가 로드되면 표시 */}
            {pestImageUrl ? (
              <img src={pestImageUrl} alt="병해충 예시" className={styles.diagnosisImage} />
            ) : (
              <p>병해충 사진을 로딩 중입니다...</p>
            )}
          </div>
          <div className={styles.pestInfo}>
            <h3 className={styles.pestTitle}>{pestName}</h3>
            <p className={styles.pestDescription}>{pestDescription}</p>
            <h3 className={styles.treatmentTitle}>치료법</h3>
            <p className={styles.treatmentDescription}>{treatmentMethod}</p>
          </div>
        </>
      ) : (
        <>
          <p className={styles.healthyMessage}>현재 {plantName}는 매우 건강해요!</p>
          <img
            src={require('../../assets/img/plants/Default.png')}
            alt="건강한 작물"
            className={styles.illustImage}
          />
        </>
      )}

      {/* 닫기 버튼 */}
      <button className={styles.closeButton} onClick={() => navigate(`/myGarden/${myPlaceId}/${myPlantId}`)}>
        닫기
      </button>
    </div>
  );
};

export default CameraDiagnosis;
