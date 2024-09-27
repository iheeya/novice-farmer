import React from 'react';
import styles from '../../styles/Detail/Pestdetection.module.css'; // 새로운 CSS 파일 경로
import pestDetectionIcon from '../../assets/icons/pestDetection.png'; // 병해충 검사 이미지

interface PestDetectionBoxProps {
  plantName: string;
}

const PestDetectionBox: React.FC<PestDetectionBoxProps> = ({ plantName }) => {
  return (
    <div className={styles.pestDetectionBox}>
      <img
        src={pestDetectionIcon}
        alt="병해충 검사"
        className={styles.pestDetectionImage}
      />
      <h3 className={styles.pestDetectionTitle}>병해충 검사</h3>
      <p className={styles.pestDetectionDescription}>
        {plantName}의 건강을 확인해보세요
      </p>
    </div>
  );
};

export default PestDetectionBox;
