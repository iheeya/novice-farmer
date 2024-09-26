import api from "../../utils/axios";

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

export function getMyPage(): Promise<{
    email: string;
    nickname: string;
    regDate: string;
    isFirstLogin: boolean;
    gender: string;
    age: number;
    address: string;
    pushAllow: boolean;
  }> {
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
