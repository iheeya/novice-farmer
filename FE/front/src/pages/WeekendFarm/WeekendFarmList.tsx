import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styles from '../../styles/WeekendFarm/WeekendFarmList.module.css';

// require로 이미지 경로를 불러옴
const weekendFarmImage = require('../../assets/img/farms/weekendFarm.png');

const WeekendFarm: React.FC = () => {
  const [address, setAddress] = useState<string>('');

  useEffect(() => {
    // 여기에 axios로 데이터 요청
    axios.get('/api/weekendFarm')
      .then(response => {
        setAddress(response.data.address);  // 응답 데이터에서 주소 설정
      })
      .catch(error => {
        console.error('데이터 요청 실패:', error);
      });
  }, []);

  return (
    <div className={styles.weekendFarmContainer}>
      {/* 상단 제목 */}
      <h2 className={styles.title}># {address}의 주말농장을 알아보아요</h2>

      {/* 지도 영역 */}
      <div className={styles.mapWrapper}>
        {/* 지도는 나중에 구현 예정 */}
        <img src={weekendFarmImage} alt="주말 농장 지도" className={styles.mapImage} />
      </div>

      {/* 컴포넌트 목록 */}
      <div className={styles.componentList}>
        <p>여기에 나올 컴포넌트 목록</p>
        {/* 나중에 axios로 가져온 데이터를 기반으로 렌더링 */}
      </div>
    </div>
  );
};

export default WeekendFarm;
