import React from 'react';
import deleteIcon from '../../assets/icons/Delete.png';
import harvestIcon from '../../assets/icons/Harvest.png';
import endIcon from '../../assets/icons/End.png';
import waterIcon from '../../assets/icons/Water.png';
import fertilizeIcon from '../../assets/icons/Fertilize.png';
import {
  showDeleteModal,
  showHarvestModal,
  showEndGrowModal,
  showWaterModal,
  showFertilizeModal,
} from './IconModals'; // 모달 함수들 가져오기
import styles from '../../styles/Detail/icons.module.css';

interface IconProps {
  onDelete: () => void;           // 삭제 처리 함수
  onHarvest: () => void;          // 첫 수확 처리 함수
  onEndCultivation: () => void;   // 재배 종료 처리 함수
  onWater: () => void;            // 물주기 처리 함수
  onFertilize: () => void;        // 비료주기 처리 함수
  placeName: string;
  plantName: string;
  myPlaceId: number;
  isAlreadyFirstHarvest: boolean;
  recentWateringDate: string;
  recentFertilizingDate: string;
  firstHarvestDate: string;
  isStarted: boolean;
}

const Icons: React.FC<IconProps> = ({
  onDelete,
  onHarvest,
  onEndCultivation,
  onWater,
  onFertilize,
  placeName,
  plantName,
  myPlaceId,
  isAlreadyFirstHarvest,
  recentWateringDate,
  recentFertilizingDate,
  firstHarvestDate,
  isStarted, // 추가된 부분
}) => {
  return (
    <div className={styles.iconContainer}>
      {/* 삭제 아이콘 */}
      <div className={styles.iconWrapper} onClick={() => showDeleteModal(placeName, plantName, onDelete)}>
        <img src={deleteIcon} alt="삭제" className={styles.icon} />
        <p>삭제</p>
      </div>

      {/* 첫 수확 또는 재배 종료 아이콘 */}
      <div className={styles.iconWrapper} onClick={() => {
        if (isAlreadyFirstHarvest) {
          showEndGrowModal(placeName, plantName, onEndCultivation);
        } else {
          showHarvestModal(placeName, plantName, onHarvest);
        }
      }}>
        <img
          src={isAlreadyFirstHarvest ? endIcon : harvestIcon}
          alt={isAlreadyFirstHarvest ? '재배 종료' : '첫 수확'}
          className={styles.icon}
        />
        <p>{isAlreadyFirstHarvest ? '재배종료' : '첫수확하기'}</p>
      </div>

      {/* 물주기 아이콘 */}
      <div className={styles.iconWrapper} onClick={() => showWaterModal(placeName, plantName, onWater)}>
        <img src={waterIcon} alt="물주기" className={styles.icon} />
        <p>물주기</p>
      </div>

      {/* 비료주기 아이콘 */}
      <div className={styles.iconWrapper} onClick={() => showFertilizeModal(placeName, plantName, onFertilize)}>
        <img src={fertilizeIcon} alt="비료주기" className={styles.icon} />
        <p>비료주기</p>
      </div>
    </div>
  );
};

export default Icons;
