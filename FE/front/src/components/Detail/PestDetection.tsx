import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../../styles/Detail/Pestdetection.module.css';
import pestDetectionIcon from '../../assets/icons/PestDetection.png';

interface PestDetectionBoxProps {
  plantName: string;
  myPlaceId: number; // 추가
  myPlantId: string; // 추가
}

const PestDetectionBox: React.FC<PestDetectionBoxProps> = ({ plantName, myPlaceId, myPlantId }) => {
  const navigate = useNavigate();

  const handleImageClick = () => {
    // 이미지 클릭 시, 카메라 페이지로 myPlaceId와 myPlantId를 포함하여 이동
    navigate(`/myGarden/${myPlaceId}/${myPlantId}/camera`);
  };

  return (
    <div className={styles.pestDetectionBox}>
      <img
        src={pestDetectionIcon}
        alt="병해충 검사"
        className={styles.pestDetectionImage}
        onClick={handleImageClick}
        style={{ cursor: 'pointer' }}
      />
      <h3 className={styles.pestDetectionTitle}>병해충 검사</h3>
      <p className={styles.pestDetectionDescription}>
        {plantName}의 건강을 확인해보세요
      </p>
    </div>
  );
};

export default PestDetectionBox;
