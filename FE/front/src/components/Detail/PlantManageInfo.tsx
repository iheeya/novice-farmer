// components/Detail/PlantManagementInfo.tsx
import React from 'react';
import styles from '../../styles/Detail/plantManageInfo.module.css';

interface PlantManagementInfoProps {
  firstHarvestDate: string;
  recentWateringDate: string;
  recentFertilizingDate: string;
}

const PlantManagementInfo: React.FC<PlantManagementInfoProps> = ({
  firstHarvestDate,
  recentWateringDate,
  recentFertilizingDate,
}) => {
  const formatDate = (dateString: string) => {
    const [year, month, day] = dateString.split('-');
    return `${year.slice(2)}.${month}.${day}`;
  };

  return (
    <div className={styles.dateInfoBox}>
      <h3 className={styles.dateTitle}>작물관리 정보</h3>

      {firstHarvestDate && (
        <div className={styles.dateItem}>
          <span className={styles.label}>첫 수확일</span>
          <span className={styles.value}>{formatDate(firstHarvestDate)}</span>
        </div>
      
      )}

      {recentWateringDate && (
        <div className={styles.dateItem}>
          <div className={styles.label}>마지막 물 준 날</div>
          <div className={styles.value}>{formatDate(recentWateringDate)}</div>
        </div>
      )}

      {recentFertilizingDate && (
        <div className={styles.dateItem}>
          <div className={styles.label}>마지막 비료 준 날</div> 
          <div className={styles.value}>{formatDate(recentFertilizingDate)}</div>
        </div>
      )}
    </div>

  );
};

export default PlantManagementInfo;
