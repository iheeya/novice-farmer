import { useState, useEffect } from 'react'
import tomato from '../../assets/img/plants/1.png'
import pepper from '../../assets/img/plants/2.png'
import lettuce from '../../assets/img/plants/3.png'
import perilla from '../../assets/img/plants/4.png'
import cabbage from '../../assets/img/plants/5.png'
import '../../styles/RegisterGarden/gardenSelect.css'
import { FaHeart } from "react-icons/fa";
import PlantModal from './PlantModal'
import { useDispatch } from 'react-redux'
import {setFarmData, setPlantData, setPlantIdData} from '../../store/AddFarm/store'
import { getPlantData } from '../../services/AddGarden/AddGardenGet'

// farm/place api 사용해서 텃밭 리스트 가져오기
interface GardenLoadingProps{
    onLoading : () => void;
    onResponse: (data: any) => void;
}

interface Plant {
    plantId: number; 
    plantName: string; 
    isService: boolean; 
    isFavorite : boolean;
}

function PlantSelect({onLoading, onResponse}: GardenLoadingProps) {
    const dispatch = useDispatch(); // 디스패치 함수 
    const [selectedPlant, setSelectedPlant] = useState<string | null>(null); // 선택된 장소를 저장할 상태
    const [isModalOpen, setIsModalOpen] = useState(false)  // 모달 열림 상태
    const [selectPlantId, setSelectPlantId] = useState<number|null>(null) // 장소 id 저장
    const [farmPlant, setfarmPlant] = useState<any[]>([]);

    const imageMapping: {[key:string]: string} = {
        1:tomato,
        2:pepper,
        3:lettuce,
        4: perilla,
        5: cabbage
    }

    const handleImageClick = (plantName :string, plantId:number) => {
        // setSelectedPlace(place); // 선택한 장소 저장
        // console.log(`${placeName}`)
        // console.log(`${placeId}`)
        setSelectedPlant(plantName)
        dispatch(setPlantData(plantName))   //farm 데이터에 선택한 장소 이름 저장
        setSelectPlantId(plantId)
        dispatch(setPlantIdData(plantId))
        setIsModalOpen(true)
    }

    const closeModal = () => {
        setIsModalOpen(false); // 모달 닫기
    };

    useEffect(() => {
        const PlantData = async() => {
            try{
                const data = await getPlantData()
                console.log(data)
                setfarmPlant(data)
            } catch(e) {
                console.log(e)
            }
        }

        PlantData();
    }, [])



    return(
        <div className='frame'>
            <div className='farm-instruction'>작물을 선택해주세요!</div>
            <div className='image-group'>
                {farmPlant.map((plant:Plant) => (
                    <div className={`image-container ${plant.isService ? '': 'blur'}`} // 서비스하지 않는 텃밭은 흑백 처리
                     key={plant.plantId}
                    onClick={plant.isService ? () => handleImageClick(plant.plantName, plant.plantId) : undefined} // 클릭 이벤트 설정
                    >     
                     {plant.isFavorite && <FaHeart className='heart-icon' />}
                     {/* 왼쪽 조건이 True일 때만 오른쪽에 있는 값을 반환 */}
                    <img
                        src={imageMapping[plant.plantId]}
                        alt={`${plant.plantName} 이미지`}
                        className='image-size'
                    />
                    <div className='farm-name'>{plant.plantName}</div>
                </div>
                ))}
            </div>
            {isModalOpen && <PlantModal  plantId={selectPlantId} onClose={closeModal} onLoading={onLoading} onResponse={onResponse}/>} 
        </div>
    )
}

export default PlantSelect;