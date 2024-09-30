import React from "react";
import { Container, Box, Button, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
const HomePage = () => {
    const navigate = useNavigate();
  return (
    <Container maxWidth="lg">
      {/* 최상단 헤더 및 소개 섹션 */}
      <Box
        sx={{
          textAlign: "center",
          py: 4,
        }}
      >
        <Typography variant="h4" gutterBottom>
          새싹 농부가 되어볼까요?
        </Typography>
        <Typography variant="body1" color="textSecondary" gutterBottom>
          새싹 농부는 개인 맞춤 작물 추천 및 일과 관리를 통해 처음 시작하는 사람도 손쉽게 작물을 키울 수 있게 해줍니다
        </Typography>
        <Button variant="contained" color="success" size="large" onClick={()=>{navigate("/user/login")}}>
          시작하기
        </Button>
      </Box>

      {/* 주요 소개 섹션 */}
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          textAlign: "center",
          py: 4,
        }}
      >
        {/* 첫 번째 섹션 */}
        <Box
          sx={{
            display: "flex",
            flexDirection: { xs: "column", md: "row" },
            alignItems: "center",
            justifyContent: "space-between",
            width: "100%",
            mb: 4,
          }}
        >
          {/* 이미지가 왼쪽, 텍스트가 오른쪽 */}
          <Box component="img" src="/landing/landing1.png" alt="작물 추천 페이지" sx={{ width: "100px", mr: { md: 2, xs: 0 }, mb: { xs: 2, md: 0 } }} />
          <Box>
            <Typography variant="h5" color="success" gutterBottom>
              이 텃밭에선 뭘 키워야 하지?
            </Typography>
            <Typography variant="body2" color="textSecondary">
              일조량, 강수량 등의 데이터를 통해 텃밭에 어울리는 작물을 추천해드려요.
            </Typography>
          </Box>
        </Box>

        {/* 두 번째 섹션 */}
        <Box
          sx={{
            display: "flex",
            flexDirection: { xs: "column", md: "row-reverse" },
            alignItems: "center",
            justifyContent: "space-between",
            width: "100%",
            mb: 4,
          }}
        >
          {/* 이미지가 오른쪽, 텍스트가 왼쪽 */}
          <Box component="img" src="/landing/landing2.png" alt="텃밭 추천 페이지" sx={{ width: "100px", ml: { md: 2, xs: 0 }, mb: { xs: 2, md: 0 } }} />
          <Box>
            <Typography variant="h5" color="success" gutterBottom>
              나는 이거 키워보고 싶은데
            </Typography>
            <Typography variant="body2" color="textSecondary">
              원하는 작물에 알맞는 텃밭을 추천해드려요.
            </Typography>
          </Box>
        </Box>

        {/* 세 번째 섹션 */}
        <Box
          sx={{
            display: "flex",
            flexDirection: { xs: "column", md: "row" },
            alignItems: "center",
            justifyContent: "space-between",
            width: "100%",
            mb: 4,
          }}
        >
          {/* 이미지가 왼쪽, 텍스트가 오른쪽 */}
          <Box component="img" src="/landing/landing3.png" alt="Todo 아니면 작물상세페이지" sx={{ width: "100px", mr: { md: 2, xs: 0 }, mb: { xs: 2, md: 0 } }} />
          <Box>
            <Typography variant="h5" color="success" gutterBottom>
              어 내가 물을 언제 줬지?
            </Typography>
            <Typography variant="body2" color="textSecondary">
              식물마다 물 주는 시기도 다르고, 필요한 작업도 다릅니다. 새싹농부는 쉽게 키울 수 있도록 도와드려요.
            </Typography>
          </Box>
        </Box>

        {/* 네 번째 섹션 */}
        <Box
          sx={{
            display: "flex",
            flexDirection: { xs: "column", md: "row-reverse" },
            alignItems: "center",
            justifyContent: "space-between",
            width: "100%",
          }}
        >
          {/* 이미지가 오른쪽, 텍스트가 왼쪽 */}
          <Box component="img" src="/landing/landing4.png" alt="병해충 인식 페이지" sx={{ width: "100px", ml: { md: 2, xs: 0 }, mb: { xs: 2, md: 0 } }} />
          <Box>
            <Typography variant="h5" color="success" gutterBottom>
              어? 이상한게 생겼는데?
            </Typography>
            <Typography variant="body2" color="textSecondary">
              AI 병해충 판단을 통해 치료방법을 알아보세요.
            </Typography>
          </Box>
        </Box>
      </Box>

      {/* FAQ 섹션 */}
      <Box
        sx={{
          mt: 8,
          textAlign: "center",
        }}
      >
        <Typography variant="h6" gutterBottom>
          자주 묻는 질문
        </Typography>
        {/* FAQ 아코디언 컴포넌트 대신 버튼으로 대체 */}
        <Box>
          <Button fullWidth variant="outlined" sx={{ mb: 1 }}>
            새싹농부는 무엇인가요?
          </Button>
          <Button fullWidth variant="outlined" sx={{ mb: 1 }}>
            도시농부란 무엇인가요?
          </Button>
          <Button fullWidth variant="outlined">
            어떤 정보를 제공해주나요?
          </Button>
        </Box>
      </Box>
    </Container>
  );
};

export default HomePage;
