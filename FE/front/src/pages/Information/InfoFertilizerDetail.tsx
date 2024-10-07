import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Box, Typography } from "@mui/material";
import { getInfoFertilizerDetail } from "../../services/Information/InfoApi";
import { GetImage } from "../../services/getImage";

interface ContentProps {
  title: string;
  content: string;
}

interface DetailDataType {
  images: string[];
  contents: ContentProps[];
}

export default function InfoFertilizerDetail() {
  const { title } = useParams<{ title: string }>();
  const [detailData, setDetailData] = useState<DetailDataType | null>(null); // 데이터 상태 관리
  const [imageUrl, setImageUrl] = useState<string | null>(null);

  // 데이터 가져오기
  useEffect(() => {
    if (title) {
      getInfoFertilizerDetail(title)
        .then((res) => {
          setDetailData(res); // API 응답 데이터 설정
          if (res.images && res.images.length > 0) {
            // 첫 번째 이미지의 URL 가져오기
            GetImage(res.images[0]).then((url) => {
              setImageUrl(url);
            });
          }
        })
        .catch((err) => {
          console.error("Error fetching fertilizer detail:", err);
        });
    }
  }, [title]);

  // 데이터가 로드되지 않았을 때 로딩 메시지 출력
  if (!detailData || !detailData.contents) {
    return (
      <Box sx={{ textAlign: "center", p: 4 }}>
        <Typography variant="h6">데이터를 불러오는 중입니다...</Typography>
      </Box>
    );
  }

  return (
    <Box
      sx={{
        width: "100%",
        maxWidth: 360,
        margin: "0 auto", // 가운데 정렬
        padding: 2,
        bgcolor: "#fff",
      }}
    >
      {/* 이미지 출력 */}
      {imageUrl && (
        <Box
          component="img"
          src={imageUrl}
          alt={title}
          sx={{ width: "100%", height: "auto", borderRadius: 2, mb: 3 }}
        />
      )}

      {/* 내용 출력 */}
      {detailData.contents &&
        detailData.contents.map((content, index) => (
          <Box key={index} sx={{ mb: 3 }}>
            <Typography
              variant="h6"
              sx={{ fontWeight: "bold", color: "#5B8E55", fontSize: "1.5rem" }}
            >
              {content.title}
            </Typography>
            <Typography variant="body1" sx={{ mt: 1, color: "#333" }}>
              {content.content}
            </Typography>
          </Box>
        ))}
    </Box>
  );
}
