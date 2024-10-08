import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../../styles/Main/FarmGuideInfo.module.css';


const FarmGuideInfo: React.FC = () => {
  const navigate = useNavigate();

  const handlePlantClick = () => {
    navigate(`/info/plant`)
  }

  const handleFarmClick = () => {
    navigate(`/info/place`);
  }

  return (
    <div className={styles.farmGuideContainer}>
      <h2 className={styles.farmGuideTitle}>아직 텃밭이 없으시네요!</h2>
      <p className={styles.subtitle}>텃밭 재배에 대해서 알려드릴게요</p>
      <ul className={styles.guideList}>
        <li className={styles.guideItem}
          onClick={() => handleFarmClick()}>
          <p>텃밭 가꾸기 기초 방법</p>
        </li>
        <li className={styles.guideItem}
          onClick={() => handlePlantClick()}>
          <p>파종 기초 방법</p>
        </li>
      </ul>
    </div>
  );
};

export default FarmGuideInfo;
