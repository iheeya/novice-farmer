import React from 'react';
import styles from '../../styles/Main/RecommendInfo.module.css';
import { getImageForCrop } from '../../utils/imageMapping'; // 이미지 매핑 함수 가져오기

interface RecommendInfoProps {
  data: {
    recommendByPlace: {
      comment: string;
      recommendPlants: {
        plantId: number;
        plantName: string;
        plantDescription : string;
      }[];
    };
    recommendByUser: {
      comment: string;
      recommendPlants: {
        plantId: number;
        plantName: string;
        plantDescription : string;
      }[];
    };
  };
}

const RecommendInfo: React.FC<RecommendInfoProps> = ({ data }) => {
  return (
    <div className={styles.recommendContainer}>
      <div className={styles.sectionHeader}>
        <h2>이런 작물 어떠세요?</h2>
        <img src={getImageForCrop('Default')} alt="기본 작물 이미지" className={styles.headerImage} />
      </div>

      {/* 장소에 따른 추천 작물 */}
      <div className={styles.recommendSection}>
        <p className={styles.comment}>{data.recommendByPlace.comment}</p>
        <ul className={styles.recommendList}>
          {data.recommendByPlace.recommendPlants.map((plant) => (
            <li key={plant.plantId} className={styles.recommendItem}>
              <div className={styles.itemContent}>
                <h4>{plant.plantName} - {plant.plantDescription}</h4>
                <img src={getImageForCrop(plant.plantName)} alt={plant.plantName} className={styles.plantImage} />
              </div>
            </li>
          ))}
        </ul>
      </div>

      {/* 사용자에 따른 추천 작물 */}
      <div className={styles.recommendSection}>
        <p className={styles.comment}>{data.recommendByUser.comment}</p>
        <ul className={styles.recommendList}>
          {data.recommendByUser.recommendPlants.map((plant) => (
            <li key={plant.plantId} className={styles.recommendItem}>
              <div className={styles.itemContent}>
                <h4>{plant.plantName} - {plant.plantDescription}</h4>
                <img src={getImageForCrop(plant.plantName)} alt={plant.plantName} className={styles.plantImage} />
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default RecommendInfo;
