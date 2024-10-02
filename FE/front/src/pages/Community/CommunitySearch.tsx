import {useState} from 'react'
import CommunitySearchHeader from "../../components/CommunitySearch/CommunitySearchHeader"
import CommunityTag from "../../components/CommunitySearch/CommunityTag"
import CommunitySearchInput from '../../components/CommunitySearch/CommunitySearchInput'

function CommunitySearch(){
    const [isInput, setIsInput] = useState<boolean>(false)  // 검색창을 띄우는지 아닌지

    const handleSearchClick = () => {
        setIsInput(true)
    }

    return(
        <>
            <CommunitySearchHeader onTextFieldClick={handleSearchClick}/>
            {isInput ? <CommunitySearchInput/>:<CommunityTag/>}
            
        </>
    )
}

export default CommunitySearch