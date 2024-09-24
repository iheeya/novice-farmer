import React from 'react';
import styles from '../../styles/Main/WeekendFarm.module.css';

// require로 이미지 경로를 불러옴
const weekendFarmImage = require('../../assets/img/farms/weekendFarm.png');

interface WeekendFarmProps {
  data: {
    address: string;
  };
}

const WeekendFarm: React.FC<WeekendFarmProps> = ({ data }) => {
  return (
    <div className={styles.weekendFarmContainer}>
      <div className={styles.imageWrapper}>
        <img src={weekendFarmImage} alt="주말 농장" className={styles.weekendFarmImage} />
      </div>
      <div className={styles.textWrapper}>
        <h2 className={styles.title}>{data.address}</h2>
        <p className={styles.description}>
           근처 주말농장에서 <br />  작물을 길러보세요.
        </p>
      </div>
    </div>
  );
};

export default WeekendFarm;
