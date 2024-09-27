import React, { useState } from 'react';
import deleteIcon from '../../assets/icons/Delete.png';
import harvestIcon from '../../assets/icons/Harvest.png';
import endIcon from '../../assets/icons/End.png'; // 재배 종료 아이콘
import waterIcon from '../../assets/icons/Water.png';
import fertilizeIcon from '../../assets/icons/Fertilize.png';
import FarmDeleteModal from './FarmDeleteModal';
import HarvestModal from './HarvestModal';
import WaterModal from './WaterModal';
import FertilizeModal from './FertilizeModal';
import EndGrowModal from './EndGrowModal';
import styles from '../../styles/Detail/icons.module.css';

interface IconProps {
  onDelete: () => void;
  onHarvest: () => void;
  onWater: () => void;
  onFertilize: () => void;
  placeName: string;
  plantName: string;
  myPlaceId: number;
  isAlreadyFirstHarvest: boolean; // 첫 수확 여부 전달
  recentWateringDate: string; 
  recentFertilizingDate: string; 
  firstHarvestDate: string;
}

const Icons: React.FC<IconProps> = ({
  onDelete,
  onHarvest,
  onWater,
  onFertilize,
  placeName,
  plantName,
  myPlaceId,
  isAlreadyFirstHarvest,
  recentWateringDate,  
  recentFertilizingDate,
  firstHarvestDate
}) => {
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [isHarvestModalOpen, setIsHarvestModalOpen] = useState(false);
  const [isEndGrowModalOpen, setIsEndGrowModalOpen] = useState(false);
  const [isWaterModalOpen, setIsWaterModalOpen] = useState(false);
  const [isFertilizeModalOpen, setIsFertilizeModalOpen] = useState(false);

  return (
    <div className={styles.iconContainer}>
      {/* 삭제 아이콘 */}
      <div className={styles.iconWrapper} onClick={() => setIsDeleteModalOpen(true)}>
        <img src={deleteIcon} alt="삭제" className={styles.icon} />
        <p>삭제</p>
      </div>

       {/* 첫 수확 아이콘 또는 재배 종료 아이콘 */}
       <div className={styles.iconWrapper} onClick={() => {
        isAlreadyFirstHarvest
          ? setIsEndGrowModalOpen(true)  // 재배 종료 모달 열기
          : setIsHarvestModalOpen(true); // 첫 수확 모달 열기
      }}>
        <img
          src={isAlreadyFirstHarvest ? endIcon : harvestIcon}
          alt={isAlreadyFirstHarvest ? '재배 종료' : '첫 수확'}
          className={styles.icon}
        />
        <p>{isAlreadyFirstHarvest ? '재배종료' : '첫수확하기'}</p>
      </div>

      {/* 물주기 아이콘 */}
      <div className={styles.iconWrapper} onClick={() => setIsWaterModalOpen(true)}>
        <img src={waterIcon} alt="물주기" className={styles.icon} />
        <p>물주기</p>
      </div>

      {/* 비료주기 아이콘 */}
      <div className={styles.iconWrapper} onClick={() => setIsFertilizeModalOpen(true)}>
        <img src={fertilizeIcon} alt="비료주기" className={styles.icon} />
        <p>비료주기</p>
      </div>

      {/* 삭제 모달 */}
      {isDeleteModalOpen && (
        <FarmDeleteModal
          isOpen={isDeleteModalOpen}
          onClose={() => setIsDeleteModalOpen(false)}
          onDelete={onDelete}
          placeName={placeName}
          plantName={plantName}
        />
      )}

      {/* 첫수확 모달 */}
      {isHarvestModalOpen && !isAlreadyFirstHarvest && (
        <HarvestModal
          isOpen={isHarvestModalOpen}
          onClose={() => setIsHarvestModalOpen(false)}
          onConfirm={onHarvest}
          placeName={placeName}
          plantName={plantName}
        />
      )}

      {/* 재배 종료 모달 */}
      {isEndGrowModalOpen && (
        <EndGrowModal
          isOpen={isEndGrowModalOpen}
          onClose={() => setIsEndGrowModalOpen(false)}
          onConfirm={() => {
            setIsEndGrowModalOpen(false);
            // 재배 종료 처리를 여기에 추가
          }}
          placeName={placeName}
          plantName={plantName}
          myPlaceId={myPlaceId}
          firstHarvestDate={firstHarvestDate}
        />
      )}

      {/* 물주기 모달 */}
      {isWaterModalOpen && (
        <WaterModal
          isOpen={isWaterModalOpen}
          onClose={() => setIsWaterModalOpen(false)}
          onConfirm={onWater}
          placeName={placeName}
          plantName={plantName}
          recentWateringDate={recentWateringDate}
        />
      )}

      {/* 비료주기 모달 */}
      {isFertilizeModalOpen && (
        <FertilizeModal
          isOpen={isFertilizeModalOpen}
          onClose={() => setIsFertilizeModalOpen(false)}
          onConfirm={onFertilize}
          placeName={placeName}
          plantName={plantName}
          recentFertilizingDate={recentFertilizingDate} // 비료 준 날짜 전달
        />
      )}
    </div>
  );
};

export default Icons;
