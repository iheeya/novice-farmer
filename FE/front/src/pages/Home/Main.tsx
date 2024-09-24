import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../../styles/Main/Main.module.css'; // CSS 모듈 임포트

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

const Main = () => {
  const navigate = useNavigate(); // 라우팅을 위해 useNavigate 사용

  const [componentData, setComponentData] = useState({
    todoInfo: {
      isUsable: true,
      todoType: "WATERING",
      title: "폭우주의보",
      farmName: "구미시 진평동 농장",
      cropName: "토순이",
      plantImagePath: "https://i.ibb.co/7KJVPPh/Lettuce3.png",
      todoDate: "2024-09-22T02:31:05.668Z",
      address: "구미시 진평동",
      temperature: "25°C"
    },
    beginnerInfo: {
      isUsable: true,
      plants: [
        { plantId: 1, plantName: "토마토", plantDesc: "초보 농부가 키우기 쉬운 토마토!" },
        { plantId: 2, plantName: "상추", plantDesc: "수확 시기가 짧아서 키우기 편한 상추!" }
      ]
    },
    myFarmListInfo: {
      isUsable: true,
      farms: [
        { placeId: 1, placeName: "베란다", myPlaceId: 1, myPlaceName: "우리집베란다" },
        { placeId: 2, placeName: "주말농장", myPlaceId: 2, myPlaceName: "구미농장" }
      ]
    },
    farmGuideInfo: {
      isUsable: true
    },
    favoritesInfo: {
      isUsable: true,
      favoritePlants: [
        { plantId: 1, plantName: "토마토" },
        { plantId: 2, plantName: "상추" }
      ],
      favoritePlaces: [
        { placeId: 1, placeName: "베란다" },
        { placeId: 2, placeName: "주말 농장" }
      ]
    },
    myPlantInfo: {
      isUsable: true,
      plantId: 1,
      plantName: "토마토"
    },
    recommendInfo: {
      isUsable: true,
      recommendByPlace: {
        comment: "서늘한 가을날, 구미 진평동에서 키우기 좋은 작물들은?",
        recommendPlants: [
          { plantId: 1, plantName: "바질", plantDescription : "라이코펜 가득!" },
          { plantId: 2, plantName: "로즈마리", plantDescription : "비타민C 풍부!" }
        ]
      },
      recommendByUser: {
        comment: "토마토를 키우는 20대 남성들에게 인기있는 작물들은?",
        recommendPlants: [
          { plantId: 3, plantName: "딸기",  plantDescription : "비타민B 풍부!" },
          { plantId: 4, plantName: "알로에" ,  plantDescription : "실내 허브"}
        ]
      }
    },
    communityInfo: {
      isUsable: true,
      tagName: "토마토",
      communitySortedByPopularities: [
        {
          communityId: 1,
          title: "토마토 베란다 재배 성공기",
          content: "토마토는 이렇게 베란다에서...",
          imagePath: "http://image.dongascience.com/Photo/2018/10/1e4e9eb4a36aaff4874434582f414eae.jpg",
          heartCount: 12,
          commentCount: 4,
          writer: "농사장인",
          writerImagePath: "https://www.urbanbrush.net/web/wp-content/uploads/edd/2022/04/urbanbrush-20220404144009535832.jpg",
          registerDate: "2024-09-20T02:31:05.668Z"
        },
        {
          communityId: 2,
          title: "유기농 토마토 재배법",
          content: "유기농으로 토마토를 키우는 방법은...",
          imagePath: "https://cdn.imweb.me/upload/S201909034d88b63749dfc/3f0e26124348b.jpg",
          heartCount: 15,
          commentCount: 6,
          writer: "말랑카우",
          writerImagePath: "https://www.handmk.com/news/photo/202306/16714_40371_5250.jpg",
          registerDate: "2024-09-19T02:31:05.668Z"
        },
        {
          communityId: 3,
          title: "베란다에서 키운 토마토",
          content: "작은 공간에서 토마토를 키우는 팁...",
          imagePath: "https://www.treeinfo.net/data/file/ti_gallery/thumb-2084243718_a7f6fbcc_DSC_0090_400x300.jpg",
          heartCount: 10,
          commentCount: 2,
          writer: "마가렛트",
          writerImagePath: "https://cdn.pixabay.com/photo/2020/05/17/20/21/cat-5183427_1280.jpg",
          registerDate: "2024-09-18T02:31:05.668Z"
        }
      ],
      communitySortedByRecents: [
        {
          communityId: 4,
          title: "토마토 실내 재배 방법",
          content: "실내에서도 토마토를 재배하는 법...",
          imagePath: "https://src.hidoc.co.kr/image/lib/2023/5/26/1685090646233_0.png",
          heartCount: 5,
          commentCount: 2,
          writer: "김새싹",
          writerImagePath: "https://cdn.pixabay.com/photo/2021/10/07/15/24/microgreen-6688950_640.jpg",
          registerDate: "2024-09-22T02:31:05.668Z"
        },
        {
          communityId: 5,
          title: "여름철 토마토 관리법",
          content: "여름철에 토마토를 잘 관리하는 법...",
          imagePath: "https://www.kfia.or.kr/kfia/webzine/201907/images/sub/sub_visual_food_recipe.jpg",
          heartCount: 8,
          commentCount: 3,
          writer: "마가렛트",
          writerImagePath: "https://cdn.pixabay.com/photo/2020/05/17/20/21/cat-5183427_1280.jpg",
          registerDate: "2024-09-21T02:31:05.668Z"
        },
        {
          communityId: 6,
          title: "베란다에서 키운 토마토의 수확",
          content: "베란다에서 키운 토마토를 수확하는 시기...",
          imagePath: "https://flexgcdn-natural8127.moall.shop/data/goods/natural8127/small/thum2/1000000241_magnify_087.jpg",
          heartCount: 11,
          commentCount: 5,
          writer: "농사장인",
          writerImagePath: "https://www.urbanbrush.net/web/wp-content/uploads/edd/2022/04/urbanbrush-20220404144009535832.jpg",
          registerDate: "2024-09-20T02:31:05.668Z"
        }
      ]
    },
    weekendFarm: {
      isUsable: true,
      address: "경상북도 구미시"
    }
  });

  const [componentVisibility, setComponentVisibility] = useState({
    todoInfo: true,
    beginnerInfo: true,
    myFarmListInfo: true,
    farmGuideInfo: true,
    favoritesInfo: true,
    myPlantInfo: true,
    recommendInfo: true,
    communityInfo: true,
    weekendFarm: true,
  });

  // 주말농장 클릭 핸들러
  const handleWeekendFarmClick = () => {
    navigate("/weekendFarm-recommend"); // 클릭 시 /weekendFarm-recommend로 이동
  };

  return (
    <div className={styles.mainContainer}>
      {componentVisibility.todoInfo && componentData.todoInfo.isUsable && (
        <div className={styles.componentWrapper}><TodoInfo data={componentData.todoInfo} /></div>
      )}
      <div className={styles.componentWrapper}><MyFarmListInfo data={componentData.myFarmListInfo} /></div>
      {componentVisibility.beginnerInfo && componentData.beginnerInfo.isUsable && (
        <div className={styles.componentWrapper}><BeginnerInfo data={componentData.beginnerInfo} /></div>
      )}
      {componentVisibility.farmGuideInfo && componentData.farmGuideInfo.isUsable && (
        <div className={styles.componentWrapper}>
          <FarmGuideInfo />
        </div>
      )}
      {componentVisibility.favoritesInfo && componentData.favoritesInfo.isUsable && (
        <div className={styles.componentWrapper}><FavoritesInfo data={componentData.favoritesInfo} /></div>
      )}
      {componentVisibility.myPlantInfo && componentData.myPlantInfo.isUsable && (
        <div className={styles.componentWrapper}><MyPlantInfo data={componentData.myPlantInfo} /></div>
      )}
      {componentVisibility.recommendInfo && componentData.recommendInfo.isUsable && (
        <div className={styles.componentWrapper}><RecommendInfo data={componentData.recommendInfo} /></div>
      )}
      <div className={styles.componentWrapper}><CommunityInfo data={componentData.communityInfo} /></div>
      {/* WeekendFarm 클릭 시 라우팅 */}
      <div className={styles.componentWrapper} onClick={handleWeekendFarmClick}>
        <WeekendFarm data={componentData.weekendFarm} />
      </div>
    </div>
  );
};

export default Main;
