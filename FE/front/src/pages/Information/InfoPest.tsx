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
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { getInfoPest } from "../../services/Information/InfoApi";
import { GetImage } from "../../services/getImage";
import BookIcon from "../../assets/icons/Book.png"; // 기본 아이콘 이미지 경로 설정
import { useNavigate } from "react-router-dom";

// 데이터 타입 정의
interface PestDataProps {
  title: string;
  comment: string;
  imageName: string;
  imageUrl?: string | null; // S3에서 가져온 이미지 URL 추가
}

export default function InfoPest() {
  const [pestData, setPestData] = useState<PestDataProps[]>([]); // 데이터 상태 관리
  const [loading, setLoading] = useState(true); // 로딩 상태 관리
  const navigate = useNavigate();

  // S3 이미지 URL을 가져오는 비동기 함수
  const fetchImageUrls = async (data: PestDataProps[] = []) => {
    if (!data || data.length === 0) {
      return; // data가 비어있거나 undefined/null인 경우 함수 종료
    }
    try {
      // 모든 이미지 URL을 가져오고 상태에 반영
      const updatedData = await Promise.all(
        data.map(async (item) => {
          if (item.imageName) {
            const imageUrl = await GetImage(item.imageName); // S3에서 이미지 URL 가져오기
            return { ...item, imageUrl }; // 기존 데이터에 imageUrl 추가
          } else {
            return { ...item, imageUrl: null };
          }
        })
      );
      setPestData(updatedData); // 상태 업데이트
    } catch (error) {
      console.error("Error fetching image URLs:", error);
    }
  };

  // 데이터 로드
  useEffect(() => {
    getInfoPest()
      .then((res) => {
        fetchImageUrls(res); // 이미지 URL을 가져와서 설정
      })
      .catch((err) => {
        console.error("Error fetching pest data:", err);
      })
      .finally(() => {
        setLoading(false); // 로딩 상태 업데이트
      });
  }, []);

  const handleClickOpen = (title: string) => {
    navigate(`/info/plant/pest/${encodeURIComponent(title)}`); // URL 이동
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
        병해충 정보
      </Typography>

      {/* 데이터가 로드 중일 때 로딩 아이콘 표시 */}
      {loading ? (
        <Typography variant="body1" sx={{ textAlign: "center", mt: 4 }}>
          <CircularProgress color="success" />
        </Typography>
      ) : (
        <List>
          {/* 병해충 데이터 리스트 아이템 출력 */}
          {pestData.map((item, index) => (
            <ListItem
              key={index}
              sx={{
                borderBottom: "1px solid #ddd",
                cursor: "pointer",
                my: 1, // 리스트 아이템 사이의 간격 추가
                alignItems: "center", // 아이콘과 텍스트를 수직 중앙 정렬
              }}
              onClick={() => handleClickOpen(item.title)}
            >
              <ListItemIcon>
                {item.imageUrl ? (
                  <img src={item.imageUrl} width="40" height="40" alt={item.title} />
                ) : (
                  <img src={BookIcon} width="40" height="40" alt="버그 아이콘" /> // 기본 아이콘 표시
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
