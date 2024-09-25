import { useEffect, useState } from "react";
import { getSurveyInfo, postSurveyInfo } from "../../services/user/userapi";
import {
  Button,
  Box,
  Typography,
  Chip,
} from "@mui/material";

interface Plant {
  id: number;
  name: string;
  growthDay: number;
  isOn: boolean;
}

interface Place {
  id: number;
  name: string;
  desc: string;
  isOn: boolean;
}

export default function Survey() {
  const [plants, setPlants] = useState<Plant[]>([]);
  const [places, setPlaces] = useState<Place[]>([]);
  const [selectedPlants, setSelectedPlants] = useState<number[]>([]);
  const [selectedPlaces, setSelectedPlaces] = useState<number[]>([]);

  const NO_OPTION_ID = 0; // "없음"에 해당하는 ID

  // Survey 정보를 가져오는 함수 (API 호출)
  useEffect(() => {
    getSurveyInfo()
      .then((res) => {
        setPlants(res.plant);
        setPlaces(res.place);
      })
      .catch((err) => {
        console.error("Error fetching survey data:", err);
      });
  }, []);

  // 작물 선택 핸들러
  const handlePlantSelect = (id: number) => {
    if (id === NO_OPTION_ID) {
      // "없음" 선택 시 다른 선택지 해제
      setSelectedPlants([id]);
    } else {
      // 다른 작물 선택 시 "없음" 해제
      setSelectedPlants((prev) =>
        prev.includes(NO_OPTION_ID)
          ? [id]
          : prev.includes(id)
          ? prev.filter((plantId) => plantId !== id)
          : [...prev, id]
      );
    }
  };

  // 장소 선택 핸들러
  const handlePlaceSelect = (id: number) => {
    if (id === NO_OPTION_ID) {
      // "없음" 선택 시 다른 선택지 해제
      setSelectedPlaces([id]);
    } else {
      // 다른 장소 선택 시 "없음" 해제
      setSelectedPlaces((prev) =>
        prev.includes(NO_OPTION_ID)
          ? [id]
          : prev.includes(id)
          ? prev.filter((placeId) => placeId !== id)
          : [...prev, id]
      );
    }
  };

  // 설문 제출 함수
  const handleSubmit = () => {
    const selectedData = {
      plant: selectedPlants.map((id) => ({ id })), // 선택된 작물 ID 배열
      place: selectedPlaces.map((id) => ({ id })), // 선택된 장소 ID 배열
    };
    console.log(selectedData);

    // API 호출을 통해 설문 데이터 제출
    // postSurveyInfo(selectedData)
    //   .then(() => {
    //     console.log("Survey submitted successfully.");
    //     // 제출 성공 시 추가 처리 (예: 알림, 페이지 이동)
    //   })
    //   .catch((error) => {
    //     console.error("Error submitting survey:", error);
    //     // 제출 실패 시 에러 처리 (예: 사용자 알림)
    //   });
  };

  return (
    <Box
      sx={{
        margin: "0 auto",
        height: "100%",
        paddingX: "5%",
        backgroundColor: "white",
        borderRadius: "20px",
        textAlign: "center",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      {/* 제목 */}
      <Typography variant="h6" sx={{ marginBottom: 2 }}>
        선호하는 작물을 선택해주세요
      </Typography>

      {/* 작물 선택 부분 */}
      <Box sx={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: 2 }}>
        {plants.map((plant) => (
          <Chip
            key={plant.id}
            label={plant.name}
            color={selectedPlants.includes(plant.id) ? "success" : "default"}
            onClick={() => handlePlantSelect(plant.id)}
            clickable
            variant="outlined"
          />
        ))}
      </Box>

      {/* 장소 선택 질문 */}
      <Typography variant="h6" sx={{ marginTop: 4, marginBottom: 2 }}>
        키우고 싶은 장소가 어디인가요?
      </Typography>

      {/* 장소 선택 부분 */}
      <Box sx={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: 2 }}>
        {places.map((place) => (
          <Chip
            key={place.id}
            label={place.name}
            color={selectedPlaces.includes(place.id) ? "success" : "default"}
            onClick={() => handlePlaceSelect(place.id)}
            clickable
            variant="outlined"
          />
        ))}
      </Box>

      {/* 완료 버튼 */}
      <Button
        variant="contained"
        color="success"
        sx={{ marginTop: 4 }}
        onClick={handleSubmit}
      >
        완료
      </Button>
    </Box>
  );
}
