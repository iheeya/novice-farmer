import { useEffect, useState } from "react";
import { getSurveyInfo } from "../../services/user/surveyApi";
import { Button, Box, Typography, Chip } from "@mui/material";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2"; // SweetAlert2 임포트
import { handleSubmit } from "../../services/user/surveyHandler";

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
        console.error("설문조사 목록 가져오기 실패", err);
      });
  }, []);

  // 작물 선택 핸들러
  const handlePlantSelect = (plantId: number) => {
    if (plantId === 0) {
      setSelectedPlants([0]); // "없음"을 선택하면 다른 선택 항목 모두 해제
    } else {
      const updatedSelectedPlants = selectedPlants.includes(plantId)
        ? selectedPlants.filter((id) => id !== plantId)
        : [...selectedPlants.filter((id) => id !== 0), plantId];
      setSelectedPlants(updatedSelectedPlants.length === 0 ? [0] : updatedSelectedPlants);
    }
  };

  // 장소 선택 핸들러
  const handlePlaceSelect = (placeId: number) => {
    if (placeId === 0) {
      setSelectedPlaces([0]); // "없음"을 선택하면 다른 선택 항목 모두 해제
    } else {
      const updatedSelectedPlaces = selectedPlaces.includes(placeId)
        ? selectedPlaces.filter((id) => id !== placeId)
        : [...selectedPlaces.filter((id) => id !== 0), placeId];
      setSelectedPlaces(updatedSelectedPlaces.length === 0 ? [0] : updatedSelectedPlaces);
    }
  };

  // 완료 버튼 클릭 시 SweetAlert 표시
  const handleComplete = () => {
    // 서버에 전송하는 로직 (API 호출 등)
    handleSubmit(selectedPlants, selectedPlaces, navigate);

    // SweetAlert로 성공 메시지 표시
    Swal.fire({
      title: '설문 완료!',
      text: '성공적으로 설문이 완료되었습니다.',
      icon: 'success',
      confirmButtonText: '확인',
    })
  };

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
        position: "relative",
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
            onClick={() => handlePlantSelect(plant.id)}
            clickable
            sx={{
              padding: "1vh",
              fontSize: "3vw",
              borderColor: selectedPlants.includes(plant.id) ? "#67823a" : "#84b366",
              borderWidth: "2px",
              borderStyle: "solid",
              borderRadius: "20px",
              fontWeight: selectedPlants.includes(plant.id) ? "bold" : "normal",
              transform: selectedPlants.includes(plant.id) ? "scale(1.2)" : "scale(1)",
              transition: "all 0.3s ease-in-out",
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
            onClick={() => handlePlaceSelect(place.id)}
            clickable
            sx={{
              padding: "1vh",
              fontSize: "3vw",
              borderColor: selectedPlaces.includes(place.id) ? "#67823a" : "#84b366",
              borderWidth: "2px",
              borderStyle: "solid",
              borderRadius: "20px",
              fontWeight: selectedPlaces.includes(place.id) ? "bold" : "normal",
              transform: selectedPlaces.includes(place.id) ? "scale(1.2)" : "scale(1)",
              transition: "all 0.3s ease-in-out",
            }}
            variant="outlined"
          />
        ))}
      </Box>

      {/* 완료 버튼 */}
      <Box sx={{ display: "flex", justifyContent: "flex-end", width: "100%" }}>
        <Button
          variant="contained"
          color="success"
          sx={{
            marginTop: "4vh",
            padding: "0.7vh 3vw",
            fontSize: "4vw",
            backgroundColor: "#67823a",
          }}
          onClick={handleComplete}
        >
          완료
        </Button>
      </Box>
    </Box>
  );
}
