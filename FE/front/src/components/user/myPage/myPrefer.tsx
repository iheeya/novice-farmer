import { useEffect, useState } from "react";
import { getMyLike, postMyLike } from "../../../services/user/myPageApi";
import { Box, Chip, Typography, Button } from "@mui/material";
import { handlePlantSelect, handlePlaceSelect } from "../../../services/user/surveyHandler";
import Swal from "sweetalert2"; // SweetAlert2 임포트

interface Plant {
  id: number;
  name: string;
  isFavorite: boolean;
}

interface Place {
  id: number;
  name: string;
  desc: string;
  isFavorite: boolean;
}

export default function MyPrefer() {
  const [plants, setPlants] = useState<Plant[]>([]);
  const [places, setPlaces] = useState<Place[]>([]);
  const [selectedPlants, setSelectedPlants] = useState<number[]>([0]);
  const [selectedPlaces, setSelectedPlaces] = useState<number[]>([0]);

  // 관심 항목을 가져오는 함수
  useEffect(() => {
    getMyLike()
      .then((res) => {
        setPlants(res.plant);
        setPlaces(res.place);

        const initialSelectedPlants = res.plant
          .filter((plant: Plant) => plant.isFavorite)
          .map((plant: Plant) => plant.id);

        const initialSelectedPlaces = res.place
          .filter((place: Place) => place.isFavorite)
          .map((place: Place) => place.id);

        setSelectedPlants(initialSelectedPlants);
        setSelectedPlaces(initialSelectedPlaces);
      })
      .catch((err) => {
        console.error(err);
      });
  }, []);

  // 완료 버튼 클릭 시 관심 항목 수정
  const handleUpdate = () => {
    postMyLike({
      plant: selectedPlants.map((id) => ({ id })), // 숫자 배열을 객체 배열로 변환
      place: selectedPlaces.map((id) => ({ id })), // 숫자 배열을 객체 배열로 변환
    })
      .then(() => {
        // 성공 알림 표시
        Swal.fire({
          title: "수정 완료!",
          text: "관심 항목이 성공적으로 수정되었습니다.",
          icon: "success",
          confirmButtonText: "확인",
        });

        // 변경된 데이터 가져와서 화면 업데이트
        getMyLike()
          .then((res) => {
            setPlants(res.plant);
            setPlaces(res.place);

            const initialSelectedPlants = res.plant
              .filter((plant: Plant) => plant.isFavorite)
              .map((plant: Plant) => plant.id);

            const initialSelectedPlaces = res.place
              .filter((place: Place) => place.isFavorite)
              .map((place: Place) => place.id);

            setSelectedPlants(initialSelectedPlants);
            setSelectedPlaces(initialSelectedPlaces);
          })
          .catch((err) => {
            console.error(err);
          });
      })
      .catch((err) => {
        // 오류 발생 시 경고 알림 표시
        Swal.fire({
          title: "수정 실패!",
          text: "관심 항목을 수정하는 데 실패했습니다.",
          icon: "error",
          confirmButtonText: "확인",
        });
        console.error(err);
      });
  };

  return (
    <Box>
      {/* 제목 */}
      <Typography variant="h6" sx={{ marginBottom: "2vh", fontSize: "6vw", color: "#67823a", fontWeight: "bold" }}>
        # 작물
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
              transform: selectedPlants.includes(plant.id) ? "scale(1.2)" : "scale(1)",
              transition: "all 0.3s ease-in-out",
            }}
            variant="outlined"
          />
        ))}
      </Box>

      {/* 장소 선택 질문 */}
      <Typography
        variant="h6"
        sx={{ marginTop: "4vh", marginBottom: "2vh", fontSize: "6vw", color: "#67823a", fontWeight: "bold" }}
      >
        # 텃밭
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
              transform: selectedPlaces.includes(place.id) ? "scale(1.2)" : "scale(1)",
              transition: "all 0.3s ease-in-out",
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
          onClick={handleUpdate}
        >
          수정
        </Button>
      </Box>
    </Box>
  );
}
