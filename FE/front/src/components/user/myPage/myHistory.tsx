import { useEffect, useState } from "react";
import { getMyHistory } from "../../../services/user/myPageApi";
import { Box, Typography, Card, CardContent, CardMedia, CircularProgress } from "@mui/material";
import { PlantProps } from "../../../services/user/myPageApi"; // 타입 가져오기

export default function MyHistory() {
  const [history, setHistory] = useState<PlantProps[]>([]); // 빈 배열로 초기화
  const [loading, setLoading] = useState<boolean>(true); // 로딩 상태 관리
  const [error, setError] = useState<string | null>(null); // 에러 상태 관리

  // 데이터 불러오기
  useEffect(() => {
    getMyHistory()
      .then((res) => {
        console.log("API 응답 데이터:", res);  // 응답 데이터 확인
        setHistory(res); // 응답이 배열 형태라면 그대로 설정
        setLoading(false); // 로딩 상태 종료
      })
      .catch((err) => {
        console.error("Failed to fetch history", err);
        setError("데이터를 불러오는 데 실패했습니다."); // 에러 메시지 설정
        setLoading(false); // 로딩 상태 종료
      });
  }, []); 

  // 데이터가 없는 경우
  if (history.length === 0) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
        <Typography variant="h6">기록이 없습니다.</Typography>
      </Box>
    );
  }

  // 데이터가 있을 때
  return (
    <Box sx={{ padding: "16px", display: "flex", flexWrap: "wrap", gap: "16px", justifyContent: "center" }}>
      {history.map((plant) => (
        <Card key={plant.id} sx={{ maxWidth: 345 }}>
          {/* 이미지가 null인 경우 대체 이미지 사용 */}
          <CardMedia
            component="img"
            height="140"
            // image={plant.imageurl || "/path/to/default_image.jpg"} // 기본 이미지 경로
            alt={"식물 이미지"}            
          />
          <CardContent>
            <Typography gutterBottom variant="h5" component="div">
              {plant.plantmyname}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {`종류: ${plant.plantname}`}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {`위치: ${plant.placename}`}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {`씨앗 심은 날짜: ${new Date(plant.seedDate).toLocaleDateString()}`}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {`첫 수확 날짜: ${new Date(plant.firstHarvestDate).toLocaleDateString()}`}
            </Typography>
          </CardContent>
        </Card>
      ))}
    </Box>
  );
}
