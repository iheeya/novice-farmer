import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../../styles/Todo/AddGarden.module.css';
import PlusIcon from '../../assets/icons/Plus.png'; 
import DefaultPlant from '../../assets/img/loading/loading.png'; // 기본 이미지 경로 확인

const AddGarden: React.FC = () => {
  const navigate = useNavigate();

  const handleAddGardenClick = () => {
    navigate('/register/garden');
  };

  return (
    <div className={styles.addGardenContainer}>
      {/* 텃밭 추가 섹션 */}
      <div className={styles.gardenBox}>
        <h2 className={styles.title}>텃밭을 추가하세요</h2>
        <p className={styles.subtitle}>나의 작물을 추가해보세요</p>
        <img
          src={PlusIcon}
          alt="Add garden"
          className={styles.plusIcon}
          onClick={handleAddGardenClick}
        />
      </div>

      {/* 작물 없음 섹션 */}
      <div className={styles.noPlantsContainer}>
        <img
          src={DefaultPlant}
          alt="Default plant"
          className={styles.defaultPlantImage}
        />
        <p className={styles.noPlantsText}>키우고 있는 작물이 없습니다.</p>
      </div>
    </div>
  );
};

export default AddGarden;
