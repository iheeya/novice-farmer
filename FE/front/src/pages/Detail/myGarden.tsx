import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../../styles/Detail/myGarden.module.css';
import FarmDeleteModal from '../../components/Detail/FarmDeleteModal';
import { getImageForPlantGrowthStep } from '../../utils/imageMapping';
import { getFarmDetailPageInfo, FarmDetailPageInfoProps, Farm } from '../../services/FarmDetail/farmDetailPageApi'; 
import { updatePlaceName } from '../../services/FarmDetail/farmDetailPageApi';


const MyGarden: React.FC = () => {
  const { myPlaceId } = useParams<{ myPlaceId: string }>();
  const navigate = useNavigate();

  const [placeName, setPlaceName] = useState('');
  const [nickname, setNickname] = useState('');
  const [weatherInfo, setWeatherInfo] = useState('');
  const [farms, setFarms] = useState<FarmDetailPageInfoProps['farms']>([]);
  const [isEditing, setIsEditing] = useState(false);
  const [tempNickname, setTempNickname] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedPlant, setSelectedPlant] = useState<null | Farm>(null); 
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (myPlaceId) {
      // 실제 API 데이터 가져오기
      getFarmDetailPageInfo(Number(myPlaceId))
        .then((data) => {
          setPlaceName(data.placeInfo.placeName);
          setNickname(data.placeInfo.myPlaceName);
          setTempNickname(data.placeInfo.myPlaceName);
          setWeatherInfo(data.placeInfo.weather);
          setFarms(data.farms);
          setLoading(false);
        })
        .catch((error) => {
          console.error('Failed to fetch farm detail data', error);
          setLoading(false);
        });
    }
  }, [myPlaceId]);

  const handleEditNickname = () => setIsEditing(true);

  const handleSaveNickname = () => {
    if (tempNickname.trim() === '') {
      alert('별명을 입력해주세요.');
      return;
    }

    // 이름 수정 API 호출
    updatePlaceName(Number(myPlaceId), tempNickname)
      .then(() => {
        setNickname(tempNickname);
        setIsEditing(false);
      })
      .catch((error) => {
        console.error('Failed to update place name', error);
        alert('장소 이름을 수정하는 데 실패했습니다.');
      });
  };

  const handleDeletePlantClick = (plant: Farm) => {
    setSelectedPlant(plant);
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setSelectedPlant(null);
  };

  const handleDeletePlant = () => {
    if (selectedPlant) {
      console.log(`Deleting plant with ID: ${selectedPlant.myPlantId}`);
      setIsModalOpen(false);
    }
  };

  const handleAddPlantClick = () => {
    navigate(`/myGarden/${myPlaceId}/register/plant`);
  };

  const handleBackClick = () => navigate(-1);

  const handlePlantClick = (myPlantId: number) => {
    navigate(`/myGarden/${myPlaceId}/${myPlantId}`);
  };

  const handleDeleteWeather = () => {
    setWeatherInfo('');
  };

  if (loading) {
    return <div>로딩 중...</div>;
  }

  return (
    <div className={styles.gardenContainer}>
      {/* Header */}
      <div className={styles.header}>
        <img
          src={require('../../assets/icons/Back.png')}
          alt="Back"
          className={styles.backButton}
          onClick={handleBackClick}
        />
        <h1 className={styles.placeName}>{placeName}</h1>
      </div>

      {/* 수정 가능한 MyPlaceName */}
      <div className={styles.nicknameContainer}>
        {isEditing ? (
          <input
            value={tempNickname}
            onChange={(e) => {
              if (e.target.value.length <= 10) {
                setTempNickname(e.target.value);
              } else {
                alert('이름은 10자 이하로 입력해주세요.');
              }
            }}
            className={styles.nicknameInput}
            onKeyDown={(e) => {
              if (e.key === 'Enter') {
                handleSaveNickname();
              }
            }}
          />
        ) : (
          <h2 className={styles.nickname}>{nickname}</h2>
        )}
        <img
          src={require(`../../assets/icons/${isEditing ? 'Check.png' : 'Write.png'}`)}
          alt={isEditing ? 'Save' : 'Edit'}
          className={styles.editIcon}
          onClick={isEditing ? handleSaveNickname : handleEditNickname}
        />
      </div>

      {/* Garden Type & Weather */}
      <div className={styles.gardenInfo}>
        {weatherInfo && (
          <div className={styles.weatherBox}>
            <img src={require('../../assets/img/weathers/Rain.png')} alt="Weather Icon" className={styles.weatherImage} />
            <p>{weatherInfo}</p>
            <img
              src={require('../../assets/icons/Delete.png')}
              alt="Delete"
              className={styles.deleteIcon}
              onClick={handleDeleteWeather}
            />
          </div>
        )}
      </div>

      {/* Plant List */}
      <div className={styles.plantList}>
        {farms.map((farm) => (
          <div
            key={farm.myPlantId}
            className={styles.plantBox}
            onClick={() => handlePlantClick(farm.myPlantId)}
          >
            <img
              src={getImageForPlantGrowthStep(farm.plantName, farm.myPlantGrowthStep)}
              alt={farm.myPlantName}
              className={styles.plantImage}
            />
            <div className={styles.plantDetails}>
              <h3>{farm.plantName}</h3>
              <p>{farm.todoInfo}</p>
            </div>
            <p className={styles.seedDate}>파종일: {farm.seedDate}</p>
            <img
              src={require('../../assets/icons/Delete.png')}
              alt="Delete"
              className={styles.deletePlantIcon}
              onClick={(e) => {
                e.stopPropagation();
                handleDeletePlantClick(farm);
              }}
            />
          </div>
        ))}
      </div>

      {/* Add Plant Box */}
      <div className={styles.addPlantBox}>
        <p className={styles.addPlantText}>텃밭에 다른 작물들을 등록해보세요</p>
        <div className={styles.addPlantButtonWrapper} onClick={handleAddPlantClick}>
          <img src={require('../../assets/icons/Plus.png')} alt="Add Plant" className={styles.addPlantIcon} />
        </div>
      </div>

      {/* Farm Delete Modal */}
      {isModalOpen && selectedPlant && (
        <FarmDeleteModal
          placeName={nickname}
          plantName={selectedPlant.myPlantName}
          onClose={handleModalClose}
          onDelete={handleDeletePlant}
          isOpen={isModalOpen}
        />
      )}
    </div>
  );
};

export default MyGarden;
