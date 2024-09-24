import React from 'react';
import styles from '../../styles/Main/BeginnerInfo.module.css';
import { getImageForCrop } from '../../utils/imageMapping'; // 이미지 매핑 함수 가져오기

interface BeginnerInfoProps {
  data: {
    isUsable: boolean;
    plants: {
      plantId: number;
      plantName: string;
      plantDesc: string;
    }[];
  };
}

const BeginnerInfo: React.FC<BeginnerInfoProps> = ({ data }) => {
 

  return (
    <div className={styles.beginnerInfoContainer}>
      <h2 className={styles.beginnerInfoTitle}>어떤 작물을 키워야할지 <br /> 잘 모르시겠나요?</h2>
      <p className={styles.suggestionText}>추천하는 작물들이에요!</p>
      <ul className={styles.plantList}>
        {data.plants.slice(0, 2).map((plant) => (
          <li key={plant.plantId} className={styles.plantItem}>
            <p>{plant.plantDesc}</p>
            <img
              src={getImageForCrop(plant.plantName)}
              alt={plant.plantName}
              className={styles.plantImage}
            />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BeginnerInfo;
