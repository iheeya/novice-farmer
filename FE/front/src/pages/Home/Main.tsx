import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../../styles/Main/Main.module.css'; 
import { getMainPageInfo } from '../../services/Main/mainPageApi'; // API 호출 함수 임포트
import { MainPageInfoProps } from '../../services/Main/mainPageApi'; // 데이터 타입 임포트

// 각 컴포넌트 임포트
import TodoInfo from '../../components/Main/TodoInfo';
import BeginnerInfo from '../../components/Main/BeginnerInfo';
import MyFarmListInfo from '../../components/Main/MyFarmListInfo';
import FarmGuideInfo from '../../components/Main/FarmGuideInfo';
import FavoritesInfo from '../../components/Main/FavoritesInfo';
import MyPlantInfo from '../../components/Main/MyPlantInfo';
import RecommendInfo from '../../components/Main/RecommendInfo';
import CommunityInfo from '../../components/Main/CommunityInfo';
import WeekendFarm from '../../components/Main/WeekendFarm';

const Main: React.FC = () => {
  const navigate = useNavigate(); // 라우팅을 위해 useNavigate 사용

  const [componentData, setComponentData] = useState<MainPageInfoProps | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // API 호출
    getMainPageInfo()
      .then((data) => {
        setComponentData(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Failed to fetch main page data", error);
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <div>로딩 중...</div>;
  }

  if (!componentData) {
    return <div>데이터를 불러오는 데 실패했습니다.</div>;
  }

  // 주말농장 클릭 핸들러
  const handleWeekendFarmClick = () => {
    navigate("/weekendFarm-recommend"); // 클릭 시 /weekendFarm-recommend로 이동
  };

  return (
    <div className={styles.mainContainer}>
      {componentData.todoInfo.isUsable && (
        <div className={styles.componentWrapper}>
          <TodoInfo data={componentData.todoInfo} />
        </div>
      )}
      {componentData.myFarmListInfo.isUsable && (
        <div className={styles.componentWrapper}>
          <MyFarmListInfo data={componentData.myFarmListInfo} />
        </div>
      )}
      {componentData.beginnerInfo.isUsable && (
        <div className={styles.componentWrapper}>
          <BeginnerInfo data={componentData.beginnerInfo} />
        </div>
      )}
      {componentData.farmGuideInfo.isUsable && (
        <div className={styles.componentWrapper}>
          <FarmGuideInfo />
        </div>
      )}
      {componentData.favoritesInfo.isUsable && (
        <div className={styles.componentWrapper}>
          <FavoritesInfo data={componentData.favoritesInfo} />
        </div>
      )}
      {componentData.myPlantInfo.isUsable && (
        <div className={styles.componentWrapper}>
          <MyPlantInfo data={componentData.myPlantInfo} />
        </div>
      )}
      {componentData.recommendInfo.isUsable && (
        <div className={styles.componentWrapper}>
          <RecommendInfo data={componentData.recommendInfo} />
        </div>
      )}
      {componentData.communityInfo.isUsable && (
        <div className={styles.componentWrapper}>
          <CommunityInfo data={componentData.communityInfo} />
        </div>
      )}
      {componentData.weekendFarm.isUsable && (
        <div className={styles.componentWrapper} onClick={handleWeekendFarmClick}>
          <WeekendFarm data={componentData.weekendFarm} />
        </div>
      )}
    </div>
  );
};

export default Main;
