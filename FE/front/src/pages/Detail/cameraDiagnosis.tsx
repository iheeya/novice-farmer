import React from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import styles from '../../styles/Detail/cameraDiagnosis.module.css';

const CameraDiagnosis: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { myPlaceId, myPlantId } = useParams<{ myPlaceId: string; myPlantId: string }>();

  const { plantName, capturedImage, pestDetected, pestName, pestDescription, treatmentMethod } = location.state || {};

  const handleBackClick = () => {
    navigate(-1);
  };

  const handleClose = () => {
    navigate(`/myGarden/${myPlaceId}/${myPlantId}`); // 작물 상세 페이지로 이동
  };

  return (
    <div className={styles.diagnosisPage}>
      <div className={styles.header}>
        <img
          src={require('../../assets/icons/Back.png')}
          alt="뒤로가기"
          className={styles.backButton}
          onClick={handleBackClick}
        />
        <h2 className={styles.title}>병해충 검사 결과</h2>
      </div>

      {/* 내 작물 사진과 버튼을 감싸는 박스 */}
      <div className={styles.imageContainer}>
        <div className={styles.sectionButton}>내 {plantName} 사진</div>
        <img src={capturedImage} alt="Captured" className={styles.diagnosisImage} />
      </div>

      {/* 병해충 검사 결과 */}
      {pestDetected ? (
        <>
          <div className={styles.imageContainer}>
            <div className={styles.sectionButton}>병해충 {plantName} 사진</div>
            <img
              src={require('../../assets/img/plants/pestExample.png')}
              alt="병해충 예시"
              className={styles.diagnosisImage}
            />
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
      <button className={styles.closeButton} onClick={handleClose}>닫기</button>
    </div>
  );
};

export default CameraDiagnosis;
