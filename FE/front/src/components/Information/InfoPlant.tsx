import React, { useEffect, useState } from "react";
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  CircularProgress,
} from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos"; // 오른쪽 화살표 아이콘
import { useNavigate } from "react-router-dom";
import { getInfoPlant } from "../../services/Information/InfoApi";
import { GetImage } from "../../services/getImage"; // S3 이미지 URL을 가져오는 함수
import BookIcon from "../../assets/icons/Book.png"; // 아이콘 이미지 경로 설정

// 데이터 타입 정의
interface PlantDataProps {
  title: string;
  comment: string;
  imageName: string;
  imageUrl?: string|null; // S3에서 가져온 이미지 URL을 추가
}

export default function InfoPlant() {
  const [plantData, setPlantData] = useState<PlantDataProps[]>([]); // 데이터 상태 관리
  const [loading, setLoading] = useState(true); // 로딩 상태 관리
  const navigate = useNavigate(); // useNavigate 훅 사용하여 URL 이동

  // S3 이미지 URL을 가져오는 비동기 함수
  const fetchImageUrls = async (data: PlantDataProps[] = []) => {
    if (!data || data.length === 0) {
      return; // data가 비어있거나 undefined/null인 경우 함수 종료
    }
    try {
      // 모든 이미지 URL을 가져오고 상태에 반영
      const updatedData = await Promise.all(
        data.map(async (item) => {
          if (item.imageName){
            const imageUrl = await GetImage(item.imageName); // GetImage 함수로 S3 URL 가져오기
            return { ...item, imageUrl }; // 기존 데이터에 imageUrl 추가
          } else {
            return { ...item, imageUrl:null}
          }
        })
      );
      setPlantData(updatedData); // 상태 업데이트
    } catch (error) {
      console.error("Error fetching image URLs:", error);
    }
  };

  // 데이터 로드
  useEffect(() => {
    getInfoPlant()
      .then((res) => {
        fetchImageUrls(res); // 이미지 URL을 가져와서 설정
      })
      .catch((err) => {
        console.error("Error fetching plant data:", err);
      })
      .finally(() => {
        setLoading(false); // 로딩 상태 업데이트
      });
  }, []);

  // 특정 제목에 따라 경로 이동
  const handleClickOpen = (title: string) => {
    switch (title) {
      case "비료":
        navigate("/info/plant/fertilizer");
        break;
      case "병해충":
        navigate("/info/plant/pest");
        break;
      default:
        navigate(`/info/plant/${encodeURIComponent(title)}`); // 기본 경로 이동
        break;
    }
  };

  return (
    <Box
      sx={{
        width: "100%",
        maxWidth: 360,
        bgcolor: "background.paper",
        margin: "0 auto", // 가운데 정렬
        paddingTop: 2,
      }}
    >
      <Typography variant="h4" sx={{ textAlign: "center", mb: 2 }}>
        작물 정보
      </Typography>

      {/* 데이터가 있을 때만 리스트 렌더링 */}
      {loading ? (
        <Typography variant="body1" sx={{ textAlign: "center", mt: 4 }}>
          <CircularProgress color="success" />
        </Typography>
      ) : (
        <List>
          {plantData.map((item, index) => (
            <ListItem
              key={index}
              sx={{
                borderBottom: "1px solid #ddd",
                cursor: "pointer",
                my: 1, // 리스트 아이템 사이의 간격 추가
                alignItems: "center", // 아이콘과 텍스트를 수직 중앙 정렬
              }}
              onClick={() => handleClickOpen(item.title)} // 클릭 시 조건에 따라 URL 이동
            >
              <ListItemIcon>
                {item.imageUrl ? (
                  <img src={item.imageUrl} width="40" height="40" alt={item.title} />
                ) : (
                  <img src={BookIcon} width="40" height="40" alt="책아이콘" /> // 기본 아이콘 표시
                )}
              </ListItemIcon>
              <ListItemText
                primary={item.title}
                secondary={item.comment}
                primaryTypographyProps={{
                  fontSize: "1.3rem", // 제목 텍스트 크기 설정
                  fontWeight: "bold", // 제목 텍스트 굵기 설정
                  color: "#333", // 제목 색상
                }}
                secondaryTypographyProps={{
                  fontSize: "0.98rem", // 보조 텍스트 크기 설정
                  color: "#777", // 보조 텍스트 색상 설정
                }}
                sx={{ mr: 2 }} // 오른쪽 화살표와 간격 조정
              />
              <ArrowForwardIosIcon sx={{ color: "#bbb" }} /> {/* 오른쪽 화살표 아이콘 */}
            </ListItem>
          ))}
        </List>
      )}
    </Box>
  );
}
