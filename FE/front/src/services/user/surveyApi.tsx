import api from "../../utils/axios";

interface postSurveyInfoProps {
  plant: { id: number }[];
  place: { id: number }[];
}

// 설문조사 리스트 get 함수
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

// 설문조사 제출 함수
export function postSurveyInfo({ plant, place }: postSurveyInfoProps): Promise<any> {
  console.log({ plant, place });
  return api
    .post("/user/survey", { plant, place })
    .then((response) => {
      return Promise.resolve();
    })
    .catch((error) => {
      return Promise.reject(error);
    });
}
