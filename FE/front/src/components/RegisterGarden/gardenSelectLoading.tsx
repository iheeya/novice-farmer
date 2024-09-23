import '../../styles/RegisterGarden/gardenSelectLoading.css'
import loading from '../../assets/img/loading/loading.png'

function GardenSelectLoading(){
    return(
        <div className="loading-instruction">
            <div>베란다에서 키우기 좋은 </div>
            <div> 작물을 분석중이에요! </div>

            <img
                src={loading}
                className="loading-image-size"
            />
        </div>
    )
}

export default GardenSelectLoading;