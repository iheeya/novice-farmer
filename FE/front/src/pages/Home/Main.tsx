import React, { useEffect, useState } from 'react';
import axios from 'axios';
import styles from '../../styles/Main/Main.module.css'; // CSS 모듈 임포트

// 각 컴포넌트 임포트 (수정된 이름으로)
import TodoInfo from '../../components/Main/TodoInfo';
import BeginnerInfo from '../../components/Main/BeginnerInfo';
import MyFarmListInfo from '../../components/Main/MyFarmListInfo';
import FarmGuideInfo from '../../components/Main/FarmGuideInfo';
import FavoritesInfo from '../../components/Main/FavoritesInfo';
import MyPlantInfo from '../../components/Main/MyPlantInfo';
import RecommendInfo from '../../components/Main/RecommendInfo';
import CommunityInfo from '../../components/Main/CommunityInfo';
import WeekendFarm from '../../components/Main/WeekendFarm';

const Main = () => {
  const [componentData, setComponentData] = useState({
    todo: {
      title: "폭우주의보",
      type: "기상특보",
      cropName: "토순이",
      farmLocation: "구미시 진평동",
      plant_imgURL: "https://i.ibb.co/7KJVPPh/Lettuce3.png",
      alertDate: "2024-09-22",
      temperature: "25°C",
    },
    beginnerInfo: {
      plants: [
        { plantName: "토마토", plantDesc: "초보 농부가 키우기 쉬운 토마토!" },
        { plantName: "상추", plantDesc: "수확 시기가 짧아서 키우기 편한 상추!" }
      ]
    },
    farmList: {
      farms: [
        { myPlaceName: "우리집 베란다", placeName: "베란다" },
        { myPlaceName: "구미농장", placeName: "주말 농장" }
      ]
    },
    farmInfo: {
      guide: [
        "텃밭 가꾸기 기초방법",
        "파종 기초 방법"
      ]
    },
    favorites: {
      crops: ["토마토", "상추"],
      farms: ["베란다", "주말 농장"]
    },
    myCrop: {
      crops: [{ cropName: "토마토", progress: "성장 중" }]
    },
    recommendInfo: {
      recommendCropBasedPlace: [
        { plantName: "바질", plantDesc: "실내에서 키우기 좋은 식물" },
        { plantName: "로즈마리", plantDesc: "실내에서 키우기 좋은 식물" }
      ],
      recommendCropBasedAge: [
        { plantName: "딸기", plantDesc: "20대 남성이 많이 키우는 식물" },
        { plantName: "알로에", plantDesc: "20대 남성이 많이 키우는 식물" }
      ]
    },
    communityInfo: {
      popularPosts: [
        {
          image: "/assets/community1.jpg",
          title: "토마토 베란다 재배 성공기",
          content: "토마토는 이렇게 베란다에서...",
          author: "홍길동",
          authorImage: "/assets/profile1.jpg",
          date: "2024-09-20",
        },
        {
          image: "/assets/community2.jpg",
          title: "토마토 여름철 재배법",
          content: "여름에 토마토를 키우는 꿀팁...",
          author: "김철수",
          authorImage: "/assets/profile2.jpg",
          date: "2024-09-18",
        },
        {
          image: "/assets/community3.jpg",
          title: "유기농 토마토 재배 이야기",
          content: "유기농으로 토마토를 재배하려면...",
          author: "박민수",
          authorImage: "/assets/profile3.jpg",
          date: "2024-09-15",
        }
      ],
      latestPosts: [
        {
          image: "/assets/community4.jpg",
          title: "토마토 실내 재배 방법",
          content: "실내에서도 토마토를 키울 수 있어요...",
          author: "이영희",
          authorImage: "/assets/profile4.jpg",
          date: "2024-09-22",
        },
        {
          image: "/assets/community5.jpg",
          title: "토마토 수확 시기 알아보기",
          content: "토마토를 언제 수확해야 할까요...",
          author: "김수진",
          authorImage: "/assets/profile5.jpg",
          date: "2024-09-21",
        },
        {
          image: "/assets/community6.jpg",
          title: "베란다에서 토마토 키우기",
          content: "베란다에서 토마토를 키울 때 주의할 점은...",
          author: "최민정",
          authorImage: "/assets/profile6.jpg",
          date: "2024-09-19",
        }
      ]
    }
  });

  const [componentVisibility, setComponentVisibility] = useState({
    todo: true,
    beginnerInfo: true,
    farmList: true,
    farmInfo: true,
    favorites: true,
    myCrop: true,
    recommendInfo: true,
    communityInfo: true,
    weekendFarmRecommendation: true,
  });

  return (
    <div className={styles.mainContainer}>
      {componentVisibility.todo && <div className={styles.componentWrapper}><TodoInfo data={componentData.todo} /></div>}
      {componentVisibility.beginnerInfo && <div className={styles.componentWrapper}><BeginnerInfo data={componentData.beginnerInfo} /></div>}
      {componentVisibility.farmList && <div className={styles.componentWrapper}><MyFarmListInfo data={componentData.farmList} /></div>}
      {componentVisibility.farmInfo && <div className={styles.componentWrapper}><FarmGuideInfo data={componentData.farmInfo} /></div>}
      {componentVisibility.favorites && <div className={styles.componentWrapper}><FavoritesInfo data={componentData.favorites} /></div>}
      {componentVisibility.myCrop && <div className={styles.componentWrapper}><MyPlantInfo data={componentData.myCrop} /></div>}
      {componentVisibility.recommendInfo && <div className={styles.componentWrapper}><RecommendInfo data={componentData.recommendInfo} /></div>}
      {componentVisibility.communityInfo && <div className={styles.componentWrapper}><CommunityInfo data={componentData.communityInfo} /></div>}
      <div className={styles.componentWrapper}><WeekendFarm /></div>
    </div>
  );
};

export default Main;
