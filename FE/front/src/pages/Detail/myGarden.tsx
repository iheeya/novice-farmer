import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../../styles/Detail/myGarden.module.css'; // 스타일링 파일 경로

const MyGarden = () => {
  const { myPlaceId } = useParams(); // URL에서 myPlaceId 가져오기
  const navigate = useNavigate();

  // 텃밭 별명을 수정 가능하게 하기 위한 상태
  const [isEditing, setIsEditing] = useState(false);
  const [nickname, setNickname] = useState(''); // 텃밭 별명 초기값 비워둠
  const [tempNickname, setTempNickname] = useState('');
  const [weatherInfo, setWeatherInfo] = useState('');

  // 더미 데이터 (myPlaceId에 따라 다른 데이터 설정)
  const dummyData1 = {
    placeInfo: {
      placeId: 1,
      placeName: '베란다',
      myPlaceName: '우리집베란다',
      farmCount: 2,
      weather: '오늘 비가 올 예정입니다'
    },
    farms: [
      {
        plantId: 1,
        plantName: '토마토',
        myPlantId: 1,
        myPlantName: '토순이',
        myPlantGrowthStep: 2,
        imagePath: 'https://i.ibb.co/7KJVPPh/Lettuce3.png',
        todoInfo: '5일 후에 물을 줘야해요',
        seedDate: '2024-04-01'
      },
      {
        plantId: 2,
        plantName: '상추',
        myPlantId: 2,
        myPlantName: '상춘이',
        myPlantGrowthStep: 3,
        imagePath: 'https://i.ibb.co/7KJVPPh/Lettuce3.png',
        todoInfo: '5일 후에 비료를 줘야해요',
        seedDate: '2024-04-02'
      }
    ]
  };

  const dummyData2 = {
    placeInfo: {
      placeId: 2,
      placeName: '주말농장',
      myPlaceName: '구미농장',
      farmCount: 3,
      weather: '맑고 화창한 날씨입니다'
    },
    farms: [
      {
        plantId: 3,
        plantName: '고추',
        myPlantId: 3,
        myPlantName: '작고맵',
        myPlantGrowthStep: 1,
        imagePath: 'https://i.ibb.co/7KJVPPh/Lettuce3.png',
        todoInfo: '3일 후에 물을 줘야해요',
        seedDate: '2024-05-01'
      },
      {
        plantId: 4,
        plantName: '바질',
        myPlantId: 4,
        myPlantName: '바질이',
        myPlantGrowthStep: 2,
        imagePath: 'https://i.ibb.co/7KJVPPh/Lettuce3.png',
        todoInfo: '2일 후에 비료를 줘야해요',
        seedDate: '2024-06-02'
      },
      {
        plantId: 5,
        plantName: '딸기',
        myPlantId: 5,
        myPlantName: '딸기이',
        myPlantGrowthStep: 3,
        imagePath: 'https://i.ibb.co/7KJVPPh/Lettuce3.png',
        todoInfo: '내일 물을 줘야해요',
        seedDate: '2024-07-01'
      }
    ]
  };

  // myPlaceId에 따라 더미 데이터 선택
  const dummyData = myPlaceId === '1' ? dummyData1 : dummyData2;

  // 초기 별명 및 날씨 설정
  useEffect(() => {
    setNickname(dummyData.placeInfo.myPlaceName);
    setTempNickname(dummyData.placeInfo.myPlaceName);
    setWeatherInfo(dummyData.placeInfo.weather);
  }, []);

  // 별명 수정 시작
  const handleEditNickname = () => setIsEditing(true);

  // 별명 수정 완료
  const handleSaveNickname = () => {
    setNickname(tempNickname);
    setIsEditing(false);
  };

  // 작물 삭제
  const handleDeletePlant = (myPlantId: number) => {
    const confirmDelete = window.confirm('정말로 이 작물을 삭제하시겠습니까?');
    if (confirmDelete) {
      console.log(`작물 ID: ${myPlantId} 삭제 완료`);
    }
  };

  // 작물 등록 페이지로 이동
  const handleAddPlantClick = () => {
    navigate(`/myGarden/${myPlaceId}/register/plant`);
  };

  // 뒤로 가기
  const handleBackClick = () => navigate(-1); // 이전 페이지로 이동

  // 날씨 삭제
  const handleDeleteWeather = () => {
    setWeatherInfo('');
  };

  // 작물 상세 페이지로 이동
  const handlePlantClick = (myPlantId: number) => {
    navigate(`/myGarden/${myPlaceId}/${myPlantId}`);
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
                alert("이름은 10자 이하로 입력해주세요.");
              }
            }}
            className={styles.nicknameInput}
            // 엔터 키를 눌렀을 때 저장
            onKeyDown={(e) => {
              if (e.key === "Enter") {
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
          <div key={farm.myPlantId} className={styles.plantBox} onClick={() => handlePlantClick(farm.myPlantId)}>
            <img src={farm.imagePath} alt={farm.myPlantName} className={styles.plantImage} />
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
                handleDeletePlant(farm.myPlantId);
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
    </div>
  );
};

export default MyGarden;
