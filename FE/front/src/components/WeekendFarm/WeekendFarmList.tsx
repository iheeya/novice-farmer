import React from 'react';
import styles from '../../styles/WeekendFarm/WeekendFarmList.module.css';

interface WeekendFarmListProps {
  name: string;
  address: string;
  desc: string;
  imagePath?: string;
  isActive: boolean; // 수정: isActive로 변경
}

const WeekendFarmList: React.FC<WeekendFarmListProps> = ({ name, address, desc, imagePath, isActive }) => {
  return (
    <div className={`${styles.farmItemContainer} ${isActive ? styles.active : ''}`}>
      <div className={styles.textContainer}>
        <div className={styles.farmName}>{name}</div>
        <div className={styles.farmAddress}>{address}</div>
        <div className={styles.bottomContainer}>
          <div className={styles.farmDesc}>{desc}</div>
          {imagePath && <img src={imagePath} alt={name} className={styles.farmImage} />}
        </div>
      </div>
    </div>
  );
};

export default WeekendFarmList;
