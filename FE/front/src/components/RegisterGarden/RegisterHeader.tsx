import Back from '../../assets/icons/Back.png'
import { useNavigate, useParams } from 'react-router-dom';

function RegisterHeader(){
    const navigate = useNavigate();

    const handleBack = () => {
        navigate(-1)
    }

    return(
        <div  onClick={handleBack}>
            <img src={Back} alt='뒤로가기' style={{width:'13%', marginTop:'5%', marginLeft:'5%'}}/>
        </div>
    )
}

export default RegisterHeader;