import veranda from '../../assets/farms/1.png'
import weekend from '../../assets/farms/2.png'
import individual from '../../assets/farms/3.png'
import school from '../../assets/farms/4.png'
import rooftop from '../../assets/farms/5.png'
import farmPlace from '../../assets/dummydata/farmPlace.json'
import '../../styles/RegisterGarden/gardenSelect.css'


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
        <>
            <div className='farm-instruction'>텃밭을 선택해주세요!</div>
            <div className='image-group'>
                {farmPlace.map(place => (
                    <img
                        key={place.placeId}
                        src={imageMapping[place.placeId]}
                        alt={`${place.placeName} 이미지`}
                        className='image-size'
                    />
                ))}
            </div>
        </>
    )
}

export default GardenSelect;