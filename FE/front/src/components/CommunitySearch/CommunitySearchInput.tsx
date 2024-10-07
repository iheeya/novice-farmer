import { useEffect, useState } from "react";
import '../../styles/CommunitySearch/CommunitySearchInput.css'
import { useNavigate } from "react-router-dom";

// 쿠키에서 값을 가져오는 함수
const getCookie = (name: string): string => {
  return document.cookie.split('; ').reduce((r, v) => {
    const parts = v.split('=');
    return parts[0] === name ? decodeURIComponent(parts[1]) : r;
  }, '');
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

  return (
    <div>
      {/* 최근 검색 기록 표시 */}
      {searchHistory.length > 0 && (
        <div className="recent-record-box">
          <div className="recent-search">최근 검색어</div>
       
            {searchHistory.map((item, index) => (
              <div key={index} className="recent-word" onClick={() => handleClick(item)}>{item}</div>
            ))}
        
        </div>
      )}
    </div>
  );
}

export default CommunitySearchInput;
