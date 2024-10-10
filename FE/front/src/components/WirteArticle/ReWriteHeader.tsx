import Back from '../../assets/icons/Back.png'
import { useNavigate } from 'react-router-dom';

function ReWriteHeader(){
    const navigate = useNavigate();

    const handleBack = () => {
        navigate(-1)
    }
    
    return (
        <div style={{ display: "flex", alignItems: "center", marginTop: "10%", marginLeft:'5%' }}>
        <div onClick={handleBack} style={{ marginRight: '5%' }}> {/* 이미지와 글 사이 간격 추가 */}
          <img src={Back} alt="뒤로가기 버튼" style={{width: '40%'}}/>
        </div>
        <div style={{ color: "#5B8E55", fontSize: "1.3rem", marginLeft:'3%' }}>수정하기</div>
      </div>
    )
}

export default ReWriteHeader;