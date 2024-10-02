import { Box, Tab, Tabs, Button } from "@mui/material";
import { useState } from "react";
import MyInfo from "../../components/user/myPage/myInfo";
import MyHistory from "../../components/user/myPage/myHistory";
import MyPrefer from "../../components/user/myPage/myPrefer";
import { useNavigate } from "react-router-dom";

export default function MyPage() {
  const [value, setValue] = useState("one");
  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };
  const navigate = useNavigate()
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
        value={value}
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
        <Tab value="one" label="내 정보" sx={{ fontSize: "1rem" }} />
        <Tab value="two" label="History" sx={{ fontSize: "1rem" }} />
        <Tab value="three" label="관심항목" sx={{ fontSize: "1rem" }} />
      </Tabs>

      {/* 탭 내용 */}
      <Box sx={{ width: "90%" }}>
        {value === "one" && <MyInfo />}
        {value === "two" && <MyHistory />}
        {value === "three" && <MyPrefer />}
      </Box>
      {/* 로그아웃 버튼 */}
      <Button variant="outlined"  sx={{ width: "60%", marginTop:"4vh" }} onClick={()=>{
        sessionStorage.removeItem("accessToken")
        sessionStorage.removeItem("refreshToken")
        navigate("/introduce")
      }
      }>
        로그아웃
      </Button>
    </Box>
  );
}
