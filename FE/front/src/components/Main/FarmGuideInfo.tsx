import React from 'react';
import styles from '../../styles/Main/FarmGuideInfo.module.css'; // CSS 경로

interface FarmGuideInfoProps {
  data: {
    guide: string[];
  };
}

const FarmGuideInfo: React.FC<FarmGuideInfoProps> = ({ data }) => {
  return (
    <div className={styles.farmGuideContainer}>
      <h2>텃밭 가꾸기 가이드</h2>
      <ul className={styles.guideList}>
        {data.guide.map((item, index) => (
          <li key={index} className={styles.guideItem}>
            {item}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FarmGuideInfo;
