import React from 'react';
import styles from '../../styles/Main/MyPlantInfo.module.css';
import { getImageForCrop } from '../../utils/imageMapping';  // 이미지 매핑 함수 가져오기

interface MyPlantInfoProps {
  data: {
    isUsable: boolean;
    plantId: number;
    plantName: string;
  };
}

const MyPlantInfo: React.FC<MyPlantInfoProps> = ({ data }) => {
  return (
    <div className={styles.plantInfoContainer}>
      {/* 작물 정보 섹션 */}
      <div className={styles.sectionHeader}>
        <div>
          <h2>{data.plantName}를(을) <br /> 키우시네요!</h2>
          <p>{data.plantName}에 대해 알아보아요.</p>
        </div>
        <img src={getImageForCrop(data.plantName)} alt={data.plantName} className={styles.headerImage} />
      </div>

      {/* 해시태그 */}
      <div className={styles.hashTags}>
        <span className={styles.hashTag}>#{data.plantName}</span>
      </div>

      {/* 작물 재배방법과 비료/농약 추천 컴포넌트 */}
      <ul className={styles.plantList}>
        <li className={styles.plantItem}>
          <p>{data.plantName} 재배방법?</p>
        </li>
        <li className={styles.plantItem}>
          <p>{data.plantName} 비료/농약 추천</p>
        </li>
      </ul>
    </div>
  );
};

export default MyPlantInfo;
