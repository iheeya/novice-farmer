import React from 'react';
import { useNavigate } from 'react-router-dom';
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
  const navigate = useNavigate();

  // 클릭 시 해당 plantName의 정보 페이지로 이동
  const handlePlantClick = (plantName: string) => {
    navigate(`/info/plant/${plantName}`);
  };

  return (
    <div className={styles.beginnerInfoContainer}>
      <h2 className={styles.beginnerInfoTitle}>
        어떤 작물을 키워야할지 <br /> 잘 모르시겠나요?
      </h2>
      <p className={styles.suggestionText}>추천하는 작물들이에요!</p>
      <ul className={styles.plantList}>
        {data.plants.slice(0, 2).map((plant) => (
          <li
            key={plant.plantId}
            className={styles.plantItem}
            onClick={() => handlePlantClick(plant.plantName)} // 클릭 이벤트 추가
          >
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
