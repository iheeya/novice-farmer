// 해야 할 것들
// 1. 이메일 중복 검사 추가
// 2. 닉네임 중복 검사 추가

// import api from "../../utils/axios";
import { handleSignup, isEmailDuplicate, isNickNameDuplicate } from "../../services/user/userApi";
import { useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  TextField,
  Button,
  Checkbox,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  FormControlLabel,
  Box,
} from "@mui/material";
import { validateEmail, validateNickname, validatePassword, passwordConfirm } from "../../utils/signUpValidate";

function SignUp() {
  // 행정구역 정보
  const Divisions: { [key: string]: string[] } = {
    서울특별시: [
      "종로구",
      "중구",
      "용산구",
      "성동구",
      "광진구",
      "동대문구",
      "중랑구",
      "성북구",
      "강북구",
      "도봉구",
      "노원구",
      "은평구",
      "서대문구",
      "마포구",
      "양천구",
      "강서구",
      "구로구",
      "금천구",
      "영등포구",
      "동작구",
      "관악구",
      "서초구",
      "강남구",
      "송파구",
      "강동구",
    ],
    부산광역시: [
      "중구",
      "서구",
      "동구",
      "영도구",
      "부산진구",
      "동래구",
      "남구",
      "북구",
      "해운대구",
      "사하구",
      "금정구",
      "강서구",
      "연제구",
      "수영구",
      "사상구",
    ],
    대구광역시: ["중구", "동구", "서구", "남구", "북구", "수성구", "달서구", "달성군"],
    인천광역시: ["중구", "동구", "미추홀구", "연수구", "남동구", "부평구", "계양구", "서구", "강화군", "옹진군"],
    광주광역시: ["동구", "서구", "남구", "북구", "광산구"],
    대전광역시: ["동구", "중구", "서구", "유성구", "대덕구"],
    울산광역시: ["중구", "남구", "동구", "북구", "울주군"],
    세종특별자치시: ["세종특별자치시"],
    경기도: [
      "수원시",
      "성남시",
      "의정부시",
      "안양시",
      "부천시",
      "광명시",
      "평택시",
      "동두천시",
      "안산시",
      "고양시",
      "과천시",
      "의왕시",
      "구리시",
      "남양주시",
      "오산시",
      "시흥시",
      "군포시",
      "하남시",
      "파주시",
      "이천시",
      "안성시",
      "김포시",
      "화성시",
      "광주시",
      "양주시",
      "포천시",
      "여주시",
      "양평군",
      "가평군",
      "연천군",
    ],
    강원도: [
      "춘천시",
      "원주시",
      "강릉시",
      "동해시",
      "태백시",
      "속초시",
      "삼척시",
      "홍천군",
      "횡성군",
      "영월군",
      "평창군",
      "정선군",
      "철원군",
      "화천군",
      "양구군",
      "인제군",
      "고성군",
      "양양군",
    ],
    충청북도: [
      "청주시",
      "충주시",
      "제천시",
      "보은군",
      "옥천군",
      "영동군",
      "증평군",
      "진천군",
      "괴산군",
      "음성군",
      "단양군",
    ],
    충청남도: [
      "천안시",
      "공주시",
      "보령시",
      "아산시",
      "서산시",
      "논산시",
      "계룡시",
      "당진시",
      "금산군",
      "부여군",
      "서천군",
      "청양군",
      "홍성군",
      "예산군",
      "태안군",
    ],
    전라북도: [
      "전주시",
      "군산시",
      "익산시",
      "정읍시",
      "남원시",
      "김제시",
      "완주군",
      "진안군",
      "무주군",
      "장수군",
      "임실군",
      "순창군",
      "고창군",
      "부안군",
    ],
    전라남도: [
      "목포시",
      "여수시",
      "순천시",
      "나주시",
      "광양시",
      "담양군",
      "곡성군",
      "구례군",
      "고흥군",
      "보성군",
      "화순군",
      "장흥군",
      "강진군",
      "해남군",
      "영암군",
      "무안군",
      "함평군",
      "영광군",
      "장성군",
      "완도군",
      "진도군",
      "신안군",
    ],
    경상북도: [
      "포항시",
      "경주시",
      "김천시",
      "안동시",
      "구미시",
      "영주시",
      "영천시",
      "상주시",
      "문경시",
      "경산시",
      "군위군",
      "의성군",
      "청송군",
      "영양군",
      "영덕군",
      "청도군",
      "고령군",
      "성주군",
      "칠곡군",
      "예천군",
      "봉화군",
      "울진군",
      "울릉군",
    ],
    경상남도: [
      "창원시",
      "진주시",
      "통영시",
      "사천시",
      "김해시",
      "밀양시",
      "거제시",
      "양산시",
      "의령군",
      "함안군",
      "창녕군",
      "고성군",
      "남해군",
      "하동군",
      "산청군",
      "함양군",
      "거창군",
      "합천군",
    ],
    제주특별자치도: ["제주시", "서귀포시"],
  };

  // 회원가입을 위한 상태 변수
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConf, setPasswordConf] = useState("");
  const [nickName, setNickName] = useState("");
  const [age, setAge] = useState("");
  const [gender, setGender] = useState("");
  const [selectedProvince, setSelectedProvince] = useState(""); // 도/특별시/광역시
  const [selectedCity, setSelectedCity] = useState(""); // 시/군/구
  const [pushAllow, setPushAllow] = useState(false); // 푸시 알림 동의 여부

  const isEmailValid = useMemo(() => validateEmail(email), [email]);
  const isPasswordValid = useMemo(() => validatePassword(password), [password]);
  const isPasswordConfirmed = useMemo(() => passwordConfirm({ password, passwordConf }), [password, passwordConf]);
  const isNicknameValid = useMemo(() => validateNickname(nickName), [nickName]);

  const navigate = useNavigate();

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
        boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)",
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
        onSubmit={async (event) => {
          event.preventDefault();
          const isDuplicate = await isEmailDuplicate(email);
          const isDuplicateNickName = await isNickNameDuplicate(nickName);
          if (!isDuplicate) {
            alert("이미 존재하는 이메일입니다");
            return;
          } else if (!isDuplicateNickName) {
            alert("이미 존재하는 닉네임입니다");
            return;
          } else {
            handleSignup({
              email,
              password,
              nickname: nickName,
              age: parseInt(age, 10),
              gender,
              address: `${selectedProvince} ${selectedCity}`,
              pushAllow,
            })
              .then((response) => {
                console.log("signup success");
                navigate("/user/login");
              })
              .catch((err) => {
                console.log("signup failed", err);
              });
          }
        }}
      >
        <TextField
          label="이메일"
          variant="outlined"
          fullWidth
          error={!isEmailValid}
          margin="normal"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          helperText={!isEmailValid ? "유효한 이메일이 아닙니다" : ""}
        />

        <TextField
          label="비밀번호"
          type="password"
          variant="outlined"
          fullWidth
          error={!isPasswordValid}
          margin="normal"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          helperText={!isPasswordValid ? "문자와 숫자를 포함하여 8자리 이상이여야 합니다" : ""}
          required
        />

        <TextField
          label="비밀번호 확인"
          type="password"
          variant="outlined"
          fullWidth
          margin="normal"
          value={passwordConf}
          error={!isPasswordConfirmed}
          helperText={!isPasswordConfirmed ? "비밀번호가 일치하지 않습니다" : ""}
          onChange={(e) => setPasswordConf(e.target.value)}
          required
        />

        <TextField
          label="별명"
          variant="outlined"
          fullWidth
          error={!isNicknameValid}
          margin="normal"
          value={nickName}
          onChange={(e) => setNickName(e.target.value)}
          helperText={!isNicknameValid ? "자음과 모음을 단독으로 사용할 수 없습니다" : ""}
          required
        />
        <TextField
          label="나이"
          type="number"
          variant="outlined"
          fullWidth
          margin="normal"
          value={age}
          onChange={(e) => {
            const value = e.target.value;

            // 빈 값 허용
            if (value === "") {
              setAge(value);
              return;
            }

            const numericValue = parseInt(value, 10);

            // 음수 및 최대값 제한
            if (numericValue >= 0 && numericValue <= 150) {
              setAge(value);
            }
          }}
          inputProps={{
            min: 0,
            max: 150,
          }}
          required
        />

        <FormControl fullWidth margin="normal" variant="outlined" required>
          <InputLabel id="gender-label">성별</InputLabel>
          <Select
            sx={{
              textAlign: "left",
            }}
            labelId="gender-label"
            value={gender}
            label="성별"
            onChange={(e) => setGender(e.target.value)}
          >
            <MenuItem value="MALE">남자</MenuItem>
            <MenuItem value="FEMALE">여자</MenuItem>
          </Select>
        </FormControl>

        {/* 도/특별시/광역시 선택 */}
        <FormControl fullWidth margin="normal" variant="outlined" required>
          <InputLabel id="province-label">도/특별시/광역시</InputLabel>
          <Select
            sx={{
              textAlign: "left",
            }}
            labelId="province-label"
            value={selectedProvince}
            onChange={(e) => {
              setSelectedProvince(e.target.value);
              setSelectedCity(""); // 도/특별시 선택 변경 시 시/군/구 초기화
            }}
            label="도/특별시/광역시"
          >
            {Object.keys(Divisions).map((province) => (
              <MenuItem key={province} value={province}>
                {province}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        {/* 시/군/구 선택 */}
        <FormControl fullWidth margin="normal" disabled={!selectedProvince} variant="outlined" required>
          <InputLabel id="city-label">시/군/구</InputLabel>
          <Select
            sx={{
              textAlign: "left",
            }}
            labelId="city-label"
            value={selectedCity}
            onChange={(e) => setSelectedCity(e.target.value)}
            label="시/군/구"
          >
            {selectedProvince &&
              Divisions[selectedProvince].map((city) => (
                <MenuItem key={city} value={city}>
                  {city}
                </MenuItem>
              ))}
          </Select>
        </FormControl>

        {/* 푸시 알림 동의 체크박스 */}
        <FormControlLabel
          control={<Checkbox checked={pushAllow} onChange={(e) => setPushAllow(e.target.checked)} color="primary" />}
          label="푸시 알림 동의"
        />

        <Button
          type="submit"
          variant="contained"
          color="success"
          fullWidth
          sx={{ marginTop: "1.5rem", marginBottom: "1.5rem", padding: "0.75rem", backgroundColor: "#5B8E55" }}
        >
          회원가입 하기
        </Button>
      </form>
    </Box>
  );
}

export default SignUp;
