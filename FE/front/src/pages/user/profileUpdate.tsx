import { useEffect, useState, useRef } from "react";
import {
  Box,
  Typography,
  CircularProgress,
  Button,
  Avatar,
  Paper,
  TextField,
  Checkbox,
  FormControlLabel,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  IconButton,
} from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import { getMyInfo, postMyInfo, postProfileImage } from "../../services/user/myPageApi";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2"; // SweetAlert2 임포트
import { GetImage } from "../../services/getImage";

interface UserInfo {
  email: string;
  nickname: string;
  regDate: string;
  isFirstLogin: boolean;
  gender: string;
  imagepath: string;
  age: number;
  address: string;
  pushAllow: boolean;
}

export default function ProfileUpdate() {
  const [userInfo, setUserInfo] = useState<UserInfo | null>(null);
  const [selectedProvince, setSelectedProvince] = useState("");
  const [selectedCity, setSelectedCity] = useState("");
  const [imagePreview, setImagePreview] = useState<string | null>(null);
  const fileInputRef = useRef<HTMLInputElement | null>(null);
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const navigate = useNavigate();

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
  // API 호출하여 사용자 정보 가져오기
  useEffect(() => {
    getMyInfo()
      .then((res) => {
        setUserInfo(res);
        GetImage(res.imagepath).then((url) => {
          setImagePreview(url);
        });
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

  // 파일 입력 필드 클릭
  const handleAddButtonClick = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  // 파일이 선택될 때 호출되는 함수
  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      const imageUrl = URL.createObjectURL(file);
      setImagePreview(imageUrl);
      setSelectedFile(file);
    }
  };

  // 정보 수정 버튼 클릭 시 호출되는 함수
  const handleUpdate = async () => {
    try {
      // 1. 프로필 이미지가 변경된 경우 서버에 업로드
      if (selectedFile) {
        await postProfileImage(selectedFile);
      }

      // 2. 사용자 정보 업데이트
      if (userInfo) {
        await postMyInfo(userInfo);
      }

      // 3. 성공 알림 표시
      Swal.fire({
        title: "수정 완료!",
        text: "정보가 성공적으로 수정되었습니다.",
        icon: "success",
        confirmButtonText: "확인",
      }).then(() => {
        // SweetAlert2 확인 버튼을 클릭했을 때 프로필 페이지로 이동
        navigate("/myPage");
      });
    } catch (error) {
      console.error("업데이트 중 오류 발생:", error);
      Swal.fire({
        title: "수정 실패!",
        text: "정보 수정 중 오류가 발생했습니다.",
        icon: "error",
        confirmButtonText: "확인",
      });
    }
  };

  return (
    <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", padding: "20px" }}>
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
        <Box sx={{ position: "relative", display: "inline-block" }}>
          <Avatar
            alt={userInfo.nickname}
            src={imagePreview || ""}
            sx={{ width: 100, height: 100, marginBottom: "20px" }}
          />
          <IconButton
            sx={{
              position: "absolute",
              bottom: 15,
              right: 0,
              backgroundColor: "white",
              borderRadius: "50%",
              padding: "5px",
            }}
            onClick={handleAddButtonClick}
          >
            <AddCircleIcon sx={{ color: "#5b8e55" }} />
          </IconButton>
        </Box>

        <input
          type="file"
          accept="image/*"
          ref={fileInputRef}
          style={{ display: "none" }}
          onChange={handleFileChange}
        />

        <TextField label="이메일" value={userInfo.email} fullWidth margin="normal" disabled />
        <TextField
          label="별명"
          value={userInfo.nickname}
          fullWidth
          margin="normal"
          onChange={(e) => setUserInfo({ ...userInfo, nickname: e.target.value })}
        />
        <TextField
          label="나이"
          type="number"
          value={userInfo.age}
          fullWidth
          margin="normal"
          onChange={(e) => setUserInfo({ ...userInfo, age: parseInt(e.target.value) })}
          required
        />

        <FormSelect
          label="도/특별시/광역시"
          value={selectedProvince}
          options={Object.keys(Divisions).map((province) => ({ value: province, label: province }))}
          onChange={(value) => {
            setSelectedProvince(value);
            setSelectedCity("");
          }}
        />

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

        <FormControlLabel
          control={
            <Checkbox
              checked={userInfo.pushAllow}
              onChange={(e) => setUserInfo({ ...userInfo, pushAllow: e.target.checked })}
              sx={{ "&.Mui-checked": { color: "#5b8e55" } }}
            />
          }
          label="푸시 알림 동의"
        />
        <Button variant="contained" onClick={handleUpdate} sx={{ marginTop: "20px", backgroundColor: "#5b8e55" }}>
          정보 수정
        </Button>
      </Paper>
    </Box>
  );
}

// FormSelect 컴포넌트 정의 (기존 코드 유지)

// FormSelect 컴포넌트 정의
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
