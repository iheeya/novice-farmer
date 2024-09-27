import { yellow } from "@mui/material/colors";
import { useNavigate } from 'react-router-dom';

// 임시 커뮤니티 페이지 (라우팅을 위해 만듬)

function Community(){

    const navigate = useNavigate();
  
    const handleClick = (id: number) => {
      // 글의 id를 URL로 전달하며 상세 페이지로 이동
      navigate(`/community/${id}/detail`);
    };

    const communityList = [{
        id: 6, 
        title: 'ㅎㅎ'
    }]


    return(        
    <div style={{ backgroundColor: 'yellow' }}>
        {communityList.map((item) => (
        <div key={item.id} onClick={() => handleClick(item.id)}>
            {item.title}
        </div>
        ))}
    </div>
    )
}

export default Community;




