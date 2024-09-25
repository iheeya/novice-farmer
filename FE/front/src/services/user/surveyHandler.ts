// src/services/user/surveyHandler.ts

import { postSurveyInfo } from "./surveyApi";
import { NavigateFunction } from "react-router-dom";

// 작물 선택 핸들러
export const handlePlantSelect = (
  id: number,
  selectedPlants: number[],
  setSelectedPlants: React.Dispatch<React.SetStateAction<number[]>>
) => {
  if (id === 0) {
    setSelectedPlants([id]); // "없음" 선택 시 다른 선택지 해제
  } else {
    setSelectedPlants((prev) =>
      prev.includes(0)
        ? [id]
        : prev.includes(id)
        ? prev.filter((plantId) => plantId !== id)
        : [...prev, id]
    );
  }
};

// 장소 선택 핸들러
export const handlePlaceSelect = (
  id: number,
  selectedPlaces: number[],
  setSelectedPlaces: React.Dispatch<React.SetStateAction<number[]>>
) => {
  if (id === 0) {
    setSelectedPlaces([id]); // "없음" 선택 시 다른 선택지 해제
  } else {
    setSelectedPlaces((prev) =>
      prev.includes(0)
        ? [id]
        : prev.includes(id)
        ? prev.filter((placeId) => placeId !== id)
        : [...prev, id]
    );
  }
};

// 설문 제출 함수
export const handleSubmit = (
  selectedPlants: number[],
  selectedPlaces: number[],
  navigate: NavigateFunction
) => {
  const selectedData = {
    plant: selectedPlants.map((id) => ({ id })), // 선택된 작물 ID 배열
    place: selectedPlaces.map((id) => ({ id })), // 선택된 장소 ID 배열
  };

  postSurveyInfo(selectedData)
    .then(() => {
      console.log("Survey submitted successfully.");
      navigate("/"); // 제출 후 다른 페이지로 이동
    })
    .catch((error) => {
      console.error("Error submitting survey:", error);
    });
};

// SKIP 버튼 클릭 시 "없음"으로 설정하고 바로 제출
export const handleSkip = (
  setSelectedPlants: React.Dispatch<React.SetStateAction<number[]>>,
  setSelectedPlaces: React.Dispatch<React.SetStateAction<number[]>>,
  navigate: NavigateFunction
) => {
  // 상태 업데이트 후 콜백 함수 내에서 submit 실행
  setSelectedPlants([0]);
  setSelectedPlaces([0]);

  // 상태가 업데이트된 후에 제출을 진행 (이전 상태가 아니고 0으로 업데이트된 후 제출)
  handleSubmit([0], [0], navigate); // 업데이트된 상태를 바로 전달
};



