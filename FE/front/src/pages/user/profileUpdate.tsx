import { useEffect, useState } from "react";
import {
  Box,
  Typography,
  CircularProgress,
  Button,
  Avatar,
  Paper,
  TextField,
  Checkbox,
  FormControl,
  FormControlLabel,
  InputLabel,
  MenuItem,
  Select,
} from "@mui/material";
import { getMyInfo, postMyInfo } from "../../services/user/myPageApi";
import { useNavigate } from "react-router-dom";

interface UserInfo {
  email: string;
  nickname: string;
  regDate: string;
  isFirstLogin: boolean;
  gender: string;
  age: number;
  address: string;
  pushAllow: boolean;
}

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

export default function ProfileUpdate() {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [selectedProvince, setSelectedProvince] = useState(""); // 도/특별시/광역시
  const [selectedCity, setSelectedCity] = useState(""); // 시/군/구
  const navigate = useNavigate();

  // API 호출
  useEffect(() => {
    getMyInfo()
      .then((res) => {
        setUserInfo(res);
        const addressParts = res.address.split(" ");
        if (addressParts.length > 1) {
          setSelectedProvince(addressParts[0]);
          setSelectedCity(addressParts[1]);
        }
      })
      .catch((err) => {
        console.error("Failed to fetch user info", err);
      });
  }, []);

  if (!userInfo) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
        <CircularProgress />
      </Box>
    );
  }

  const handleUpdate = async () => {
    // 프로필 업데이트 로직
    console.log(userInfo);
    await postMyInfo(userInfo);
    // navigate("/myPage");
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        padding: "20px",
      }}
    >
      <Typography variant="h4" gutterBottom>
        내 정보
      </Typography>
      <Paper
        sx={{
          padding: "20px",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          width: "100%",
          maxWidth: "400px",
        }}
      >
        <Avatar
          alt="User Avatar"
          src={"/static/images/avatar/1.jpg"}
          sx={{ width: 100, height: 100, marginBottom: "20px" }}
        />
        <TextField label="이메일" value={userInfo.email} fullWidth margin="normal" disabled />
        <TextField
          label="별명"
          value={userInfo.nickname}
          fullWidth
          margin="normal"
          onChange={(e) => setUserInfo({ ...userInfo, nickname: e.target.value })}
        />

        {/* 나이 입력 필드 추가 */}
        <TextField
          label="나이"
          type="number"
          value={userInfo.age}
          fullWidth
          margin="normal"
          onChange={(e) => {
            const value = e.target.value;
            if (value === "") {
                setUserInfo({ ...userInfo, age: parseInt(e.target.value, 10) })
              return;
            }
            const numericValue = parseInt(value, 10);

            // 음수 및 최대값 제한
            if (numericValue >= 0 && numericValue <= 150) {
                setUserInfo({ ...userInfo, age: parseInt(e.target.value, 10) })
            }
          }}
          inputProps={{ min: 0, max: 150 }} // 유효한 나이 범위 제한
          required
        />

        {/* 성별 선택 */}
        {/* <FormSelect
          label="성별"
          value={userInfo.gender}
          options={[
            { value: "MALE", label: "남자" },
            { value: "FEMALE", label: "여자" },
          ]}
          onChange={(value) => setUserInfo({ ...userInfo, gender: value })}
        /> */}

        {/* 도/특별시/광역시 선택 */}
        <FormSelect
          label="도/특별시/광역시"
          value={selectedProvince}
          options={Object.keys(Divisions).map((province) => ({ value: province, label: province }))}
          onChange={(value) => {
            setSelectedProvince(value);
            setSelectedCity(""); // 도/특별시 변경 시 시/군/구 초기화
          }}
        />

        {/* 시/군/구 선택 */}
        <FormSelect
          label="시/군/구"
          value={selectedCity}
          options={Divisions[selectedProvince]?.map((city) => ({ value: city, label: city })) || []}
          onChange={(value) => {
            setSelectedCity(value);
            setUserInfo({ ...userInfo, address: `${selectedProvince} ${value}` });
          }}
          disabled={!selectedProvince}
        />

        {/* 푸시 알림 동의 */}
        <FormControlLabel
          control={
            <Checkbox
              checked={userInfo.pushAllow}
              onChange={(e) => setUserInfo({ ...userInfo, pushAllow: e.target.checked })}
              color="primary"
            />
          }
          label="푸시 알림 동의"
        />
        <Button variant="contained" color="primary" onClick={handleUpdate} sx={{ marginTop: "20px" }}>
          Update Profile
        </Button>
      </Paper>
    </Box>
  );
}

// FormSelect 컴포넌트
interface FormSelectProps {
  label: string;
  value: string;
  options: { value: string; label: string }[];
  onChange: (value: string) => void;
  disabled?: boolean;
}

function FormSelect({ label, value, options, onChange, disabled = false }: FormSelectProps) {
  return (
    <FormControl fullWidth margin="normal" disabled={disabled}>
      <InputLabel>{label}</InputLabel>
      <Select value={value} onChange={(e) => onChange(e.target.value)} label={label}>
        {options.map((option) => (
          <MenuItem key={option.value} value={option.value}>
            {option.label}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
}
