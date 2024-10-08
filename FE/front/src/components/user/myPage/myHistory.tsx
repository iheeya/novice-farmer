import { useEffect, useState } from "react";
import { getMyHistory } from "../../../services/user/myPageApi";
import { Box, Typography, Card, CardContent, CardMedia, Button } from "@mui/material";
import { PlantProps } from "../../../services/user/myPageApi";
import { useNavigate } from "react-router-dom";
import { GetImage } from "../../../services/getImage";

export default function MyHistory() {
  const [history, setHistory] = useState<PlantProps[]>([]); // 빈 배열로 초기화
  const [loading, setLoading] = useState<boolean>(true); // 로딩 상태 관리
  const [error, setError] = useState<string | null>(null); // 에러 상태 관리
  const [imageUrls, setImageUrls] = useState<{ [key: number]: string }>({}); // 이미지 URL 상태 관리
  const navigate = useNavigate();

  // 데이터 불러오기
  useEffect(() => {
    getMyHistory()
      .then(async (res) => {
        console.log("API 응답 데이터:", res); // 응답 데이터 확인
        setHistory(res); // 응답이 배열 형태라면 그대로 설정

        // 각 plant 객체에 대해 이미지 URL 가져오기
        const urls = await Promise.all(
          res.map(async (plant) => {
            const imageUrl = await GetImage(plant.imageurl); // 각 이미지 URL 가져오기
            return { id: plant.id, url: imageUrl };
          })
        );

        // 이미지 URL 상태 업데이트
        const imageMap: { [key: number]: string } = {};
        urls.forEach((item) => {
          imageMap[item.id] = item.url;
        });
        setImageUrls(imageMap); // 이미지 URL 상태 설정
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
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          height: "50vh",
        }}
      >
        <Typography variant="h6">수확하신 작물이 없어요</Typography>
        <Button
          variant="contained"
          sx={{ backgroundColor: "#5b8e55", marginTop: "3vh" }}
          onClick={() => {
            navigate("/register/garden");
          }}
        >
          작물 등록하기
        </Button>
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
            image={imageUrls[plant.id]} // 이미지가 없으면 대체 이미지 사용
            alt={plant.plantname || "식물 이미지"}
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
