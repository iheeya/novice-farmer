import veranda from '../../assets/farms/1.png'
import weekend from '../../assets/farms/2.png'
import individual from '../../assets/farms/3.png'
import school from '../../assets/farms/4.png'
import rooftop from '../../assets/farms/5.png'
import farmPlace from '../../assets/dummydata/farmPlace.json'
import '../../styles/RegisterGarden/gardenSelect.css'
import { FaHeart } from "react-icons/fa";


// farm/place api 사용해서 텃밭 리스트 가져오기

function GardenSelect() {
    const imageMapping: {[key:string]: string} = {
        1:veranda,
        2:weekend,
        3:individual,
        4: school,
        5: rooftop
    }

    return(
        <div className='frame'>
            <div className='farm-instruction'>텃밭을 선택해주세요!</div>
            <div className='image-group'>
                {farmPlace.map(place => (
                    <div className={`image-container ${place.isService ? '': 'blur'}`} key={place.placeId}>      {/* 서비스하지 않는 텃밭은 흑백 처리 */}
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
        </div>
    )
}

export default GardenSelect;