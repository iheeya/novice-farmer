import '../../styles/RegisterGarden/gardenSelectLoading.css'
import loading from '../../assets/img/loading/loading.png'
import { useSelector } from 'react-redux';
import { RootState } from '../../store/AddFarm/store';

function PlanSelectLoading(){
    const plantData = useSelector((state:RootState) => state.farmSelect.plant)

    return(
        <div className="loading-instruction">
            <div>{plantData}를 키우기 좋은 </div>
            <div> 텃밭을 분석중이에요! </div>

            <img
                src={loading}
                className="loading-image-size"
            />
        </div>
    )
}

export default PlanSelectLoading;