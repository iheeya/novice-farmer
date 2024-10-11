import "../../styles/CommunitySearch/CommunitySearchHeader.css";
import { useNavigate } from "react-router-dom";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import { useState, useEffect } from "react";
import Back from '../../assets/icons/Back.png'

// 쿠키에 값을 저장하는 함수
const setCookie = (name: string, value: string, days: number) => {
  const expires = new Date(Date.now() + days * 864e5).toUTCString();
  document.cookie = `${name}=${encodeURIComponent(
    value
  )}; expires=${expires}; path=/`;
};

// 쿠키에서 값을 가져오는 함수
const getCookie = (name: string): string => {
  return document.cookie.split("; ").reduce((r, v) => {
    const parts = v.split("=");
    return parts[0] === name ? decodeURIComponent(parts[1]) : r;
  }, "");
};

interface PropFunc {
  onTextFieldClick: () => void;
  onCancelClick: () => void;
  isSearchActive: boolean;
  onSearchComplete?: () => void;
}

function CommunitySearchHeader({ onTextFieldClick, onCancelClick, isSearchActive, onSearchComplete }: PropFunc) {
  const [searchTerm, setSearchTerm] = useState<string>(""); // 검색어 입력 상태
  const [searchHistory, setSearchHistory] = useState<string[]>([]); // 검색 기록 상태
  const navigate = useNavigate();

  const handleMyPage = () => {
    navigate("/community/myArticles");
  };

  // 쿠키에서 검색 기록을 불러오는 함수
  useEffect(() => {
    const savedHistory = getCookie("searchHistory");
    if (savedHistory) {
      setSearchHistory(JSON.parse(savedHistory));
    }
  }, []);

  const handleTextFieldClick = () => {
    onTextFieldClick();
  };

  const handleCancelClick = () => {
    onCancelClick();
  };

  // 엔터 키 입력 처리
  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      // 엔터 키를 누르면 검색어로 URL을 업데이트하고 초기화
      if (searchTerm) {
        // 검색어를 업데이트 (중복 제거)
        const updatedHistory = Array.from(
          new Set([searchTerm, ...searchHistory])
        ).slice(0, 5); // 최근 5개 검색어만 저장
        setSearchHistory(updatedHistory);
        setCookie("searchHistory", JSON.stringify(updatedHistory), 7); // 쿠키에 저장

        navigate(`/community/search/${encodeURIComponent(searchTerm)}`); // 검색어를 URL에 추가
        setSearchTerm(""); // 입력창 초기화
        if (onSearchComplete) {
          onSearchComplete(); // Only call if the function is provided
        }
      }
    }
  };

  
  const handleBack = () => {
    navigate(-1)
}

  return (
    <>
      <div className="community-search-container">
            <div className='search-back-box' onClick={handleBack}><img className='back' src={Back} alt='뒤로가기 버튼'/></div>
            <div className='search-my-profile' onClick={handleMyPage}>MY</div> {/* 누르면 마이페이지로 이동*/}
        {/* 누르면 마이페이지로 이동*/}
      </div>

      <div style={{ display: "flex", alignItems: "center", marginTop: "5%" }}>
        <TextField
          id="outlined-basic"
          variant="outlined"
          type="search"
          placeholder="검색"
          value={searchTerm} // 검색어 상태 연결
          onChange={(e) => setSearchTerm(e.target.value)} // 상태 업데이트
          onKeyPress={handleKeyPress} // 엔터 키 이벤트 처리
          sx={{
            marginLeft: "5%",
            marginTop: "5%",
            width: isSearchActive ? "80%" : "90%", // width 조절
            "& .MuiOutlinedInput-root": {
              height: "2.7rem", // 여기서 높이를 설정
            },
            backgroundColor: "#F8FAF8",
          }}
          onClick={handleTextFieldClick}
        />

        {isSearchActive && (
          <Button
            onClick={handleCancelClick}
            sx={{
              color: "#5B8E55",
              marginTop: "5%",
              marginRight: "5%",
              marginLeft: "2%",
              height: "2.5rem",
              fontSize: "1.15rem",
            }}
          >
            취소
          </Button>
        )}
      </div>
    </>
  );
}

export default CommunitySearchHeader;
