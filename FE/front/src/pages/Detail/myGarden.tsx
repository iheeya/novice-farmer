import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../../styles/Detail/myGarden.module.css';
import FarmDeleteModal from '../../components/Detail/FarmDeleteModal';
import { getImageForPlantGrowthStep } from '../../utils/imageMapping'

interface Plant {
  plantId: number;
  plantName: string;
  myPlantId: number;
  myPlantName: string;
  myPlantGrowthStep: number;
  todoInfo: string;
  seedDate: string;
}

const MyGarden = () => {
  const { myPlaceId } = useParams();
  const navigate = useNavigate();

  const [isEditing, setIsEditing] = useState(false);
  const [nickname, setNickname] = useState('');
  const [tempNickname, setTempNickname] = useState('');
  const [weatherInfo, setWeatherInfo] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedPlant, setSelectedPlant] = useState<Plant | null>(null);

  const dummyData1 = {
    placeInfo: {
      placeId: 1,
      placeName: '베란다',
      myPlaceName: '우리집베란다',
      farmCount: 2,
      weather: '오늘 비가 올 예정입니다',
    },
    farms: [
      {
        plantId: 1,
        plantName: '토마토',
        myPlantId: 1,
        myPlantName: '토순이',
        myPlantGrowthStep: 2,
        todoInfo: '5일 후에 물을 줘야해요',
        seedDate: '2024-04-01',
      },
      {
        plantId: 2,
        plantName: '상추',
        myPlantId: 2,
        myPlantName: '상춘이',
        myPlantGrowthStep: 3,
        todoInfo: '5일 후에 비료를 줘야해요',
        seedDate: '2024-04-02',
      },
    ],
  };

  const dummyData2 = {
    placeInfo: {
      placeId: 2,
      placeName: '주말농장',
      myPlaceName: '구미농장',
      farmCount: 3,
      weather: '맑고 화창한 날씨입니다',
    },
    farms: [
      {
        plantId: 3,
        plantName: '고추',
        myPlantId: 3,
        myPlantName: '작고맵',
        myPlantGrowthStep: 1,
        todoInfo: '3일 후에 물을 줘야해요',
        seedDate: '2024-05-01',
      },
      {
        plantId: 4,
        plantName: '바질',
        myPlantId: 4,
        myPlantName: '바질이',
        myPlantGrowthStep: 2,
        todoInfo: '2일 후에 비료를 줘야해요',
        seedDate: '2024-06-02',
      },
      {
        plantId: 5,
        plantName: '딸기',
        myPlantId: 5,
        myPlantName: '딸기이',
        myPlantGrowthStep: 3,
        todoInfo: '내일 물을 줘야해요',
        seedDate: '2024-07-01',
      },
    ],
  };

  const dummyData = myPlaceId === '1' ? dummyData1 : dummyData2;

  useEffect(() => {
    setNickname(dummyData.placeInfo.myPlaceName);
    setTempNickname(dummyData.placeInfo.myPlaceName);
    setWeatherInfo(dummyData.placeInfo.weather);
  }, [myPlaceId]);

  const handleEditNickname = () => setIsEditing(true);
  const handleSaveNickname = () => {
    if (tempNickname.trim() === '') {
      alert('별명을 입력해주세요.');
      return;
    }
    setNickname(tempNickname);
    setIsEditing(false);
  };

  const handleDeletePlantClick = (plant: Plant) => {
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
  
  // 작물 클릭 시 작물 상세 페이지로 이동
  const handlePlantClick = (myPlantId: number) => {
    navigate(`/myGarden/${myPlaceId}/${myPlantId}`);
  };

  const handleDeleteWeather = () => {
    setWeatherInfo('');
  };

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
        <h1 className={styles.placeName}>{dummyData.placeInfo.placeName}</h1>
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
        {dummyData.farms.map((farm) => (
          <div
            key={farm.myPlantId}
            className={styles.plantBox}
            onClick={() => handlePlantClick(farm.myPlantId)}  // 클릭 시 상세 페이지로 이동
          >
            <img
              src={getImageForPlantGrowthStep(farm.plantName, farm.myPlantGrowthStep)}  // 이미지 경로 매핑 함수 적용
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
                e.stopPropagation();  // 클릭 이벤트 전파 방지
                handleDeletePlantClick(farm);
              }}  // 삭제 모달 열기
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
          placeName={dummyData.placeInfo.myPlaceName}
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
