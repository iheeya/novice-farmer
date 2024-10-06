import {useState} from 'react'
import CommunitySearchHeader from "../../components/CommunitySearch/CommunitySearchHeader"
import CommunityTag from "../../components/CommunitySearch/CommunityTag"
import CommunitySearchInput from '../../components/CommunitySearch/CommunitySearchInput'

function CommunitySearch(){
    const [isInput, setIsInput] = useState<boolean>(false)  // 검색창을 띄우는지 아닌지

    const handleSearchClick = () => {
        setIsInput(true)
    }

    const handleCancelClick = () => {
        setIsInput(false); // 취소 버튼 누르면 검색창 닫고 태그 페이지로 이동
      };

    return(
        <>
            <CommunitySearchHeader onCancelClick={handleCancelClick} onTextFieldClick={handleSearchClick}/>
            {isInput ? <CommunitySearchInput/>:<CommunityTag/>}
            
        </>
    )
}

export default CommunitySearch