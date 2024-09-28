import { useState } from 'react'
import veranda from '../../assets/img/farms/1.png'
import weekend from '../../assets/img/farms/2.png'
import individual from '../../assets/img/farms/3.png'
import school from '../../assets/img/farms/4.png'
import rooftop from '../../assets/img/farms/5.png'
import farmPlants from '../../assets/dummydata/farmRecommend.json'
import '../../styles/RegisterGarden/gardenSelect.css'
import { FaHeart } from "react-icons/fa";
import { FaStar } from "react-icons/fa6";
import PlantFinalModal from './PlantFinalModal'
import { useDispatch, useSelector } from 'react-redux';
import { setPlantData } from '../../store/AddFarm/store'
import { RootState } from '../../store/AddFarm/store'

function RecommendLand() {

    const [selectedPlace, setSelectedPlace] = useState<string | null>(null); // 선택된 장소를 저장할 상태
    const [isModalOpen, setIsModalOpen] = useState(false)  // 모달 열림 상태
    const [selectPlaceId, setSelectPlaceId] = useState<number|null>(null) // 장소 id 저장
    const plantData = useSelector((state:RootState) => state.farmSelect.plant)

    const imageMapping: {[key:string]: string} = {
        1:veranda,
        2:weekend,
        3:individual,
        4: school,
        5: rooftop
    }

    const handleImageClick = (plantName :string, plantId:number) => {
        setSelectedPlace(plantName)
        setSelectPlaceId(plantId)
       
        setIsModalOpen(true)
    }

    const closeModal = () => {
        setIsModalOpen(false); // 모달 닫기
    };


    return(
         <div className='frame'>
            <div className='farm-instruction'>{plantData}를 키울 텃밭을 선택해주세요!</div>
            <div className='image-group'>
                {farmPlants.map(place => (
                    <div className={`image-container ${place.isService ? '': 'blur'}`} // 서비스하지 않는 텃밭은 흑백 처리
                     key={place.placeId}
                    onClick={place.isService ? () => handleImageClick(place.placeName, place.placeId) : undefined} // 클릭 이벤트 설정
                    >     
                     {place.isFavorite && <FaHeart className='heart-icon' />}
                     {/* 왼쪽 조건이 True일 때만 오른쪽에 있는 값을 반환 */}
                    {place.isRecommend && <FaStar className='star-icon'/>}
                    <img
                        src={imageMapping[place.placeId]}
                        alt={`${place.placeName} 이미지`}
                        className='image-size'
                    />
                    <div className='farm-name'>{place.placeName}</div>
                </div>
                ))}
            </div>
            {isModalOpen && <PlantFinalModal placeName={selectedPlace} placeId={selectPlaceId} onClose={closeModal}/>} 
        </div>
    )
}

export default RecommendLand;