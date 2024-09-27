import { useEffect, useState } from "react";
import { getSurveyInfo } from "../../services/user/surveyApi";
import { Button, Box, Typography, Chip, IconButton } from "@mui/material";
import { useNavigate } from "react-router-dom";
import {handlePlantSelect, handlePlaceSelect, handleSubmit} from "../../services/user/surveyHandler";

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
  const [selectedPlants, setSelectedPlants] = useState<number[]>([0]);
  const [selectedPlaces, setSelectedPlaces] = useState<number[]>([0]);
  const navigate = useNavigate();

  // Survey 정보를 가져오는 함수 (API 호출)
  useEffect(() => {
    getSurveyInfo()
      .then((res) => {
        setPlants(res.plant);
        setPlaces(res.place);
      })
      .catch((err) => {
        console.error("설문조사 목록 get failed", err);
      });
  }, []);

  return (
    <Box
      sx={{
        margin: "0 auto",
        height: "90vh",
        paddingX: "5%",
        backgroundColor: "white",
        borderRadius: "20px",
        textAlign: "center",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        maxWidth: "100%",
        position: "relative", // for placing the skip button at the top
      }}
    >
      {/* 제목 */}
      <Typography variant="h6" sx={{ marginBottom: "2vh", fontSize: "6vw", color: "#67823a", fontWeight: "normal" }}>
        어떤 작물에 관심이 있으신가요?
      </Typography>

      {/* 작물 선택 부분 */}
      <Box sx={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: "2vh" }}>
        {plants.map((plant) => (
          <Chip
            key={plant.id}
            label={plant.name}
            color={selectedPlants.includes(plant.id) ? "success" : "default"}
            onClick={() => handlePlantSelect(plant.id, selectedPlants, setSelectedPlants)}
            clickable
            sx={{
              padding: "1vh",
              fontSize: "3vw",
              borderColor: selectedPlants.includes(plant.id) ? "#67823a" : "#84b366",
              borderWidth: "2px",
              color: selectedPlants.includes(plant.id) ? "#67823a" : "#84b366",
            }}
            variant="outlined"
          />
        ))}
      </Box>

      {/* 장소 선택 질문 */}
      <Typography variant="h6" sx={{ marginTop: "4vh", marginBottom: "2vh", fontSize: "6vw", color: "#67823a" }}>
        어디서 키우고 싶으신가요?
      </Typography>

      {/* 장소 선택 부분 */}
      <Box sx={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: "2vh" }}>
        {places.map((place) => (
          <Chip
            key={place.id}
            label={place.name}
            color={selectedPlaces.includes(place.id) ? "success" : "default"}
            onClick={() => handlePlaceSelect(place.id, selectedPlaces, setSelectedPlaces)}
            clickable
            sx={{
              padding: "1vh",
              fontSize: "3vw",
              borderColor: selectedPlaces.includes(place.id) ? "#67823a" : "#84b366",
              borderWidth: "2px",
              color: selectedPlaces.includes(place.id) ? "#67823a" : "#84b366",
            }}
            variant="outlined"
          />
        ))}
      </Box>
      {/* 완료 버튼 */}
      <Box
        sx={{
          display: "flex",
          justifyContent: "flex-end", // 버튼을 우측에 정렬
          width: "100%", // 버튼의 부모 박스가 화면 너비를 차지하도록 설정
        }}
      >
        <Button
          variant="contained"
          color="success"
          sx={{
            marginTop: "4vh",
            padding: "0.7vh 3vw",
            fontSize: "4vw",
            backgroundColor: "#67823a",
          }}
          onClick={() => handleSubmit(selectedPlants, selectedPlaces, navigate)}
        >
          완료
        </Button>
      </Box>
    </Box>
  );
}
