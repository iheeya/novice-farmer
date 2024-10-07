import {useState} from 'react'
import CommunitySearchHeader from "../../components/CommunitySearch/CommunitySearchHeader"
import CommunityTag from "../../components/CommunitySearch/CommunityTag"
import CommunitySearchInput from '../../components/CommunitySearch/CommunitySearchInput'

function CommunitySearch(){
    const [isInput, setIsInput] = useState<boolean>(false)  // 검색창을 띄우는지 아닌지
    const [isSearchActive, setIsSearchActive] = useState<boolean>(false); // 최근 검색어 클릭 여부

    const handleSearchClick = () => {
        setIsInput(true)
        setIsSearchActive(true); // 검색창 열기
    }

    const handleCancelClick = () => {
        setIsInput(false); // 취소 버튼 누르면 검색창 닫고 태그 페이지로 이동
        setIsSearchActive(false); // 검색 활성화 상태 초
    };

    return(
        <>
            <CommunitySearchHeader onCancelClick={handleCancelClick}
             onTextFieldClick={handleSearchClick}
             isSearchActive={isSearchActive}
             />
            {isInput ? <CommunitySearchInput/>:<CommunityTag/>}
            
        </>
    )
}

export default CommunitySearch