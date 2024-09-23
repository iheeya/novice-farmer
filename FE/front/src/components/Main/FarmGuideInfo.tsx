import React from 'react';
import styles from '../../styles/Main/FarmGuideInfo.module.css';

const FarmGuideInfo: React.FC = () => {
  return (
    <div className={styles.farmGuideContainer}>
      <h2>아직 텃밭이 없으시네요!</h2>
      <p className={styles.subtitle}>텃밭 재배에 대해서 알려드릴게요</p>
      <ul className={styles.guideList}>
        <li className={styles.guideItem}>
          <p>텃밭 가꾸기 기초 방법</p>
        </li>
        <li className={styles.guideItem}>
          <p>파종 기초 방법</p>
        </li>
      </ul>
    </div>
  );
};

export default FarmGuideInfo;
