// mainPageApi.tsx

import api from "../../utils/axios";

// todoInfo 타입 정의
interface TodoInfoProps {
  isUsable: boolean;
  todoType: string;
  title: string;
  myPlaceName: string;
  myplantName: string;
  plantName: string;
  plantImagePath: string;
  growthStep: string;
  todoDate: string;
  address: string;
  temperature: string;
}

// beginnerInfo 타입 정의
interface BeginnerInfoProps {
  isUsable: boolean;
  plants: any[]; // 구체적인 식물 정보 타입 정의 가능
}

// myFarmListInfo 타입 정의
interface FarmInfoProps {
  placeId: number;
  placeName: string;
  myPlaceId: number;
  myPlaceName: string;
}

interface MyFarmListInfoProps {
  isUsable: boolean;
  farms: FarmInfoProps[];
}

// farmGuideInfo 타입 정의
interface FarmGuideInfoProps {
  isUsable: boolean;
}

// favoritesInfo 타입 정의
interface FavoritesInfoProps {
  isUsable: boolean;
  favoritePlants: any[];
  favoritePlaces: any[];
}

// myPlantInfo 타입 정의
interface MyPlantInfoProps {
  isUsable: boolean;
  plantId: number;
  plantName: string;
}

// recommendInfo 타입 정의
interface RecommendPlantProps {
  plantId: number;
  plantName: string;
  plantDescription: string;
}

interface RecommendInfoByPlaceProps {
  comment: string;
  recommendPlants: RecommendPlantProps[];
}

interface RecommendInfoByUserProps {
  comment: string;
  recommendPlants: RecommendPlantProps[];
}

interface RecommendInfoProps {
  isUsable: boolean;
  recommendByPlace: RecommendInfoByPlaceProps;
  recommendByUser: RecommendInfoByUserProps;
}

// communityInfo 타입 정의
interface CommunityPostProps {
  communityId: number;
  title: string;
  content: string;
  imagePath: string | null;
  heartCount: number;
  commentCount: number;
  writer: string;
  writerImagePath: string;
  registerDate: string;
}

interface CommunityInfoProps {
  isUsable: boolean;
  tagName: string;
  communitySortedByRecents: CommunityPostProps[];
  communitySortedByPopularities: CommunityPostProps[];
}

// weekendFarm 타입 정의
interface WeekendFarmInfoProps {
  isUsable: boolean;
  address: string;
}

// 최종 main page 정보 타입 정의
export interface MainPageInfoProps {
  todoInfo: TodoInfoProps;
  beginnerInfo: BeginnerInfoProps;
  myFarmListInfo: MyFarmListInfoProps;
  farmGuideInfo: FarmGuideInfoProps;
  favoritesInfo: FavoritesInfoProps;
  myPlantInfo: MyPlantInfoProps;
  recommendInfo: RecommendInfoProps;
  communityInfo: CommunityInfoProps;
  weekendFarm: WeekendFarmInfoProps;
}

// MainPage 데이터를 가져오는 함수
export function getMainPageInfo(): Promise<MainPageInfoProps> {
  return api
    .get("/mainpage")
    .then((response) => {
      return Promise.resolve(response.data);
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}
