import React from 'react';
import styles from '../../styles/Main/MyPlantInfo.module.css'; // CSS 경로

interface MyPlantInfoProps {
  data: {
    crops: {
      cropName: string;
      progress: string;
    }[];
  };
}

const MyPlantInfo: React.FC<MyPlantInfoProps> = ({ data }) => {
  return (
    <div className={styles.plantInfoContainer}>
      <h2>나의 작물 정보</h2>
      <ul className={styles.plantList}>
        {data.crops.map((crop, index) => (
          <li key={index} className={styles.plantItem}>
            <h3>{crop.cropName}</h3>
            <p>현재 상태: {crop.progress}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default MyPlantInfo;
