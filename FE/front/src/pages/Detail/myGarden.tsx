import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import styles from '../../styles/Detail/myGarden.module.css';
import Swal from 'sweetalert2';  
import { getImageForPlantGrowthStep } from '../../utils/imageMapping';
import { getFarmDetailPageInfo, FarmDetailPageInfoProps, Farm } from '../../services/FarmDetail/farmDetailPageApi'; 
import { updatePlaceName } from '../../services/FarmDetail/farmDetailPageApi';
import { selectGardenPost } from '../../services/AddGarden/AddGardenPost';
import { useDispatch } from 'react-redux';
import { setLocationData, setFarmData } from '../../store/AddFarm/store';


interface Address {
  sido: string;    // 시/도
  sigugun: string; // 시/군/구
  bname1: string;  // 법정동/리 이름1
  bname2: string;  // 법정동/리 이름2
  bunji: string;   // 번지
  jibun: string;   // 지번 주소
  zonecode: string; // 우편번호
}

const MyGarden: React.FC = () => {
  const { myPlaceId } = useParams<{ myPlaceId: string }>();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [placeName, setPlaceName] = useState('');
  const [nickname, setNickname] = useState('');
  const [weatherInfo, setWeatherInfo] = useState('');
  const [farms, setFarms] = useState<FarmDetailPageInfoProps['farms']>([]);
  const [isEditing, setIsEditing] = useState(false);
  const [tempNickname, setTempNickname] = useState('');
  const [loading, setLoading] = useState(true);
  const [placeIdInfo, setPlaceIdInfo]  = useState<number>();
  const [addressInfo, setAddressInfo] = useState<Address>();

  useEffect(() => {
    if (myPlaceId) {
      getFarmDetailPageInfo(Number(myPlaceId))
        .then((data) => {
          setPlaceName(data.placeInfo.placeName);
          setNickname(data.placeInfo.myPlaceName);
          setTempNickname(data.placeInfo.myPlaceName);
          setWeatherInfo(data.placeInfo.weather);
          setFarms(data.farms);
          setAddressInfo(data.placeInfo.address)
          setPlaceIdInfo(data.placeInfo.placeId)
          setLoading(false);

          dispatch(setFarmData(data.placeInfo.placeName))
        })
        .catch((error) => {
          console.error('Failed to fetch farm detail data', error);
          setLoading(false);
        });
    }
  }, [myPlaceId]);

  // 닉네임 수정 로직
  const handleEditNickname = () => setIsEditing(true);

  const handleSaveNickname = () => {
    if (tempNickname.trim() === '') {
      Swal.fire('이름 입력 필요', '별명을 입력해주세요.', 'error');
      return;
    }

    // 이름 수정 API 호출
    updatePlaceName(Number(myPlaceId), tempNickname)
      .then(() => {
        setNickname(tempNickname);
        setIsEditing(false);
        Swal.fire('수정 완료', '별명이 성공적으로 수정되었습니다.', 'success');
      })
      .catch((error) => {
        console.error('Failed to update place name', error);
        Swal.fire('수정 실패', '별명을 수정하는 데 실패했습니다.', 'error');
      });
  };

  // SweetAlert2 삭제 모달
  const handleDeletePlantClick = (plant: Farm) => {
    Swal.fire({
      html: '<strong>정말 이 작물을 <br> 삭제하시겠습니까?</strong>',
      text: `${nickname} - ${plant.myPlantName}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#e74c3c',
      cancelButtonColor: '#f0f0f0',
      confirmButtonText: '삭제',
      cancelButtonText: '취소',
      customClass: {
        popup: styles.customPopup,
        icon: styles.customIcon,
        htmlContainer: styles.customHtml, 
        actions: styles.customActions, 
      },
      width: '70%', 
    }).then((result) => {
      if (result.isConfirmed) {
        // 삭제 로직 처리
        Swal.fire({
          icon: 'success',
          title: '삭제 완료',
          text: '작물이 성공적으로 삭제되었습니다.',
          confirmButtonText: '확인',
        });
      }
    });
    
  };

  const handleAddPlantClick = () => {
    if (placeIdInfo && addressInfo) {
      console.log(placeIdInfo);
      console.log(addressInfo);
  
      const payload = {
        placeId: placeIdInfo,
        address: addressInfo
      };

      // selectGardenPost 호출
    selectGardenPost(payload)
    .then((data) => {
      console.log('성공적으로 데이터가 전송되었습니다:', data);
      // 데이터 전송 후 다른 로직 처리 (예: navigate)
      dispatch(setLocationData(addressInfo.jibun))
      navigate(`/myGarden/${myPlaceId}/register/plant`, { state: { plantData: data } });
    })
    .catch((error) => {
      console.error('데이터 전송 중 오류 발생:', error);
      // 오류 처리 로직 추가 (예: 사용자에게 오류 메시지 표시)
    });
    }

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
                Swal.fire('이름 너무 김', '이름은 10자 이하로 입력해주세요.', 'warning');
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
              <h3>{farm.myPlantName}</h3>              
              <p>{farm.todoInfo}</p>
            </div>
            <p className={styles.seedDate}>{farm.plantName}파종일: {farm.seedDate}</p>
            <img
              src={require('../../assets/icons/Delete.png')}
              alt="Delete"
              className={styles.deletePlantIcon}
              onClick={(e) => {
                e.stopPropagation();
                handleDeletePlantClick(farm);  // SweetAlert2 모달 띄우기
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
