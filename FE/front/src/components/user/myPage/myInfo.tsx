// 프로필 페이지에서 프로필 이미지 URL 을 안줌
// s3 사용법도 알아야함. URL 로 사진 받아와서 출력하고 등등

import { useEffect, useState } from "react";
import { getMyInfo } from "../../../services/user/myPageApi";
import { Box, Typography, Button, Avatar, Paper } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { GetImage } from "../../../services/getImage";

interface UserInfo {
  email: string;
  nickname: string;
  regDate: string;
  isFirstLogin: boolean;
  imagepath: string;
  gender: string;
  age: number;
  address: string;
  pushAllow: boolean;
}

export default function MyInfo() {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [imageURL, setImageURL] = useState<string | null>(null);
  const navigate = useNavigate();

  // API 호출
  useEffect(() => {
    getMyInfo()
      .then((res) => {
        setUserInfo(res);
        GetImage(res.imagepath)
        .then((url) => {
          setImageURL(url);
        })

      })
      .catch((err) => {
        console.error("Failed to fetch user info", err);
      });
  }, []);

  if (!userInfo) return <Typography>Loading...</Typography>;

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        padding: "20px",
        backgroundColor: "#f5f5f5",
        borderRadius: "20px",
        maxWidth: "400px",
        margin: "0 auto",
      }}
    >
      <Avatar
        alt={userInfo.nickname}
        src={imageURL || "n"}
        sx={{ width: 100, height: 100, marginBottom: "20px" }}
      />
      {/* 유저 정보 */}
      <Paper
        elevation={3}
        sx={{
          width: "100%",
          padding: "20px",
          borderRadius: "15px",
          marginBottom: "20px",
        }}
      >
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            marginY: "3%",
            paddingX: "3%",
          }}
        >
          <Typography sx={{ fontWeight: "bold" }}>닉네임</Typography>
          <Typography>{userInfo.nickname}</Typography>
        </Box>

        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            marginY: "3%",
            paddingX: "3%",
          }}
        >
          <Typography sx={{ fontWeight: "bold" }}>나이</Typography>
          <Typography>{userInfo.age}</Typography>
        </Box>

        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            marginY: "3%",
            paddingX: "3%",
          }}
        >
          <Typography sx={{ fontWeight: "bold" }}>Email</Typography>
          <Typography>{userInfo.email}</Typography>
        </Box>

        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            marginY: "3%",
            paddingX: "3%",
          }}
        >
          <Typography sx={{ fontWeight: "bold" }}>성별</Typography>
          <Typography>{userInfo.gender === "MALE" ? "남" : "여"}</Typography>
        </Box>

        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            marginY: "3%",
            paddingX: "3%",
          }}
        >
          <Typography sx={{ fontWeight: "bold" }}>사는 곳</Typography>
          <Typography>{userInfo.address}</Typography>
        </Box>
      </Paper>

      {/* 수정 버튼 */}
      <Button
        variant="contained"
        sx={{
          backgroundColor: "#5b8e55",
          color: "white",
          padding: "10px 30px",
          borderRadius: "20px",
        }}
        onClick={() => {
          navigate("/mypage/profile");
        }}
      >
        수정
      </Button>

    </Box>
  );
}
