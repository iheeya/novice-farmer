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
import { getInfoPlaceType } from "../../services/Information/InfoApi"; // API 호출 함수
import { GetImage } from "../../services/getImage"; // S3 이미지 URL을 가져오는 함수

// 데이터 타입 정의
interface TypeDataProps {
  title: string;
  comment: string;
  imageName: string;
  imageUrl?: string; // S3에서 가져온 이미지 URL을 추가
}

export default function InfoPlaceType() {
  const [placeData, setPlaceData] = useState<TypeDataProps[]>([]); // 데이터 상태 관리
  const [loading, setLoading] = useState(true); // 로딩 상태 관리
  const navigate = useNavigate(); // useNavigate 훅 사용하여 URL 이동

  // S3 이미지 URL을 가져오는 비동기 함수
  const fetchImageUrls = async (data: TypeDataProps[] = []) => {
    if (!data || data.length === 0) {
      return; // data가 비어있거나 undefined/null인 경우 함수 종료
    }
    try {
      // 모든 이미지 URL을 가져오고 상태에 반영
      const updatedData = await Promise.all(
        data.map(async (item) => {
          const imageUrl = await GetImage(item.imageName); // GetImage 함수로 S3 URL 가져오기
          return { ...item, imageUrl }; // 기존 데이터에 imageUrl 추가
        })
      );
      setPlaceData(updatedData); // 상태 업데이트
    } catch (error) {
      console.error("Error fetching image URLs:", error);
    }
  };

  // 컴포넌트 마운트 시 데이터 로드
  useEffect(() => {
    getInfoPlaceType()
      .then((response) => {
        if (response) {
          fetchImageUrls(response); // 이미지 URL을 가져와서 설정
        } else {
          console.error("Invalid response data:", response);
        }
      })
      .catch((error) => {
        console.error("Error fetching place data:", error);
      })
      .finally(() => {
        setLoading(false); // 로딩 완료 상태 설정
      });
  }, []);

  // 리스트 항목 클릭 시 경로 이동
  const handleClickOpen = (title: string) => {
    navigate(`/info/place/type/${encodeURIComponent(title)}`); // URL 이동
  };

  if (loading) {
    return (
      <Box sx={{ textAlign: "center", padding: "20px" }}>
        <Typography variant="h6">데이터를 불러오는 중입니다...</Typography>
      </Box>
    );
  }

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
      {/* 데이터가 있을 때만 리스트 렌더링 */}
      {placeData.length > 0 ? (
        <List>
          {placeData.map((item, index) => (
            <ListItem
              key={index}
              sx={{
                borderBottom: "1px solid #ddd",
                cursor: "pointer",
                my: 1, // 리스트 아이템 사이의 간격 추가
                alignItems: "center", // 아이콘과 텍스트를 수직 중앙 정렬
              }}
              onClick={() => handleClickOpen(item.title)} // 클릭 시 URL 이동
            >
              <ListItemIcon>
                {item.imageUrl ? (
                  <img
                    src={item.imageUrl} // S3에서 가져온 이미지 URL 사용
                    alt={item.title}
                    style={{ width: "40px", height: "40px", borderRadius: "8px" }}
                  />
                ) : (
                  <Typography variant="body2">이미지 로딩 중...</Typography> // 이미지 로딩 중 표시
                )}
              </ListItemIcon>
              <ListItemText
                primary={item.title}
                secondary={item.comment}
                sx={{ mr: 2 }} // 오른쪽 화살표와 간격 조정
              />
              <ArrowForwardIosIcon sx={{ color: "#bbb" }} /> {/* 오른쪽 화살표 아이콘 */}
            </ListItem>
          ))}
        </List>
      ) : (
        <Typography variant="body1" sx={{ textAlign: "center", mt: 4 }}>
          <CircularProgress color="success" />
        </Typography>
      )}
    </Box>
  );
}
