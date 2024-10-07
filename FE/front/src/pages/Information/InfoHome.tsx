import React, { useState } from "react";
import { Box, Tab, Tabs } from "@mui/material";
import { Outlet, useNavigate, useLocation } from "react-router-dom";
import InfoPlace from "../../components/Information/InfoPlace";
import InfoCrops from "../../components/Information/InfoCrops";

export default function InfoHome() {
  const [value, setValue] = useState("one");
  const navigate = useNavigate();
  const location = useLocation();

  // URL 변경에 따라 탭 상태를 설정
  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
    if (newValue === "one") {
      navigate("/info/place");
    } else {
      navigate("/info/crops");
    }
  };

  return (
    <Box
      sx={{
        width: "90%", // 화면 전체 너비
        margin: "auto",
        padding: "2% 0",
        textAlign: "center",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      {/* Tabs 컴포넌트 */}
      <Tabs
        value={location.pathname.includes("/crops") ? "two" : "one"}
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
        <Tab value="one" label="텃밭 정보" sx={{ fontSize: "1rem" }} />
        <Tab value="two" label="작물 정보" sx={{ fontSize: "1rem" }} />
      </Tabs>

      {/* 하위 라우트 내용 표시 (Outlet 사용) */}
      <Box sx={{ width: "90%" }}>
        <Outlet />
      </Box>
    </Box>
  );
}
