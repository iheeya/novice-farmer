import React from 'react';
import styles from '../../styles/Main/RecommendInfo.module.css'; // CSS 경로

interface RecommendInfoProps {
  data: {
    recommendCropBasedPlace: {
      plantName: string;
      plantDesc: string;
    }[];
    recommendCropBasedAge: {
      plantName: string;
      plantDesc: string;
    }[];
  };
}

const RecommendInfo: React.FC<RecommendInfoProps> = ({ data }) => {
  return (
    <div className={styles.recommendContainer}>
      <h2>추천 작물 정보</h2>
      <div className={styles.recommendSection}>
        <h3>장소에 따른 추천 작물</h3>
        <ul className={styles.recommendList}>
          {data.recommendCropBasedPlace.map((plant, index) => (
            <li key={index} className={styles.recommendItem}>
              <h4>{plant.plantName}</h4>
              <p>{plant.plantDesc}</p>
            </li>
          ))}
        </ul>
      </div>
      <div className={styles.recommendSection}>
        <h3>연령에 따른 추천 작물</h3>
        <ul className={styles.recommendList}>
          {data.recommendCropBasedAge.map((plant, index) => (
            <li key={index} className={styles.recommendItem}>
              <h4>{plant.plantName}</h4>
              <p>{plant.plantDesc}</p>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default RecommendInfo;
