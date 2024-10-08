import { Container, Box, Button, Typography, Accordion, AccordionSummary, AccordionDetails } from "@mui/material";
import { useNavigate } from "react-router-dom";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const HomePage = () => {
  const navigate = useNavigate();
  return (
    <Container maxWidth="lg">
      {/* 최상단 헤더 및 소개 섹션 */}
      <Box
        sx={{
          textAlign: "center",
          py: 4,
          marginTop: 10,
        }}
      >
        <Typography
          variant="h1"
          gutterBottom
          sx={{
            fontWeight: "bold",
            fontSize: { xs: "2.5rem", md: "4rem" },
            lineHeight: { xs: "1.2", md: "1.5" },
            textAlign: { xs: "center", md: "center" },
          }}
        >
          새싹 농부가
        </Typography>
        <Typography
          variant="h1"
          gutterBottom
          sx={{
            fontWeight: "bold",
            fontSize: { xs: "2.5rem", md: "4rem" },
            lineHeight: { xs: "1.2", md: "1.5" },
            textAlign: { xs: "center", md: "center" },
          }}
        >
          모두 알려드려요
        </Typography>
        <Typography
          variant="body1"
          color="textSecondary"
          sx={{
            textAlign: { xs: "center", md: "inherit" },
            marginBottom: 4,
            fontWeight: "bold",
            fontSize: { xs: "1rem", md: "1.25rem" },
          }}
        >
          새싹 농부는 개인 맞춤 작물 추천 및 일과 관리를 통해 처음 시작하는 사람도 손쉽게 작물을 키울 수 있게 해줍니다.
        </Typography>
        <Button
          variant="contained"
          color="success"
          size="large"
          onClick={() => {
            navigate("/user/login");
          }}
        >
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
          maxWidth: "800px", // 너비 제한
          margin: "0 auto", // 중앙 정렬
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
          <Box
            component="img"
            src="/landing/landing1.png"
            alt="작물 추천 페이지"
            sx={{ width: "100px", mr: { md: 2, xs: 0 }, mb: { xs: 2, md: 0 } }}
          />
          <Box>
            <Typography
              variant="h5"
              color="success"
              gutterBottom
              sx={{ textAlign: { xs: "center", md: "left" }, fontWeight: "bold" }}
            >
              이 텃밭에선 뭘 키워야 하지?
            </Typography>
            <Typography
              variant="body2"
              color="textSecondary"
              sx={{ textAlign: { xs: "center", md: "left" }, fontWeight: "bold" }}
            >
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
          <Box
            component="img"
            src="/landing/landing2.png"
            alt="텃밭 추천 페이지"
            sx={{ width: "100px", ml: { md: 2, xs: 0 }, mb: { xs: 2, md: 0 } }}
          />
          <Box>
            <Typography
              variant="h5"
              color="success"
              gutterBottom
              sx={{ textAlign: { xs: "center", md: "left" }, fontWeight: "bold" }}
            >
              나는 이거 키워보고 싶은데
            </Typography>
            <Typography
              variant="body2"
              color="textSecondary"
              sx={{ textAlign: { xs: "center", md: "left" }, fontWeight: "bold" }}
            >
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
          <Box
            component="img"
            src="/landing/landing3.png"
            alt="작물 관리 페이지"
            sx={{ width: "100px", mr: { md: 2, xs: 0 }, mb: { xs: 2, md: 0 } }}
          />
          <Box>
            <Typography
              variant="h5"
              color="success"
              gutterBottom
              sx={{ textAlign: { xs: "center", md: "right" }, fontWeight: "bold" }}
            >
              어? 내가 물을 언제 줬었지?
            </Typography>
            <Typography
              variant="body2"
              color="textSecondary"
              sx={{ textAlign: { xs: "center", md: "right" }, fontWeight: "bold" }}
            >
              식물마다 물 주는 시기도 다르고, 필요한 작업도 다릅니다.
            </Typography>
            <Typography
              variant="body2"
              color="textSecondary"
              sx={{ textAlign: { xs: "center", md: "right" }, fontWeight: "bold" }}
            >
              새싹농부는 쉽게 키울 수 있도록 도와드려요.
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
          <Box
            component="img"
            src="/landing/landing4.png"
            alt="병해충 인식 페이지"
            sx={{ width: "100px", ml: { md: 2, xs: 0 }, mb: { xs: 2, md: 0 } }}
          />
          <Box>
            <Typography
              variant="h5"
              color="success"
              gutterBottom
              sx={{ textAlign: { xs: "center", md: "left" }, fontWeight: "bold" }}
            >
              어? 이상한게 생겼는데?
            </Typography>
            <Typography
              variant="body2"
              color="textSecondary"
              sx={{ textAlign: { xs: "center", md: "right" }, fontWeight: "bold" }}
            >
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
    maxWidth: "800px",
    margin: "0 auto",
    px: 2,
  }}
>
  <Typography variant="h6" gutterBottom sx={{ fontWeight: "bold" }}>
    자주 묻는 질문
  </Typography>

  {/* 각 Accordion의 모서리 스타일링 수정 */}
  <Accordion
    sx={{
      borderRadius: "16px", // 전체 아코디언을 둥글게
      marginBottom: 2, // 아코디언 사이 간격
      boxShadow: "0 2px 8px rgba(0, 0, 0, 0.1)",
      "&:first-of-type": { borderTopLeftRadius: "16px", borderTopRightRadius: "16px" }, // 첫 번째 아코디언의 상단 모서리를 둥글게
      "&:last-of-type": { borderBottomLeftRadius: "16px", borderBottomRightRadius: "16px" }, // 마지막 아코디언의 하단 모서리를 둥글게
      "&:before": { display: "none" }, // Accordion의 기본 border 제거
      overflow: "hidden", // 둥근 모서리가 잘리도록 설정
    }}
  >
    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
      <Typography sx={{ fontWeight: "bold" }}>새싹농부는 무엇인가요?</Typography>
    </AccordionSummary>
    <AccordionDetails>
      <Typography>
        새싹 농부는 개인 맞춤 작물 추천 및 일과 관리를 통해 처음 시작하는 사람도 손쉽게 작물을 키울 수 있도록 도와주는 서비스입니다.
      </Typography>
    </AccordionDetails>
  </Accordion>

  <Accordion
    sx={{
      borderRadius: "16px",
      marginBottom: 2,
      boxShadow: "0 2px 8px rgba(0, 0, 0, 0.1)",
      "&:before": { display: "none" },
      overflow: "hidden",
    }}
  >
    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
      <Typography sx={{ fontWeight: "bold" }}>도시농부란 무엇인가요?</Typography>
    </AccordionSummary>
    <AccordionDetails>
      <Typography>
        도시농부는 도시 내에서 작은 공간을 활용하여 작물을 재배하고, 농업 활동을 통해 자연을 가까이 하며 삶의 질을 높이는 사람들을 의미합니다.
      </Typography>
    </AccordionDetails>
  </Accordion>

  <Accordion
    sx={{
      borderRadius: "16px",
      marginBottom: 2,
      boxShadow: "0 2px 8px rgba(0, 0, 0, 0.1)",
      "&:last-of-type": { borderBottomLeftRadius: "16px", borderBottomRightRadius: "16px" }, // 마지막 아코디언의 하단 모서리를 둥글게
      "&:before": { display: "none" },
      overflow: "hidden",
    }}
  >
    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
      <Typography sx={{ fontWeight: "bold" }}>어떤 정보를 제공해주나요?</Typography>
    </AccordionSummary>
    <AccordionDetails>
      <Typography>
        새싹농부는 일조량, 강수량 등의 데이터를 통해 텃밭에 어울리는 작물을 추천하고, 식물 관리 방법과 병해충 대처법에 대한 정보를 제공합니다.
      </Typography>
    </AccordionDetails>
  </Accordion>
</Box>

    </Container>
  );
};

export default HomePage;
