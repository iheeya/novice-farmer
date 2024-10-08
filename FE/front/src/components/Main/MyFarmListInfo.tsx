import React from 'react';
import styles from '../../styles/Main/MyFarmListInfo.module.css';
import { getImageForLocation } from '../../utils/imageMapping';  // 이미지 매핑 함수 가져오기
import { useNavigate } from 'react-router-dom';

interface MyFarmListInfoProps {
  data: {
    farms: {
      placeId: number;
      placeName: string;
      myPlaceId: number;
      myPlaceName: string;
    }[];
  };
}

const MyFarmListInfo: React.FC<MyFarmListInfoProps> = ({ data }) => {
  const navigate = useNavigate();

  const AddPlantClick = () => {
    navigate("/register/garden"); 
  };

  // 텃밭 상세 페이지로 이동하는 함수
  const handleFarmClick = (myPlaceId: number) => {
    navigate(`/myGarden/${myPlaceId}`); 
  };

  return (
    <div className={styles.farmListContainer}>
      <h2 className={styles.farmListTitle}>나의 텃밭을 관리해보세요!</h2>
      {data.farms.length > 0 ? (
        <ul className={styles.farmList}>
          {data.farms.map((farm) => (
            <li 
              key={farm.myPlaceId} 
              className={styles.farmItem} 
              onClick={() => handleFarmClick(farm.myPlaceId)}  
            >
              {/* getImageForLocation 함수로 placeId에 맞는 이미지 경로를 동적으로 불러옴 */}
              <img src={getImageForLocation(farm.placeId)} alt={farm.myPlaceName} className={styles.farmImage} />
              <div className={styles.farmInfo}>
                <h3>{farm.placeName}을 둘러보세요</h3>
                <p>나의 텃밭 상세정보 보기</p>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p className={styles.noFarmText}>추가된 농장이 없습니다. <br /> 새로운 농장을 추가해보세요!</p>
      )}
      <div className={styles.addFarmContainer}>
        <p className={styles.addFarmText}>텃밭을 추가하세요</p>
        <p className={styles.addFarmSubtitle}>나의 작물을 등록해서 관리해요</p>
        <button className={styles.addButton} onClick={AddPlantClick}>
          <img src={require('../../assets/icons/Plus.png')} alt="Add farm" />
        </button>
      </div>
    </div>
  );
};

export default MyFarmListInfo;
