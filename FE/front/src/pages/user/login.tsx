// login page

// Todo
// 1. 소셜 로그인 연결
// 2. 시큐리티 프론트 할게 있으면 하기
// 3. 로그인 시 토큰 저장, cookie 설정
import { handleLogin } from "../../services/user/userapi";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { TextField, Button, Box, ImageList, ImageListItem } from "@mui/material";

export default function Login() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  return (
    <Box
      sx={{
        // width: "80%",
        // minWidth: "3px",
        margin: "0 auto", // 좌우 중앙 정렬
        // paddingY:"2%",
        height: "100%",
        paddingX: "5%",
        backgroundColor: "white",
        // boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)",
        borderRadius: "20px",
        textAlign: "center",
        display: "flex", // Flexbox 사용
        flexDirection: "column", // Flex 방향을 세로로 설정
        justifyContent: "center", // 수직 중앙 정렬
        alignItems: "center", // 수평 중앙 정렬
      }}
    >
      <img src="/user/sampleLogo.png" alt="샘플로고" style={{ width: "40%" }} />
      <form
        onSubmit={(event) => {
          event.preventDefault();
          handleLogin({ email, password })
            .then(({ firstLogin }) => {
              if (firstLogin) {
                console.log("firstLogin");
                navigate("/user/survey");
              } else {
                console.log("not firstLogin");
                navigate("/");
              }
            })
            .catch((error) => {
              console.error("Login failed:", error);
            });
        }}
      >
        <TextField
          label="이메일"
          variant="outlined"
          fullWidth
          error={false}
          margin="normal"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          //   helperText={!isEmailValid ? "유효한 이메일이 아닙니다" : ""}
        />
        <TextField
          label="비밀번호"
          type="password"
          variant="outlined"
          fullWidth
          margin="normal"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <Button
          type="submit"
          variant="contained"
          color="success"
          fullWidth
          sx={{ marginTop: "1rem", marginBottom: "1rem", padding: "0.75rem", backgroundColor: "#84b366" }}
        >
          로그인
        </Button>
        <Button
          type="button"
          variant="contained"
          color="success"
          fullWidth
          onClick={() => navigate("/user/signUp")}
          sx={{ marginBottom: "1.5rem", padding: "0.75rem", backgroundColor: "#84b366" }}
        >
          회원가입하기
        </Button>
      </form>
      {/* 소셜 로그인 부분 */}
      <ImageList>
        <ImageListItem>
          <img src="/user/kakao_login_medium_narrow.png" alt="kakao_login" />
        </ImageListItem>
        <ImageListItem>
          <img
            src="/user/android_neutral_sq_SU@1x.png"
            alt="google_login"
            style={{
              borderRadius: "5px",
            }}
          />
        </ImageListItem>
      </ImageList>
    </Box>
  );
}
