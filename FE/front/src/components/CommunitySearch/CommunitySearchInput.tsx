import { useEffect, useState } from "react";
import '../../styles/CommunitySearch/CommunitySearchInput.css'
import { useNavigate } from "react-router-dom";
import ClearIcon from '@mui/icons-material/Clear';


// 쿠키에서 값을 가져오는 함수
const getCookie = (name: string): string => {
  return document.cookie.split('; ').reduce((r, v) => {
    const parts = v.split('=');
    return parts[0] === name ? decodeURIComponent(parts[1]) : r;
  }, '');
};


// 쿠키에 값을 저장하는 함수
const setCookie = (name: string, value: string, days: number) => {
  const expires = new Date(Date.now() + days * 864e5).toUTCString();
  document.cookie = `${name}=${encodeURIComponent(value)}; expires=${expires}; path=/`;
};



interface CommunitySearchInputProps {
  onSearchComplete?: () => void; // 부모로부터 전달받은 검색 완료 콜백 함수
}

function CommunitySearchInput({onSearchComplete}:CommunitySearchInputProps) {
  const [searchHistory, setSearchHistory] = useState<string[]>([]); // 검색 기록 상태
  const navigate = useNavigate();

  // 쿠키에서 검색 기록을 불러오는 함수
  useEffect(() => {
    const savedHistory = getCookie("searchHistory");
    if (savedHistory) {
      setSearchHistory(JSON.parse(savedHistory));
    }
  }, []);

  const handleClick = (search: string) => {
    navigate(`/community/search/${search}`)
    if (onSearchComplete) {
      onSearchComplete(); // Only call if the function is provided
    }
  };

  // 검색어 삭제 핸들러
  const handleDelete = (itemToDelete: string) => {
    const updatedHistory = searchHistory.filter(item => item !== itemToDelete);
    setSearchHistory(updatedHistory); // 상태 업데이트

    // 쿠키 업데이트
    setCookie("searchHistory", JSON.stringify(updatedHistory), 7); // 7일간 유지
  };



  // 전체 삭제 핸들러
  const handleDeleteAll = () => {
    setSearchHistory([]); // 상태 업데이트
    setCookie("searchHistory", JSON.stringify([]), 7); // 쿠키 업데이트
  };
  

  return (
    <div>
      {/* 최근 검색 기록 표시 */}
      {searchHistory.length > 0 && (
        <div className="recent-record-box">
          <div className="recent-search-container">
            <div className="recent-search">최근 검색어</div>
            <div className="recent-search-delete"   onClick={handleDeleteAll}>전체 삭제</div>
          </div>
            {searchHistory.map((item, index) => (
              <div className="recent-word-container">
                <div key={index} className="recent-word" onClick={() => handleClick(item)}>{item}</div>
                <ClearIcon sx={{color:'#5B8E55', marginTop:'3%'}} onClick={() => handleDelete(item)}/>
              </div>
           ))}
        
        </div>
      )}
    </div>
  );
}

export default CommunitySearchInput;
