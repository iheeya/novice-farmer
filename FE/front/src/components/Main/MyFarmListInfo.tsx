import React from 'react';
import styles from '../../styles/Main/MyFarmListInfo.module.css'; // CSS 경로

interface MyFarmListInfoProps {
  data: {
    farms: {
      myPlaceName: string;
      placeName: string;
    }[];
  };
}

const MyFarmListInfo: React.FC<MyFarmListInfoProps> = ({ data }) => {
  return (
    <div className={styles.farmListContainer}>
      <h2>나의 농장 리스트</h2>
      {data.farms.length > 0 ? (
        <ul className={styles.farmList}>
          {data.farms.map((farm, index) => (
            <li key={index} className={styles.farmItem}>
              <h3>{farm.myPlaceName}</h3>
              <p>{farm.placeName}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p className={styles.noFarmText}>추가된 농장이 없습니다. 새로운 농장을 추가해보세요!</p>
      )}
    </div>
  );
};

export default MyFarmListInfo;
