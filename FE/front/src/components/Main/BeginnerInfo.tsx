import React from 'react';
import styles from '../../styles/Main/BeginnerInfo.module.css'; // CSS 경로

interface BeginnerInfoProps {
  data: {
    plants: {
      plantName: string;
      plantDesc: string;
    }[];
  };
}

const BeginnerInfo: React.FC<BeginnerInfoProps> = ({ data }) => {
  return (
    <div className={styles.beginnerInfoContainer}>
      <h2>초보자를 위한 작물 추천</h2>
      <ul className={styles.plantList}>
        {data.plants.map((plant, index) => (
          <li key={index} className={styles.plantItem}>
            <h3>{plant.plantName}</h3>
            <p>{plant.plantDesc}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BeginnerInfo;
