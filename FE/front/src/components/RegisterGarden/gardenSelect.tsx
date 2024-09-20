import { useState } from 'react'
import veranda from '../../assets/images/farms/1.png'
import weekend from '../../assets/images/farms/2.png'
import individual from '../../assets/images/farms/3.png'
import school from '../../assets/images/farms/4.png'
import rooftop from '../../assets/images/farms/5.png'
import farmPlace from '../../assets/dummydata/farmPlace.json'
import '../../styles/RegisterGarden/gardenSelect.css'
import { FaHeart } from "react-icons/fa";
import GardenModal from './gardenModal'


// farm/place api 사용해서 텃밭 리스트 가져오기

function GardenSelect() {
    const [selectedPlace, setSelectedPlace] = useState<string | null>(null); // 선택된 장소를 저장할 상태
    const [isModalOpen, setIsModalOpen] = useState(false)  // 모달 열림 상태
    const [selectPlaceId, setSelectPalceId] = useState<number|null>(null) // 장소 id 저장

    const imageMapping: {[key:string]: string} = {
        1:veranda,
        2:weekend,
        3:individual,
        4: school,
        5: rooftop
    }

    const handleImageClick = (placeName :string, placeId:number) => {
        // setSelectedPlace(place); // 선택한 장소 저장
        console.log(`${placeName}`)
        console.log(`${placeId}`)
        setSelectedPlace(placeName)
        setSelectPalceId(placeId)
        setIsModalOpen(true)
    }

    const closeModal = () => {
        setIsModalOpen(false); // 모달 닫기
    };

    return(
        <div className='frame'>
            <div className='farm-instruction'>텃밭을 선택해주세요!</div>
            <div className='image-group'>
                {farmPlace.map(place => (
                    <div className={`image-container ${place.isService ? '': 'blur'}`} // 서비스하지 않는 텃밭은 흑백 처리
                     key={place.placeId}
                    onClick={place.isService ? () => handleImageClick(place.placeName, place.placeId) : undefined} // 클릭 이벤트 설정
                    >     
                     {place.isFavorite && <FaHeart className='heart-icon' />}
                     {/* 왼쪽 조건이 True일 때만 오른쪽에 있는 값을 반환 */}
                    <img
                        src={imageMapping[place.placeId]}
                        alt={`${place.placeName} 이미지`}
                        className='image-size'
                    />
                    <div className='farm-name'>{place.placeName}</div>
                </div>
                ))}
            </div>
            {isModalOpen && <GardenModal placeName={selectedPlace} placeId={selectPlaceId} onClose={closeModal}/>} {/* 모달 조건부 렌더링 */}
        </div>
    )
}

export default GardenSelect;