import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../../styles/Main/FavoritesInfo.module.css';
import { getImageForCrop, getImageForLocation } from '../../utils/imageMapping';  // 이미지 매핑 함수 가져오기

interface FavoritesInfoProps {
  data: {
    isUsable: boolean;
    favoritePlants: {
      plantId: number;
      plantName: string;
    }[];
    favoritePlaces: {
      placeId: number;
      placeName: string;
    }[];
  };
}

const FavoritesInfo: React.FC<FavoritesInfoProps> = ({ data }) => {
  const navigate = useNavigate();

  const handlePlantClick = (plantName: string) => {
    navigate(`/info/plant/${plantName}`);
  };

  const handleFarmClick = (placeName: string) => {
    let routeName = placeName;

    if (placeName === '베란다') {
      routeName = '베란다 텃밭';
    } else if (placeName === '개인텃밭') {
      routeName = '옥상텃밭';
    }

    navigate(`/info/place/type/${encodeURIComponent(routeName)}`);
  };

  return (
    <div className={styles.favoritesContainer}>
      {/* 나의 관심 작물 섹션 */}
      <div className={styles.sectionHeader}>
        <h2 className={styles.interestHeader}>나의 관심 작물</h2>
        <img src={getImageForCrop('Default')} alt="기본 작물 이미지" className={styles.headerImage} />
      </div>

      {/* 해시태그 리스트 */}
      <div className={styles.hashTags}>
        {data.favoritePlants.map((plant) => (
          <span key={plant.plantId} className={styles.hashTag}>#{plant.plantName}</span>
        ))}
      </div>

      {/* 작물 정보 리스트 */}
      <ul className={styles.favoritesList}>
        {data.favoritePlants.map((plant) => (
          <li key={plant.plantId} 
            className={styles.favoritesItemWithImage} 
            onClick={() => handlePlantClick(plant.plantName)}>
            <p className={styles.plantText}>{plant.plantName} 재배방법?</p>
            <img src={getImageForCrop(plant.plantName)} alt={plant.plantName} className={styles.plantImage} />
          </li>
        ))}
      </ul>

      <hr />

      {/* 나의 관심 텃밭 섹션 */}
      <div className={styles.sectionHeader}>
        <h2 className={styles.interestHeader}>나의 관심 텃밭</h2>
        <img src={getImageForLocation(0)} alt="기본 텃밭 이미지" className={styles.headerImage} />
      </div>

      {/* 해시태그 리스트 */}
      <div className={styles.hashTags}>
        {data.favoritePlaces.map((place) => (
          <span key={place.placeId} className={styles.hashTag}>#{place.placeName}</span>
        ))}
      </div>

      {/* 텃밭 정보 리스트 */}
      <ul className={styles.favoritesList}>
        {data.favoritePlaces.map((place) => (
          <li key={place.placeId} 
            className={styles.favoritesItemNoImage}
            onClick={() => handleFarmClick(place.placeName)}
          >
            <p className={styles.placeText}>{place.placeName}에서 키울만한 작물</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default FavoritesInfo;
