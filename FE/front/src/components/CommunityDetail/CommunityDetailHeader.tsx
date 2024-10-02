import '../../styles/CommunityDetail/CommunityDetailHeader.css'
import { useNavigate } from 'react-router-dom';
import { FaSearch } from "react-icons/fa";

function CommunityDetailHeader(){
    const navigate = useNavigate();

    const handleMyPage = () => {
        navigate('/mypage')
    }

    const handleSearchPage = () => {
        navigate('/community/search')
    }


    return(
        <div className='community-header-container'>
            <FaSearch className='search' onClick={handleSearchPage}/>   {/* 누르면 검색 페이지로 이동*/}
            <div className='my-profile' onClick={handleMyPage}>MY</div> {/* 누르면 마이페이지로 이동*/}
        </div> 
    )
}


export default CommunityDetailHeader;