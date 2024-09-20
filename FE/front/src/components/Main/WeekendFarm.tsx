import React from 'react';
import styles from '../../styles/Main/WeekendFarm.module.css'; // CSS 경로

const WeekendFarm: React.FC = () => {
  return (
    <div className={styles.weekendFarmContainer}>
      <h2>주말 농장에 오신 것을 환영합니다!</h2>
      <p>
        주말 농장은 바쁜 도시 생활 속에서도 자연과 함께할 수 있는 특별한 장소입니다.
        가까운 곳에서 직접 작물을 키워보고 수확해 보세요.
      </p>
      <div className={styles.weekendFarmImage}>
      </div>
      <button className={styles.exploreButton}>더 알아보기</button>
    </div>
  );
};

export default WeekendFarm;
