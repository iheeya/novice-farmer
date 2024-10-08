import React, { useState } from "react";
import { Box, Tab, Tabs } from "@mui/material";
import { Outlet, useNavigate, useLocation } from "react-router-dom";

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
