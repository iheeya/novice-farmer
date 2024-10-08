import React, { useState } from "react";
import { Box, Tab, Tabs } from "@mui/material";
import { Outlet, useNavigate, useLocation } from "react-router-dom";
import backIcon from "../../assets/icons/Back.png";

export default function InfoHome() {
  const [value, setValue] = useState("1");
  const navigate = useNavigate();
  const location = useLocation();

  // URL 변경에 따라 탭 상태를 설정
  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
    // console.log(newValue);
    if (newValue === "1") {
      navigate("/info/place");
    } else {
      navigate("/info/plant");
    }
  };

  return (
    <Box
      sx={{
        width: "90%", // 화면 전체 너비
        margin: "10% auto",
        padding: "3% 0",
        textAlign: "center",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <img
  src={backIcon}
  alt="뒤로가기"
  onClick={() => navigate(-1)}
  style={{
    position: "absolute",  // 좌측 상단에 고정
    top: "2vh",            // 화면 높이의 2%만큼 여백
    left: "2vw",           // 화면 너비의 2%만큼 여백
    width: "3rem",         // 3rem = 약 48px (기본 폰트 크기 기준 16px)
    height: "3rem",        // 3rem = 약 48px
    cursor: "pointer",     // 마우스 커서 포인터 모양으로 변경
  }}
/>

      {/* Tabs 컴포넌트 */}
      <Tabs
        value={location.pathname.includes("/plant") ? "2" : "1"}
        onChange={handleChange}
        centered
        TabIndicatorProps={{
          style: {
            backgroundColor: "#5B8E55", // 선택된 탭 아래의 indicator 색상
          },
        }}
        textColor="inherit" // 기본 탭 텍스트 색상
        sx={{
          "& .Mui-selected": {
            color: "#5B8E55", // 선택된 탭의 텍스트 색상
          },
          margin: "10%", // 탭과 아래 내용 간격
        }}
      >
        <Tab value="1" label="텃밭 정보" sx={{ fontSize: "1.5rem" }} />
        <Tab value="2" label="작물 정보" sx={{ fontSize: "1.5rem" }} />
      </Tabs>

      {/* 하위 라우트 내용 표시 (Outlet 사용) */}
      <Box sx={{ width: "90%" }}>
        <Outlet />
      </Box>
    </Box>
  );
}
