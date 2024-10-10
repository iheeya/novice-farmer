import { useState, useEffect } from 'react'
import veranda from '../../assets/img/farms/1.png'
import weekend from '../../assets/img/farms/2.png'
import individual from '../../assets/img/farms/3.png'
import school from '../../assets/img/farms/4.png'
import rooftop from '../../assets/img/farms/5.png'
import '../../styles/RegisterGarden/gardenSelect.css'
import { FaHeart } from "react-icons/fa";
// import GardenModal from './GardenModal'
import {getFarmSelect} from '../../services/AddGarden/AddGardenGet'
import GardenModal from './gardenModal'
import { useDispatch } from 'react-redux'
import {setFarmData, setPlaceIdData} from '../../store/AddFarm/store'


// farm/place api 사용해서 텃밭 리스트 가져오기
interface GardenLoadingProps{
    onLoading : () => void;
    onResponse: (data: any) => void;
}

interface FarmPlace {
    placeId: number; 
    placeName: string; 
    isService: boolean; 
    isFavorite : boolean;
}

function GardenSelect({onLoading, onResponse}:GardenLoadingProps) {
    const dispatch = useDispatch(); // 디스패치 함수 
    const [selectedPlace, setSelectedPlace] = useState<string | null>(null); // 선택된 장소를 저장할 상태
    const [isModalOpen, setIsModalOpen] = useState(false)  // 모달 열림 상태
    const [selectPlaceId, setSelectPalceId] = useState<number|null>(null) // 장소 id 저장
    const [farmPlace, setFarmPlaceData] = useState<FarmPlace[]>([]); // API에서 받아온 텃밭 리스트 상태
  

    const imageMapping: {[key:string]: string} = {
        1:veranda,
        2:weekend,
        3:individual,
        4: school,
        5: rooftop
    }

    // API 요청을 통해 farmPlace 데이터 받아오기
    useEffect(() => {
        const getFarmData = async() => {
            try{
                const data = await getFarmSelect();
                // console.log(data)
                setFarmPlaceData(data)
            } catch(e){
                console.log(e)
            }
        }

    
        getFarmData(); // API 요청 실행
    }, []);

    const handleImageClick = (placeName :string, placeId:number) => {
        // setSelectedPlace(place); // 선택한 장소 저장
        // console.log(`${placeName}`)
        // console.log(`${placeId}`)
        setSelectedPlace(placeName)
        dispatch(setFarmData(placeName))   //farm 데이터에 선택한 장소 이름 저장
        setSelectPalceId(placeId)
        dispatch(setPlaceIdData(placeId))
        setIsModalOpen(true)
    }

    const closeModal = () => {
        setIsModalOpen(false); // 모달 닫기
    };

    


    return(
        <div className='frame'>
            <div className='farm-instruction'>텃밭을 선택해주세요!</div>
            <div className='image-group'>
                {farmPlace.map((place: FarmPlace) => (
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
            {isModalOpen && <GardenModal  placeId={selectPlaceId} onClose={closeModal} onLoading={onLoading} onResponse={onResponse} />} {/* 모달 조건부 렌더링 */}
        </div>
    )
}

export default GardenSelect;