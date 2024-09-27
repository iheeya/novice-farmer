import api from "../../utils/axios";

// 프로필 이미지 URL 은 어디에?
interface userInfoProps{
  email: string;
  nickname: string;
  regDate: string;
  isFirstLogin: boolean;
  gender: string;
  age: number;
  address: string;
  pushAllow: boolean;
}

interface getMyLikeInfoProps {
  plant: { id: number, name: string, isFavorite: boolean }[];
  place: { id: number, name: string, desc:string, isFavorite: boolean }[];
}

export interface PlantProps {
  id: number;
  plantname: string;
  plantmyname: string;
  placename: string;
  seedDate: string;
  firstHarvestDate: string;
  imageurl: string | null; 
}

interface postMyLikeProps {
  plant: { id: number }[];
  place: { id: number }[];
}

export function getSurveyInfo(): Promise<any> {
  return api
    .get("/user/survey")
    .then((response) => {
      return Promise.resolve(response.data);
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}

export function getMyInfo(): Promise<userInfoProps> {
  return api
    .get("/user/mypage")
    .then((response) => {
      console.log(response.data);
      return Promise.resolve(response.data);
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}

export function getMyHistory(): Promise<PlantProps[]> {
  return api
    .get("/user/mypage/history")
    .then((response) => {
      console.log(response.data);
      return Promise.resolve(response.data);
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}

export function getMyLike():Promise<getMyLikeInfoProps>{
  return api
    .get("/user/mypage/like")
    .then((response) => {
      return Promise.resolve(response.data);
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}

export function postMyLike({ plant, place }: postMyLikeProps):Promise<any>{
  return api
    .post("/user/mypage/like",{ plant, place })
    .then((response) => {
      return Promise.resolve(response.data);
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}